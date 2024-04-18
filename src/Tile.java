import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    private BufferedImage image;
    private int tileType;
    private String tileName;

    public Tile(int tileType) {
        this.tileType = tileType;
        this.setTileType(tileType);
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
        if (tileType == 0) image = loadImage("Tiles/file0.png");
        if (tileType == 1) image = loadImage("Tiles/file0.png");
        if (tileType == 2) image = loadImage("Tiles/file0.png");

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

    public int getTileType() {
        return tileType;
    }

    public String toString() {
        return tileType + " " + image;
    }

}