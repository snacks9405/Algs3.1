import java.io.*;
import java.util.Random;

public class Algs3_1InputGen {

    public static void generateInput(String fileName, int weight, int nodes) {
        try {
            FileWriter pathWriter = new FileWriter(fileName);
            pathWriter.write(weight + " " + nodes + " ");
            Random rng = new Random();
            int edgeLength = (int)Math.sqrt(nodes);
            int paths = ((edgeLength - 1) * edgeLength) +  ((edgeLength -1) * edgeLength);
            for(int i = 1; i <= paths; i++) {
                pathWriter.write(rng.nextInt(-20, 20) + " ");
            }
            pathWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void generateInput() {
        generateInput("input1.txt", -20, 9);
    }
}

