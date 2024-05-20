import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Random;


public class Monster {
    private BufferedImage image;
    private String imageFile = "sprites/monster_file1.PNG";
    private ArrayList<String> monsters = new ArrayList<>();
    private int x = 750; // where the monsters spawn in
    private int y = 750;
    private Rectangle monsterRect = new Rectangle(x, y, 60, 60);
    private int speed;
    private Player p;
    private World currentWorld;

    public Monster() {
        p = new Player();
        image = loadImage(imageFile);
        speed = 2;
        currentWorld = new World("levels/cave_file");
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

    public void generateMonsterImage() {
        monsters.add("sprites/monster_file1.PNG");
        monsters.add("sprites/monster_file2.PNG");
        monsters.add("sprites/monster_file3.PNG");
        monsters.add("sprites/monster_file4.PNG");
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

    public int getSpeed() {
        return speed;
    }

    public void updateWorld(World newWorld) {
        currentWorld = newWorld;
    }

    public Rectangle getMonsterRect() {
        return monsterRect;
    }

    public void updateRectPos(int newX, int newY) {
        monsterRect.setLocation(newX, newY);
    }
}
