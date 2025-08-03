import java.util.*;

public class PathFinder {
    //Declaration of the collections together with a constructor
    private final Map<String, List<Edge>> graphRepresentation;
    private final Map<String, Integer> heuristicRepresentation;

    public PathFinder(Map<String, List<Edge>> graphRepresentation, Map<String, Integer> heuristicRepresentation) {
        this.graphRepresentation = graphRepresentation;
        this.heuristicRepresentation = heuristicRepresentation;
    }

    public Node greedyBestFirstSearch(String startNode, String endNode) {
        //Initialize a priority queue to store nodes based on the heuristic value, set to keep track of visited nodes and a starting point
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visitedNodes = new HashSet<>();

        queue.add(new Node(startNode, null, 0, heuristicRepresentation.get(startNode), Algorithm.BFS));

        //Process nodes while the queue isn't empty
        while (!queue.isEmpty()) {
            //Extract the node with the lowest heuristic value
            Node currentNode = queue.poll();

            //If the destination node is reached, return the current node
            if (currentNode.getName().equals(endNode))
                return currentNode;

            //If the current node has already been visited, skip it
            if (visitedNodes.contains(currentNode.getName()))
                continue;

            //Keep track of nodes by marking them as visited
            visitedNodes.add(currentNode.getName());

            //Iterate through the neighbors of the current node being processed
            for (Edge edge : graphRepresentation.get(currentNode.getName())) {
                if (!visitedNodes.contains(edge.getDestination())) { //If the neighbor hasn't been visited, add it to the queue
                    queue.add(new Node(edge.getDestination(), currentNode, currentNode.getDistance() + edge.getDistance(), heuristicRepresentation.get(edge.getDestination()), Algorithm.BFS));
                }
            }
        }
        //Return null if no path was found during the process
        return null;
    }

    public Node aStarSearch(String startNode, String endNode) {
        //Initialize a priority queue to store nodes based on the heuristic value, set to keep track of visited nodes and a map to store each node's optimal distance
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Map<String, Integer> optimalDistance = new HashMap<>();
        Set<String> visitedNodes = new HashSet<>();

        //Define the starting points
        queue.add(new Node(startNode, null, 0, heuristicRepresentation.get(startNode), Algorithm.A_STAR));

        optimalDistance.put(startNode, 0);

        //Process nodes while the queue isn't empty
        while (!queue.isEmpty()) {
            //Extract the node with the lowest combined cost (distance + heuristic)
            Node currentNode = queue.poll();

            //If the destination node is reached, return the current node
            if (currentNode.getName().equals(endNode))
                return currentNode;

            //If the current node has already been visited, skip it
            if (visitedNodes.contains(currentNode.getName()))
                continue;

            //Keep track of nodes by marking them as visited
            visitedNodes.add(currentNode.getName());

            //Iterate through the neighbors of the current node being processed
            for (Edge edge : graphRepresentation.get(currentNode.getName())) {
                //Calculate the new distance to the node's neighbor
                int newDistance = currentNode.getDistance() + edge.getDistance();

                //If the neighbor hasn't been visited or the shorter path was found, add it to the queue
                if (!optimalDistance.containsKey(edge.getDestination()) || newDistance < optimalDistance.get(edge.getDestination())) {
                    optimalDistance.put(edge.getDestination(), newDistance);
                    queue.add(new Node(edge.getDestination(), currentNode, newDistance, heuristicRepresentation.get(edge.getDestination()), Algorithm.A_STAR));
                }
            }
        }
        //Return null if no path was found during the process
        return null;
    }
}