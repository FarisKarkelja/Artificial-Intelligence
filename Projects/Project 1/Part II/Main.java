import java.util.*;

class Main {
    public static void main(String[] args) {
        String file = "C:\\Users\\Korisnik\\Desktop\\assignment 1\\part 2\\spain map.txt";

        Map<String, Integer> heuristicRepresentation = Representation.constructTable(file);
        Map<String, List<Edge>> graphRepresentation = Representation.constructGraph(file);

        PathFinder pathFinder = new PathFinder(graphRepresentation, heuristicRepresentation);

        System.out.println("<--- Greedy Best First Search --->");
        Node greedyPath = pathFinder.greedyBestFirstSearch("Malaga", "Valladolid");
        Representation.constructPath(greedyPath);

        System.out.println();

        System.out.println("<--- A* Star Search --->");
        Node aStarPath = pathFinder.aStarSearch("Malaga", "Valladolid");
        Representation.constructPath(aStarPath);
    }
}