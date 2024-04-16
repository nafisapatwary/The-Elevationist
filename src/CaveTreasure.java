import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaveTreasure extends Treasure {
    BufferedImage image;
    private String cave_treasure_file = "sprites/treasure.png";
    private int x;
    private int y;
    private Rectangle cave_treasure_rect = new Rectangle(x, y, 40, 37);


    public CaveTreasure() {
        image = loadImage(cave_treasure_file);
    }
}
