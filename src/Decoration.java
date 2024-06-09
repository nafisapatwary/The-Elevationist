import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.lang.Object;


public class Decoration {
    private BufferedImage image;
    private String image_file;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle decorRect;

    public Decoration(String file, int x, int y, int w, int h) {
        image_file = "sprites/" + file + ".png";
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
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

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public Rectangle getPlayerRect() {
        return decorRect;
    }

}
