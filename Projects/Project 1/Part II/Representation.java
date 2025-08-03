import java.io.*;
import java.util.*;

public class Representation {
    public static Map<String, Integer> constructTable(String file) {
        //Store heuristic values
        Map<String, Integer> heuristicRepresentation = new HashMap<>();

        //Perform exception handling using try catch block while reading the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //Skip first 85 lines in order to immediately get to the data required for processing
            for (int i = 0; i < 85; i++) {
                reader.readLine();
            }
            String line;

            //Read the next 49 lines from the file while splitting the data based on whitespace
            for (int i = 85; i < 134; i++) {
                line = reader.readLine();
                String[] data = line.split(" ");

                //Store the node name and its heuristic value
                heuristicRepresentation.put(data[0], Integer.parseInt(data[1]));
            }
            reader.close();
            //Handle IO (input output) exception
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        //Return the representation of the heuristic values
        return heuristicRepresentation;
    }

    public static Map<String, List<Edge>> constructGraph(String file) {
        //Store each node's edges
        Map<String, List<Edge>> graphRepresentation = new HashMap<>();

        //Perform exception handling using try catch block while reading the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //Skip first 5 lines in order to immediately get to the data required for processing
            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }
            String line;

            //Read the next 71 lines from the file while splitting the data based on whitespace
            for (int i = 6; i < 77; i++) {
                line = reader.readLine();
                String[] data = line.split(" ");

                //Creation of an edge representing the connection between two nodes on the graph
                Edge firstConnectedCity = new Edge(data[0], data[1], Integer.parseInt(data[2]));
                Edge secondConnectedCity = new Edge(data[1], data[0], Integer.parseInt(data[2]));

                //Add the edge to the graph representation for the first node
                if (!graphRepresentation.containsKey(firstConnectedCity.getSource())) {
                    graphRepresentation.put(firstConnectedCity.getSource(), new ArrayList<>());
                }
                graphRepresentation.get(firstConnectedCity.getSource()).add(firstConnectedCity);

                //Add the edge to the graph representation for the second node
                if (!graphRepresentation.containsKey(secondConnectedCity.getSource())) {
                    graphRepresentation.put(secondConnectedCity.getSource(), new ArrayList<>());
                }
                graphRepresentation.get(secondConnectedCity.getSource()).add(secondConnectedCity);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        //Return the graph representation
        return graphRepresentation;
    }

    public static void constructPath(Node node) {
        //Creation of a list to store the path representation
        List<String> pathRepresentation = new ArrayList<>();
        //Get the distance of the path being taken
        int lengthOfPathTaken = node.getDistance();

        //Traverse the path back to the start node while managing the node's name and taking the previous node into consideration
        while (node != null) {
            pathRepresentation.add(0, node.getName());
            node = node.getPrevious();
        }

        //Output the result representing the length and the representation of the path taken
        System.out.println("Length of the path taken: " + lengthOfPathTaken);
        System.out.println("Path taken: " + pathRepresentation);
    }
}