import java.util.*;

public class Main {
    public static void main(String[] args) {
        String file = "C:\\Users\\Korisnik\\Desktop\\assignment 1\\part 1\\knapsack.txt";
        List<Integer> benefits = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        int maximumWeight = 420;

        Data.readData(file, benefits, weights);

        Knapsack knapsack = new Knapsack();

        System.out.print("<--- Breadth First Search --->");

        Performance.measurePerformance(() -> {
            State bfsState = knapsack.breadthFirstSearch(benefits, weights, maximumWeight);
            System.out.println(bfsState);
        });

        System.out.println();
        System.out.print("<--- Depth First Search --->");

        Performance.measurePerformance(() -> {
            State dfsState = knapsack.depthFirstSearch(benefits, weights, maximumWeight);
            System.out.println(dfsState);
        });
    }
}