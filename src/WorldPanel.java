import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import javax.swing.*;




public class WorldPanel extends JPanel implements KeyListener{
    private Rectangle button;
    private Player p;
    private Monster m;
    private World currentWorld;
    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;
    private ArrayList<World> levels = new ArrayList<>();
    private int count;
    Scanner s = new Scanner(System.in);
    private boolean canMove;
    private boolean collided;
    private boolean transition;


    // for text box
    private final int box_x = 50;
    private final int box_y = 50;
    private final int box_height = 100;
    private final int box_width = 200;
    private long currTime;
    private boolean won;


    private String generatedCombo;
    private String userInput = "";
    private boolean displayCombination;
    private JTextField combinationField;


    public WorldPanel() {
        button = new Rectangle(75, 200, 160, 26);
        this.setFocusable(true);
        this.addKeyListener(this);

        //change later
        generateWorldList();
        currentWorld = levels.get(count);
        count = currentWorld.getCount();
        p = new Player();
        m = new Monster();
        m.updateWorld(currentWorld);
        canMove = true;
        collided = false;
        transition = false;
        won = false;

        combinationField = new JTextField();
        combinationField.setBounds(box_x + 50, box_y + 30, 50, 30);
        combinationField.addKeyListener(this);
        this.add(combinationField);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (transition){
            drawTransition(g);
        }
        if (won){
            drawWinningScreen(g);
        }
        else if (!won && !transition){
            drawTiles(g);
            drawPlayer(g);
            drawMonsters(g);
            drawTreasures(g);
            drawCBox(g);
        }
        if (System.currentTimeMillis() - 3000 > currTime){
            transition = false;
        }
    }


    public void generateWorldList() {
        levels.add(new World("levels/cave_file"));
        levels.add(new World("levels/ocean_file"));
        levels.add(new World("levels/ground_file"));
        levels.add(new World("levels/sky_file"));
        levels.add(new World("levels/space_file"));
        for (World l : levels) {
            System.out.println(l.getWorldName());
        }
    }


    // display combination box
    private void drawCBox(Graphics g) {
        if (displayCombination) {
            g.setColor(Color.WHITE);
            g.fillRect(box_x, box_y, box_width, box_height);
            g.setColor(Color.BLACK);
            g.drawRect(box_x, box_y, box_width, box_height);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString(generatedCombo, box_x + 10, box_y + 30);
            g.drawString("Enter combination: ", box_x + 10, box_y + 60);
            g.drawString(userInput, box_x + 10, box_y + 90);
        }
    }


    // display tiles
    private void drawTiles(Graphics g) {
        int x = 0;
        int y = 0;
        for (int row = 0; row < currentWorld.getLevel().length; row++) {
            for (int col = 0; col < currentWorld.getLevel()[0].length; col++) {
                Tile t = currentWorld.getLevel()[row][col];
                g.drawImage(t.getImage(), x, y, null);
                x = x + 45;
            }
            x = 0;
            y = y + 47;
        }

    }


    // display player
    private void drawPlayer(Graphics g) {
        checkBorders();
        if (moveUp) {
            p.setY(p.getY() - p.getSpeed());
        }
        if (moveLeft) {
            p.setX(p.getX() - p.getSpeed());
        }
        if (moveRight) {
            p.setX(p.getX() + p.getSpeed());
        }
        if (moveDown) {
            p.setY(p.getY() + p.getSpeed());
        }
        p.updateRectPos(p.getX(), p.getY());
        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
    }

    // displays the monsters
    private void drawMonsters(Graphics g) {
        m.updateRectPos(m.getX(), m.getY());
        g.drawImage(m.getImage(), m.getX(), m.getY(), null);
    }

    private void checkBorders() {
        if (p.getY() == 924) moveDown = false;
        if (p.getY() == 0) moveUp = false;
        if (p.getX() == 0) moveLeft = false;
        if (p.getX() == 975) moveRight = false;
    }


    // display treasures
    private void drawTreasures(Graphics g) {
        for (Treasure treasure : currentWorld.getTreasures()) {
            g.drawImage(treasure.getImage(), treasure.getX(), treasure.getY(), null);
        }
    }

    public void drawTransition(Graphics g) {
        setBackground(Color.pink);
        g.setFont(new Font("Impress", Font.PLAIN, 75));
        g.drawString("~~NEXT LEVEL~~", 200, 500);
        g.drawRect(150,380,715,200);
        //implement some sort of decoration behind
    }

    public void drawWinningScreen(Graphics g) {
        setBackground(Color.pink);
        g.setFont(new Font("Impress", Font.PLAIN, 75));
        g.drawString("~YAY YOU WON~", 200, 500);
        g.drawRect(150,380,715,200);
    }


    // collisions
    private void checkTreasureCollisions() {
        Rectangle pRect = p.getPlayerRect();
        for (Treasure t : currentWorld.getTreasures()) {
            Rectangle tRect = t.getTreasureRect();
            if (pRect.intersects(tRect)) {
                displayCombination = true;
                canMove = false;
                if (!collided) {
                    generatedCombo = Combination.chooseCombination(count, count + 3);
                    combinationField.setText(generatedCombo);
                }
                collided = true;
                combinationField.requestFocus();
            }
        }
    }

    public void checkMonsterCollisions() {
        if (m.getMonsterRect().intersects(p.getPlayerRect())) {
            currentWorld.setLost(true);
        }
    }

    public void removeTreasures() {
        Rectangle pRect = p.getPlayerRect();
        ArrayList<Treasure> treasuresToRemove = new ArrayList<>();
        for (int i = 0; i < currentWorld.getTreasures().size(); i++) {
            Rectangle tRect = currentWorld.getTreasures().get(i).getTreasureRect();
            if (pRect.intersects(tRect)) {
                treasuresToRemove.add(currentWorld.getTreasures().get(i));
            }
        }
        currentWorld.getTreasures().removeAll(treasuresToRemove);
        if (currentWorld.getTreasures().isEmpty() && count == 4){
            won = true;
        }
        else if (currentWorld.getTreasures().isEmpty() && !won) {
            System.out.println(count);
            System.out.println("You won!");
            count++;
            transition = true;
            currTime = System.currentTimeMillis();
            currentWorld = levels.get(count);
            setPlayerSpeed(count);
            m.updateWorld(currentWorld);
        }
    }


    public void setPlayerSpeed(int count) {
        if (count == 1) p.setSpeed(3);
    }

    public void checkConditions() {
        if (currentWorld.isWon()) {
            System.out.println("you won the game!"); // filler
        }
        else if (currentWorld.isLost()) {
            System.out.println("you lost the game!");
        }
    }

    // MOUSE AND KEY INTERACTIONS
    public void keyPressed(KeyEvent e) {
        if (canMove) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
//                p.move("w");
                moveUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
//                p.move("a");
                moveLeft = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
//                p.move("d");
                moveRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
//                p.move("s");
                moveDown = true;
            }
        }
    }


    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            moveUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            moveLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            moveRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            moveDown = false;
        }
        checkTreasureCollisions();
        checkMonsterCollisions();
        checkConditions();
        repaint();
    }


    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        canMove = true;
        if (displayCombination) {
            if (key == 8) {
                userInput = userInput.substring(0, userInput.length() - 1);
            } else {
                userInput += key;
                if (userInput.equals(generatedCombo)) {
                    removeTreasures();
                    userInput = "";
                    displayCombination = false;
                    canMove = true;
                    collided = false;
                }
                repaint();
            }
            System.out.println(userInput);
        }
    }
}
