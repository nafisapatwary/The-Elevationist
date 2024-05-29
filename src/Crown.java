import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Crown {
    private BufferedImage image;
    private String image_file = "sprites/crown.png";
    private Rectangle playerRect = new Rectangle(100, 300, 300, 300);
    private int speed;

    public Crown() {
        image = loadImage(image_file);
        speed = 3;
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

}
