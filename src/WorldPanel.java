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




public class WorldPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private Rectangle button;
    private Player p;
    private World currentWorld;
    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;
    private ArrayList<World> levels = new ArrayList<>();
    private int count;
    Scanner s = new Scanner(System.in);
    private boolean newLevel;
    private boolean canMove;
    private boolean collided;


    // for text box
    private final int box_x = 50;
    private final int box_y = 50;
    private final int box_height = 100;
    private final int box_width = 200;


    private String generatedCombo;
    private String userInput = "";
    private boolean displayCombination;
    private JTextField combinationField;


    public WorldPanel(){
        button = new Rectangle(75, 200, 160, 26);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addKeyListener(this);
        //change later
        generateWorldList();
        currentWorld = levels.get(count);
        count = currentWorld.getCount();
        p = new Player();
        newLevel = false;
        canMove = true;
        collided = false;


        combinationField = new JTextField();
        combinationField.setBounds(box_x + 50, box_y + 30, 50, 30);
        combinationField.addKeyListener(this);
        this.add(combinationField);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        drawPlayer(g);
        drawTreasures(g);
        drawCBox(g);
    }


    public void generateWorldList() {
        levels.add(new World("levels/cave_file"));
        levels.add(new World("levels/ocean_file"));
        levels.add(new World("levels/ground_file"));
        levels.add(new World("levels/sky_file"));
        levels.add(new World("levels/space_file"));
        for (World l: levels){
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
        if (newLevel){
            p.setX(450);
            p.setY(700);
        }
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


    private void checkBorders(){
        if (p.getY() == 924) moveDown = false;
        if (p.getY() == 0) moveUp = false;
        if (p.getX() == 0) moveLeft = false;
        if (p.getX() == 975) moveRight = false;
        if (count == 2){
            if (p.getY() < 513) moveUp = false;
        }


    }


    // display treasures
    private void drawTreasures(Graphics g) {
        for (Treasure treasure : currentWorld.getTreasures()) {
            g.drawImage(treasure.getImage(), treasure.getX(), treasure.getY(), null);
        }
    }

    public void drawTransition(Graphics g){
        setBackground(Color.BLACK);
        g.drawImage("");
        //implement some sort of decoration behind
    }


    // collisions
    private void checkCollisions() {
        Rectangle pRect = p.getPlayerRect();
        ArrayList<Treasure> treasuresToRemove = new ArrayList<>();


        for (Treasure t : currentWorld.getTreasures()) {
            Rectangle tRect = t.getTreasureRect();
            if (pRect.intersects(tRect)) {
                // start to implement the combination function here
                // the text needs to be on the screen + time
//                String c = Combination.chooseCombination(count, count + 3);
//                System.out.println("Your combination is: " + c);
//                System.out.print("Enter the combination: ");
//                String guess = s.next();
//                if (guess.equals(c)) {
//                treasuresToRemove.add(t);
//                }
                displayCombination = true;
                canMove = false;
                if (!collided){
                    generatedCombo = Combination.chooseCombination(count, count + 3);
                    combinationField.setText(generatedCombo);
                }
//                generatedCombo = Combination.chooseCombination(count, count + 3);
//                combinationField.setText(generatedCombo);
                collided = true;
                combinationField.requestFocus();
            }
        }


        currentWorld.getTreasures().removeAll(treasuresToRemove);
        if (currentWorld.getTreasures().isEmpty()) {
            System.out.println("You won!");
            count++;
            newLevel = true;
            currentWorld = levels.get(count);
            System.out.println(count);
            System.out.println(currentWorld.getWorldName());
            setPlayerSpeed(count);
        }
        else{
            newLevel = false;
        }
    }


    public void setPlayerSpeed(int count){
        if (count == 1) p.setSpeed(3);
    }




    // MOUSE AND KEY INTERACTIONS
    public void mousePressed(MouseEvent e){
        Point clicked = e.getPoint();
        System.out.println("Mouse Clicked");
    }


    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }


    public void keyPressed(KeyEvent e) {
        if (canMove){
            if (e.getKeyCode() == KeyEvent.VK_W){
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
        if (e.getKeyCode() == KeyEvent.VK_W){
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


        if (e.getKeyCode() == KeyEvent.VK_ENTER && displayCombination) {
            if (userInput.equals(generatedCombo)) {
                displayCombination = false;
                repaint();
                canMove = true;
                collided = false;
            }
        }
        checkCollisions();
        repaint();
    }


    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if (displayCombination) {
            if (key == 8){
                userInput = userInput.substring(0, userInput.length() - 1);
            }
            else{
                userInput += key;
            }
            repaint();
        }
        System.out.println(userInput);
    }
}
