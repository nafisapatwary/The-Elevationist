import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.lang.Object;


public class Player {
    private BufferedImage image;


    private String image_file = "sprites/cat_idle.png";
    private int x = 200;
    private int y = 200;
    private Rectangle playerRect = new Rectangle(x, y, 55, 55);
    private int speed;

    public Player() {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAnimationImage(double seconds, String direction, boolean walking){
        if (direction.equals("right")){
            System.out.println(seconds);
            image = loadImage("sprites/cat_idle.png");
            if (walking){
                if ((int)seconds % 2 == 0){
                    image = loadImage("sprites/cat_right_step_forward.png");
                    System.out.println("hi");
                }
                else{
                    image = loadImage("sprites/cat_right_step_backward.png");
                }
            }
        }
        if (direction.equals("left")){
            image = loadImage("sprites/cat_idle.png");
            if (walking){
                if ((int)seconds % 2 == 0){
                    image = loadImage("sprites/cat_left_step_forward.png");
                }
                else{
                    image = loadImage("sprites/cat_left_step_backward.png");
                }
            }
        }

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

    public void updateRectPos(int newX, int newY) {
        playerRect.setLocation(newX, newY);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
