public class Node implements Comparable<Node> {
    private String name;
    private Node previous;
    private int distance;
    private int heuristic;
    private Algorithm algorithm;

    public Node(String name, Node previous, int distance, int heuristic, Algorithm algorithm) {
        this.name = name;
        this.previous = previous;
        this.distance = distance;
        this.heuristic = heuristic;
        this.algorithm = algorithm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Previous: " + previous + ", Distance: " + distance + ", Heuristic: " + heuristic + ", Algorithm: " + algorithm;
    }

    @Override
    public int compareTo(Node otherNode) {
        if (this.algorithm == Algorithm.BFS) {
            return Integer.compare(this.heuristic, otherNode.heuristic);
        } else {
            return Integer.compare(this.heuristic + this.distance, otherNode.heuristic + otherNode.distance);
        }
    }
}