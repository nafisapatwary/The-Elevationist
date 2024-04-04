import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

public class WorldPanel extends JPanel implements MouseListener {

    private Rectangle button;

    private World cave;

    public WorldPanel(){
        button = new Rectangle(75, 200, 160, 26);
        this.addMouseListener(this);
        cave = new World();
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
    }

    public void mousePressed(MouseEvent e){
        Point clicked = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }


}
