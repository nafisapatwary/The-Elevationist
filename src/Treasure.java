import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Object;

public class Treasure {
    private BufferedImage image;

    private final String image_file = "sprites/treasure.png";
    private int x = 500;
    private int y = 500;

    private Rectangle treasureRect = new Rectangle(40, 37, x, y);


    public Treasure() {
        image = loadImage(image_file);
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
}
