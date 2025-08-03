import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Korisnik\\Desktop\\assignment 3\\berlin52.tsp";
        List<City> cities = Data.loadData(filePath);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Korisnik\\Desktop\\acoOutput.txt"));
            antColonyOptimization.performAntColonyOptimization(writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}