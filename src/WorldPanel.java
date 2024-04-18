import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.event.KeyEvent;


public class WorldPanel extends JPanel implements MouseListener, KeyListener {
    private Rectangle button;
    private Player p;
    private World currentWorld;
    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;
    private ArrayList<World> levels = new ArrayList<>();
    private int count = 0;


    public WorldPanel(){
        button = new Rectangle(75, 200, 160, 26);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addKeyListener(this);
        //change later
        generateWorldList();
        currentWorld = levels.get(count);
        p = new Player();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        drawPlayer(g);
        drawTreasures(g);
    }

    public void generateWorldList() {
        levels.add(new World("levels/cave_file"));
        levels.add(new World("levels/ocean_file"));
        for (World l: levels){
            System.out.println(l.getWorldName());
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
        // player movement
        if (moveUp) {
            p.setY(p.getY() - 1);
        }
        if (moveLeft) {
            p.setX(p.getX() - 1);
        }
        if (moveRight) {
            p.setX(p.getX() + 1);
        }
        if (moveDown) {
            p.setY(p.getY() + 1);
        }
        p.updateRectPos(p.getX(), p.getY());
        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
    }

    // display treasures
    private void drawTreasures(Graphics g) {
        for (Treasure treasure : currentWorld.getTreasures()) {
            g.drawImage(treasure.getImage(), treasure.getX(), treasure.getY(), null);
        }
    }

    // collisions
    private void checkCollisions() {
        Rectangle pRect = p.getPlayerRect();
        ArrayList<Treasure> treasuresToRemove = new ArrayList<>();

        for (Treasure t : currentWorld.getTreasures()) {
            Rectangle tRect = t.getTreasureRect();
            if (pRect.intersects(tRect)) {
                treasuresToRemove.add(t);
                System.out.println("collided");
            }
        }

        currentWorld.getTreasures().removeAll(treasuresToRemove);
        if (currentWorld.getTreasures().isEmpty()) {
            System.out.println("You won!");count++;
            currentWorld = levels.get(count);
            System.out.println(count);
            System.out.println(currentWorld.getWorldName());
        }
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
        if (e.getKeyCode() == KeyEvent.VK_W){
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
        checkCollisions();
        repaint();
    }

    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
    }

}
