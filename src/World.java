import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class World {
    private Tile [][] level;
    private String worldName;
    private ArrayList<Treasure> treasures = new ArrayList<Treasure>();
    private boolean won;
    private boolean lost;


    public World(String fileName) {
        generateWorld(fileName);
        worldName = fileName;
        generateTreasure();
    }


    // reads the file
    private String[][] getWorld(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        int rows = fileData.size();
        int cols = fileData.get(0).length();

        String[][] worldData = new String[rows][cols];

        for (int i = 0; i < fileData.size(); i++) {
            String d = fileData.get(i);
            for (int j = 0; j < d.length(); j++) {
                worldData[i][j] = d.charAt(j) + "";
            }
        }
        return worldData;
    }
    
    // generates each level
    private void generateWorld(String filename) {
        String[][] mazeData = getWorld(filename);
        level = new Tile[mazeData.length][mazeData[0].length];
        for (int r = 0; r < level.length; r++) {
            for (int c = 0; c < level[0].length; c++) {
                Tile t = new Tile(mazeData[r][c]);
                level[r][c] = t;
            }
        }
    }

    // generates the treasure for each level
    private void generateTreasure(){
        for (int i = 0; i < 4; i++){
            int randX = (int)(Math.random() * 850 + 50);
            int randY = (int)(Math.random() * 850 + 50);
            treasures.add(new Treasure(randX, randY));
            treasures.get(i).updatePositions(randX, randY);
        }

    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public Tile[][] getLevel() {
        return level;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
