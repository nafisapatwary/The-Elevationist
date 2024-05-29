import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class World {
    private Tile [][] level;
    private String worldName;
    private ArrayList<Treasure> treasures = new ArrayList<Treasure>();
    private ArrayList<Point> treasurePoints = new ArrayList<Point>();
    private boolean won;
    private boolean lost;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<World> worlds = WorldPanel.levels;

    public World(String fileName) {
        generateWorld(fileName);
        worldName = fileName;
        generateTreasure();
        generateMonsters();
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
            treasurePoints.add(new Point(randX, randY));
            if (treasurePoints.size() > 1){
                boolean farApart = checkOtherTreasureLocations(treasurePoints, treasurePoints.get(i));
                //will continue to generate new points until they are at least 100 units apart
                while (!farApart) {
                    int newRandX = (int) (Math.random() * 850 + 50);
                    int newRandY = (int) (Math.random() * 850 + 50);
                    treasures.get(i).setX(newRandX);
                    treasures.get(i).setY(newRandY);
                    treasurePoints.get(i).setX(newRandX);
                    treasurePoints.get(i).setY(newRandY);
                    treasures.get(i).updatePositions(newRandX, newRandY);
                    farApart = checkOtherTreasureLocations(treasurePoints, treasurePoints.get(i));
                }
            }
        }
    }


    private boolean checkOtherTreasureLocations(ArrayList<Point> points, Point curr){
        for (int i = 0; i < points.size(); i++){
            if (!curr.equals(points.get(i))){
                Point compare = points.get(i);
                if (Math.abs(compare.getX() - curr.getX()) <= 100 || Math.abs(compare.getY() - curr.getY()) <= 100){
                    return false;
                }
            }
        }
        return true;
    }

    public void generateMonsters() {
        for (int i = 0; i < worlds.size() + 1; i++) {
            if (i == 0) {
                Monster m1 = new Monster();
                m1.setX((int) (Math.random() * 850 + 50));
                m1.setY((int) (Math.random() * 850 + 50));
                monsters.add(m1);
            }
            if (i == 1 || i == 2) {
                Monster m2 = new Monster();
                m2.setX((int) (Math.random() * 850 + 50));
                m2.setY((int) (Math.random() * 850 + 50));
                monsters.add(m2);
            }
            if (i == 3) {
                Monster m3 = new Monster();
                m3.setX((int) (Math.random() * 850 + 50));
                m3.setY((int) (Math.random() * 850 + 50));
                monsters.add(m3);
            }
            if (i == 4 || i == 5) {
                Monster m4 = new Monster();
                m4.setX((int) (Math.random() * 850 + 50));
                m4.setY((int) (Math.random() * 850 + 50));
                monsters.add(m4);
            }
        }
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
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

    public void setWon(boolean newVal) {
        this.won = newVal;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean newVal) {
        this.lost = newVal;
    }
}
