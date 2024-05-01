import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    private BufferedImage image;
    private String tileType;
    private String tileName;
    private HashMap<String, String> tileFiles = new HashMap<String, String>();

    public Tile(String tileType) {
        this.tileType = tileType;
        generateTilesHash();
        this.setTileType(tileType);
    }

    public void setTileType(String tileType) {
        System.out.println("THIS IS THE TILE NUM: " + tileFiles.get(tileType));
        image = loadImage("Tiles/" + tileFiles.get(tileType));
    }

    public void generateTilesHash(){
        for (int i = 0; i < 10; i++){
            tileFiles.put(Integer.toString(i), "file" + i + ".png");
        }
        tileFiles.put("#", "file10.png");
        tileFiles.put("@", "file11.png");
        tileFiles.put("$", "file12.png");
        tileFiles.put("%", "file13.png");
        tileFiles.put("*", "file14.png");
        tileFiles.put("+", "file15.png");
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

    public String getTileType() {
        return tileType;
    }

    public String toString() {
        return tileType + " " + image;
    }

}