import java.io.File;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class FileManager {

    public static void createFile() {
        try {
            File myObj = new File("bestTimes.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
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
            myWriter.write("Trial #" + trial + ": " + (double) time / 1000 + " seconds");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
