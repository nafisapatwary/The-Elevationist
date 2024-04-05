import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class World {
    private Tile [][] level;
    private Player player;

    public World() {
        generateWorld();
    }


    // reads the file
    private int[][] getWorld(String fileName) {
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

        int[][] worldData = new int[rows][cols];

        for (int i = 0; i < fileData.size(); i++) {
            String d = fileData.get(i);
            for (int j = 0; j < d.length(); j++) {
                if (d.charAt(j) == '#')
                    worldData[i][j] = 1;
            }
        }
        return worldData;
    }
    
    // generates each level
    private void generateWorld() {
        int[][] mazeData = getWorld("levels/cave_file");

        level = new Tile[mazeData.length][mazeData[0].length];
        for (int r = 0; r < level.length; r++) {
            for (int c = 0; c < level[0].length; c++) {
                Tile t = new Tile(mazeData[r][c]);
                level[r][c] = t;
            }
        }
    }

    public Tile[][] getLevel() {
        return level;
    }
}