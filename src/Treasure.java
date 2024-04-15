import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Object;

public class Treasure {
    private BufferedImage image;

    private final String image_file = "sprites/treasure.png";
    private int x;
    private int y;

    private Rectangle rect = getBounds();

    public Treasure() throws IOException {
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

    public Rectangle getBounds() throws IOException {
        BufferedImage bimg = ImageIO.read(new File(image_file));
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        return new Rectangle(width, height, x, y);
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
}
