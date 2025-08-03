import java.text.*;
import java.util.*;
import java.io.*;

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 50;
    private static final int NUMBER_OF_GENERATIONS = 5000;
    private static final double RATIO_OF_ELITE_INDIVIDUALS = 0.05;

    //Apply the genetic algorithm assuring elite individuals proceed to next iteration in order to not lose good solutions
    public static void performGeneticAlgorithm(List<City> cities, BufferedWriter writer) throws IOException {
        List<List<City>> population = GeneticAlgorithmHelper.createInitialPopulation(cities, POPULATION_SIZE);
        int eliteIndividualsCounter = (int) (RATIO_OF_ELITE_INDIVIDUALS * POPULATION_SIZE);

        for (int generation = 0; generation < NUMBER_OF_GENERATIONS; generation++) {
            List<List<City>> newPopulation = GeneticAlgorithmHelper.evolvePopulation(population, POPULATION_SIZE);

            for (int i = 0; i < newPopulation.size() - 1; i++) {
                for (int j = i + 1; j < newPopulation.size(); j++) {
                    double distance1 = GeneticAlgorithmHelper.calculateTotalDistance(newPopulation.get(i));
                    double distance2 = GeneticAlgorithmHelper.calculateTotalDistance(newPopulation.get(j));

                    if (distance1 > distance2) {
                        List<City> temp = newPopulation.get(i);
                        newPopulation.set(i, newPopulation.get(j));
                        newPopulation.set(j, temp);
                    }
                }
            }

            if (eliteIndividualsCounter > newPopulation.size()) {
                eliteIndividualsCounter = newPopulation.size();
            }

            List<List<City>> eliteIndividuals = new ArrayList<>(newPopulation.subList(0, eliteIndividualsCounter));
            List<List<City>> noEliteIndividuals = newPopulation.subList(eliteIndividualsCounter, newPopulation.size());
            List<List<City>> offsprings = GeneticAlgorithmHelper.evolvePopulation(noEliteIndividuals, POPULATION_SIZE);

            population = new ArrayList<>();
            population.addAll(eliteIndividuals);
            population.addAll(offsprings);

            List<City> cityBestRoute = population.get(0);
            double cityBestDistance = GeneticAlgorithmHelper.calculateTotalDistance(cityBestRoute);
            writer.write("Generation " + generation + ": " + cityBestDistance);
            writer.newLine();
            writer.flush();
        }

        List<City> cityBestRoute = null;
        double cityBestDistance = Double.MAX_VALUE;
        for (List<City> cityRoute : population) {
            double totalDistance = GeneticAlgorithmHelper.calculateTotalDistance(cityRoute);
            if (totalDistance < cityBestDistance) {
                cityBestDistance = totalDistance;
                cityBestRoute = cityRoute;
            }
        }
        System.out.println("City Route: " + cityBestRoute);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Distance Travelled: " + decimalFormat.format(cityBestDistance));
    }
}