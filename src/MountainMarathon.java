import java.io.*;
import java.util.*;

public class MountainMarathon {
    static int turnPenalty;
    static int numberOfNodes;
    static int[][] nodeArr;
    static int[][] orientationArr;
    static WeightedGraph.Graph pathGraph;
    public static void main(String[] args) {
        //String fileName = "inputsample.txt";
        String fileName = "input1.txt";
        //Algs3_1InputGen.generateInput("input1.txt", -20, 1000000);
        float startTime = System.currentTimeMillis();
        System.out.println(startTime);
        readPaths(fileName);
        pathGraph.printGraph();
        bestPath();
        float endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.printf("end time: %f\n", + endTime - startTime);
    }

    public static void readPaths(String fileName) {
        Scanner pathReader;

        try {
            BufferedReader br =
                    new BufferedReader(new FileReader(fileName));
            pathReader = new Scanner(br);
            turnPenalty = pathReader.nextInt();
            numberOfNodes = pathReader.nextInt();
            int dimension = (int)Math.sqrt(numberOfNodes);
            pathGraph = new WeightedGraph.Graph(numberOfNodes);
            nodeArr = new int[dimension][dimension];
            int numDiags = (dimension * 2) - 1;
            int middleDiag = (numDiags / 2) + 1;

            //2loops of n to generate a 2d model then create graph of nodes by referencing the model
            for (int h = 1; h <= 2; h++) {
                int populateArrIndex = 1;
                int nodes = 0;
                for (int i = 1; i <= numDiags; i++) {
                    int col = 0;
                    int row = 0;
                    if (i <= middleDiag) {
                        nodes++;
                        for (int j = 0; j < nodes; j++) {
                            row = j;
                            col = (i - j) - 1;
                            if (h == 1) {
                                nodeArr[row][col] = populateArrIndex++;
                            } else if (row == dimension - 1) {
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row][col + 1], pathReader.nextInt(), 1); //1 for right path,,,
                            } else if (col == dimension - 1) {
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row+1][col], pathReader.nextInt(), 0); //0 for down path
                            }else {
                                pathGraph.addEdge(populateArrIndex, nodeArr[row][col + 1], pathReader.nextInt(), 1);
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row + 1][col], pathReader.nextInt(), 0);
                            }
                        }
                    } else {
                        nodes--;
                        for (int j = 0; j < nodes; j++) {
                            row = (i - dimension) + j;
                            col = (dimension - 1) - j;
                            if (h == 1) {nodeArr[row][col] = populateArrIndex++;}
                            else if (col == dimension - 1 && row == dimension - 1) {
                            } else if (col == dimension - 1) {
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row + 1][col], pathReader.nextInt(), 0);
                            } else if (row == dimension - 1) {
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row][col+1], pathReader.nextInt(), 1);
                            }else {
                                pathGraph.addEdge(populateArrIndex, nodeArr[row][col + 1], pathReader.nextInt(), 1);
                                pathGraph.addEdge(populateArrIndex++, nodeArr[row + 1][col], pathReader.nextInt(), 0);
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void bestPath() {
        int[][] bestPathArr = new int[nodeArr.length][nodeArr.length];
        for (int[] arr : bestPathArr) {
            Arrays.fill(arr, Integer.MIN_VALUE);
        }
        orientationArr = new int[nodeArr.length][nodeArr.length];
        for (int[] arr: orientationArr) {
            Arrays.fill(arr, -1);
        }
        System.out.println(bestPath(bestPathArr, nodeArr.length-1, nodeArr.length-1, 2));
    }

    static int bestPath(int[][] bestPathArr, int col, int row, int orientation) {
        int tempTurnPenalty = orientation == 2 ? 0 : turnPenalty;
        if (row == 0 && col == 0) {
            bestPathArr[row][col] = 0;
            return 0;
        }else {
            int path1 = Integer.MIN_VALUE;
            int path2 = Integer.MIN_VALUE;
            WeightedGraph.Edge edge1 = null;
            WeightedGraph.Edge edge2 = null;
            if (row > 0) {
                path1 = (bestPathArr[row-1][col] == Integer.MIN_VALUE) ? bestPath(bestPathArr, col, row-1, 0) : bestPathArr[row-1][col];
                edge1 = pathGraph.getEdge(nodeArr[row-1][col], nodeArr[row][col]);
                path1 += edge1.weight;
                if (orientationArr[row-1][col] != edge1.orientation && edge1.source != 1) {
                    path1 += tempTurnPenalty;
                }
            }
            if (col > 0) {
                path2 = (bestPathArr[row][col-1] == Integer.MIN_VALUE) ? bestPath(bestPathArr, col-1, row, 1) : bestPathArr[row][col-1];
                edge2 = pathGraph.getEdge(nodeArr[row][col-1], nodeArr[row][col]);
                path2 += edge2.weight;
                if (orientationArr[row][col-1] != edge2.orientation && edge2.source != 1) {
                    path2 += tempTurnPenalty;
                }
            }
            int path1Temp = path1;
            int path2Temp = path2;
            boolean temped = false;
            if (edge1 != null && edge2!= null) {
                if (edge1.orientation == orientation) {
                    path1Temp -= turnPenalty;
                }
                if (edge2.orientation == orientation) {
                    path2Temp -= turnPenalty;
                }
                if (Math.max(path1Temp, path2Temp) != Math.max(path1, path2)) {
                    path1 = path1Temp;
                    path2 = path2Temp;
                    temped = true;
                }
            }
            int bestPath = Math.max(path1, path2);
            bestPathArr[row][col] = temped? bestPath + turnPenalty : bestPath;
            WeightedGraph.Edge bestEdge = Math.max(path1, path2) == path1 ? edge1 : edge2;
            orientationArr[row][col] = bestEdge.orientation;
            return bestPathArr[row][col];
        }
    }
}