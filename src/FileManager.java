import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private static File bestTimes = new File("bestTimes.txt");
    public static ArrayList<Trial> TRIAL_LIST = new ArrayList<Trial>();

    public static void createFile() {
        try {
            if (bestTimes.createNewFile()) {
                System.out.println("File created: " + bestTimes.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void writeToFile(int trial, long time) {
        try {
            FileWriter myWriter = new FileWriter("bestTimes.txt");
            //read from arraylist instead
            myWriter.write("Trial #" + trial + ": " + (double) time / 1000 + " seconds");
            myWriter.write("AAAAAAA TEST TEST test");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String readFile(){
        try {
            Scanner myReader = new Scanner(bestTimes);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public static String readLastTrialNumber(){
        try {
            Scanner myReader = new Scanner(bestTimes);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine() + "\n";
                System.out.println(data);
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
    public static void createTrialList(long time){
        //check the last/most recent trial number
    }
}
