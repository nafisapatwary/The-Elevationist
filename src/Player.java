import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.lang.Object;


public class Player {
    private BufferedImage image;

    private final String image_file = "sprites/player_file_cat.png";
    private int x = 502;
    private int y = 1000;
    private Rectangle playerRect = new Rectangle(47, 47, x, y);

    public Player() {
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
        return playerRect;
    }

    public void updateRectPos() {
        this.playerRect = new Rectangle(47, 47, x, y);
    }
}
