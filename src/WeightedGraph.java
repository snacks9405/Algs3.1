import java.util.ArrayList;
import java.util.LinkedList;

public class WeightedGraph {
    static class Edge {
        int source;
        int destination;
        int weight;
        int orientation;

        public Edge(int source, int destination, int weight, int orientation) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
            this.orientation = orientation;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<ArrayList<WeightedGraph.Edge>> adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new ArrayList<>();
            //initialize adjacency lists for all the vertices
            for (int i = 1; i <= vertices ; i++) {
                adjacencylist.add(new ArrayList<Edge>());
            }
        }

        public void addEdge(int source, int destination, int weight, int orientation) {
            Edge edge = new Edge(source, destination, weight, orientation);
            ArrayList<WeightedGraph.Edge> nodeEdges = adjacencylist.get(source);
            nodeEdges.add(edge); //for directed graph
            adjacencylist.set(source, nodeEdges);
        }

        public Edge getEdge(int source, int destination) {
            ArrayList<Edge> edges = adjacencylist.get(source);
            for (Edge edge : edges) {
                if (edge.destination == destination) {
                    return edge;
                }
            }
            return null;
        }

        public void printGraph(){
            for (int i = 0; i <vertices ; i++) {
                ArrayList<Edge> list = adjacencylist.get(i);
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println("source: " + i + " || destination: " +
                            list.get(j).destination + " || weight " + list.get(j).weight);
                }
            }
        }
    }

}
