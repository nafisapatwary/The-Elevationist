import java.awt.*;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import javax.swing.*;

public class WorldPanel extends JPanel implements KeyListener{
    private Player p;
    private Crown c;
    private World currentWorld;
    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;
    public static ArrayList<World> levels = new ArrayList<>();
    public static int count;
    Scanner s = new Scanner(System.in);
    private boolean canMove;
    private boolean collided;
    private boolean transition;
    private boolean debug;


    // for text box
    private final int box_x = 50;
    private final int box_y = 50;
    private final int box_height = 100;
    private final int box_width = 200;
    private long transitionTime;
    private long startingTime;
    private boolean won;
    private boolean lost;


    private String generatedCombo;
    private String userInput = "";
    private boolean displayCBox;
    private boolean displayCombo;
    private JTextField combinationField;
    private long numberComboTime;


    public WorldPanel() {
        this.setFocusable(true);
        this.addKeyListener(this);

        //change later
        generateWorldList();
        currentWorld = levels.get(count);
        count = 0;
        p = new Player();
        c = new Crown();
        canMove = true;
        collided = false;
        transition = false;
        won = false;
        lost = false;
        debug = false;
        startingTime = System.currentTimeMillis();

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
        if (lost){
            drawLosingScreen(g);
        }
        else if (!won && !transition && !lost){
            drawTiles(g);
            drawPlayer(g);
            drawMonsters(g);
            drawTreasures(g);
            if (displayCBox){
                drawCBox(g);
            }
        }
        if (System.currentTimeMillis() - 3000 > transitionTime){
            transition = false;
        }
        if (System.currentTimeMillis() - 3000 > numberComboTime){
            displayCombo = false;
        }
    }


    private void generateWorldList() {
        levels.add(new World("levels/cave_file"));
        levels.add(new World("levels/ocean_file"));
        levels.add(new World("levels/ground_file"));
        levels.add(new World("levels/sky_file"));
        levels.add(new World("levels/space_file"));
    }


    // display combination box
    private void drawCBox(Graphics g) {
        if (displayCBox) {
            g.setColor(Color.WHITE);
            g.fillRect(box_x, box_y, box_width, box_height);
            g.setColor(Color.BLACK);
            g.drawRect(box_x, box_y, box_width, box_height);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            if (displayCombo){
                g.drawString(generatedCombo, box_x + 10, box_y + 30);
            }
            else{
                g.drawString("Enter combination: ", box_x + 10, box_y + 60);
                g.drawString(userInput, box_x + 10, box_y + 90);
            }
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
        double currTime = (double)System.currentTimeMillis() - (double)startingTime;
        currTime /= 10;
        currTime = Math.round(currTime);
        if (moveUp) {
            p.setY(p.getY() - p.getSpeed());
        }
        if (moveLeft) {
            p.setX(p.getX() - p.getSpeed());
            p.setAnimationImage(currTime, "left", true);
        }
        if (moveRight) {
            p.setX(p.getX() + p.getSpeed());
            p.setAnimationImage(currTime, "right", true);
        }
        if (moveDown) {
            p.setY(p.getY() + p.getSpeed());
        }
        p.updateRectPos(p.getX(), p.getY());
        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
    }

    // displays the monsters
    private void drawMonsters(Graphics g) {
        for (Monster m : currentWorld.getMonsters()) {
            m.updateRectPos(m.getX(), m.getY());
            g.drawImage(m.getImage(), m.getX(), m.getY(), null);
        }
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

    private void drawTransition(Graphics g) {
        setBackground(Color.pink);
        g.setFont(new Font("Monospaced", Font.BOLD, 75));
        g.drawString("~~NEXT LEVEL~~", 200, 500);
        g.drawRect(150,380,715,200);
        //implement some sort of decoration behind
    }

    private void drawWinningScreen(Graphics g) {
        setBackground(Color.pink);
        g.setFont(new Font("Monospaced", Font.BOLD, 75));
        g.drawString("~YAY YOU WON~", 220, 500);
        g.drawRect(150,380,715,200);
        g.drawImage(c.getImage(), 337, 100, null);
    }

    private void drawLosingScreen(Graphics g) {
        setBackground(Color.pink);
        g.setFont(new Font("Monospaced", Font.BOLD, 75));
        g.drawString("womp womp", 310, 500);
        g.drawRect(150,380,715,200);
    }


    // collisions
    private void checkTreasureCollisions() {
        Rectangle pRect = p.getPlayerRect();
        for (Treasure t : currentWorld.getTreasures()) {
            Rectangle tRect = t.getTreasureRect();
            if (pRect.intersects(tRect)) {
                displayCBox = true;
                numberComboTime = System.currentTimeMillis();
                canMove = false;
                if (!collided) {
                    generatedCombo = Combination.chooseCombination(count, count + 3);
                    combinationField.setText(generatedCombo);
                    displayCombo = true;
                }
                collided = true;
                combinationField.requestFocus();
                if (debug) {
                    System.out.println(collided);
                    System.out.println(canMove);
                    System.out.println(displayCBox);
                }
            }
        }
    }


    public void checkMonsterCollisions() {
        for (Monster m : currentWorld.getMonsters()) {
            if (m.getMonsterRect().intersects(p.getPlayerRect())) {
                lost = true;
            }
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
            if (debug) {
                System.out.println(count);
                System.out.println("You won!");
            }
            count++;
            transition = true;
            transitionTime = System.currentTimeMillis();
            currentWorld = levels.get(count);
            setPlayerSpeed(count);
            p.setX(200);
            p.setY(200);
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
                moveUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                moveLeft = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                moveRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                moveDown = true;
            }
        }

        for (Monster m : currentWorld.getMonsters()) {
            if (p.getX() > m.getX()) {
                m.setX(m.getX() + m.getSpeed());
            }
            if (p.getX() < m.getX()) {
                m.setX(m.getX() - m.getSpeed());
            }
            if (p.getY() > m.getY()) {
                m.setY(m.getY() + m.getSpeed());
            }
            if (p.getY() < m.getY()) {
                m.setY(m.getY() - m.getSpeed());
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
        if (displayCBox) {
            if (key == 8) {
                userInput = userInput.substring(0, userInput.length() - 1);
            } else {
                userInput += key;
                if (userInput.equals(generatedCombo + " ")) {
                    removeTreasures();
                    userInput = "";
                    displayCBox = false;
                    canMove = true;
                    collided = false;
                }
                repaint();
            }
            if (debug) {
                System.out.println(userInput);
            }
        }
    }
}
