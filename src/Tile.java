import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tile {
    private BufferedImage image;
    private int tileType;
    private String tileName;

    private ArrayList<String> tileFiles = new ArrayList<String>();

    public Tile(int tileType) {
        this.tileType = tileType;
        generateTileList();
        this.setTileType(tileType);
    }

    public void generateTileList(){
        for (int i = 0; i < 9; i++){
            tileFiles.add("Tiles/file" + i + ".png");
        }
    }

    public void setTileType(int tileType) {
        image = loadImage(tileFiles.get(tileType));
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