import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Object;

public class Treasure {
    private BufferedImage image;
    private String image_file;
    private Point point;
    private int x;
    private int y;
    private Rectangle treasureRect = new Rectangle(x, y, 50, 47);


    public Treasure(int x, int y) {
        image = loadImage("sprites/closedChest.png");
        this.x = x;
        this.y = y;
        point = new Point(x, y);
    }

    public BufferedImage loadImage(String fileName) {
        try {
            BufferedImage image;
            image = ImageIO.read(new File(fileName));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getTreasureRect() {
        return treasureRect;
    }

    public void updatePositions(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        treasureRect.setLocation(newX, newY);
    }
}
