import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.event.KeyEvent;


public class WorldPanel extends JPanel implements MouseListener, KeyListener {

    private Rectangle button;
    private Player p;
    private World cave;
    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;
    private Treasure t;

    public WorldPanel(){
        button = new Rectangle(75, 200, 160, 26);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addKeyListener(this);
        cave = new World();
        p = new Player();
        t = new Treasure();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        int y = 0;
        for (int row = 0; row < cave.getLevel().length; row++) {
            for (int col = 0; col < cave.getLevel()[0].length; col++) {
                Tile t = cave.getLevel()[row][col];
                g.drawImage(t.getImage(), x, y, null);
                x = x + 45;
            }
            x = 0;
            y = y + 47;
        }
        Rectangle pRect = p.getPlayerRect();
        Rectangle tRect = t.getTreasureRect();

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
        checkCollision(pRect, tRect);
        p.updateRectPos(p.getX(), p.getY());
        g.drawImage(t.getImage(), t.getX(), t.getY(), null);
        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
    }

    public boolean checkCollision(Rectangle x, Rectangle y){
        if (x.intersects(y)) {
            return true;
        }
        return false;
    }

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
            p.setY(p.getY() - 1);
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
    }

    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
    }

}
