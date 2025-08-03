import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

//Apply ant colony optimization algorithm assuring higher visibility and amount of pheromones motivating an ant to choose better
public class AntColonyOptimization {
    private final List<City> cities;
    public static final int NUMBER_OF_ANTS = 40;
    public static final int NUMBER_OF_ITERATIONS = 150;
    public static final double ALPHA = 4.0;
    public static final double BETA = 3.0;
    public static final double EVAPORATION_RATE = 0.25;

    public AntColonyOptimization(List<City> cities) {
        this.cities = cities;
    }

    public void performAntColonyOptimization(BufferedWriter writer) throws IOException {
        int numberOfCities = cities.size();

        double[][] distanceMatrix = AntColonyHelper.constructDistanceMatrix(cities);
        double[][] visibilityMatrix = AntColonyHelper.constructVisibilityMatrix(cities);
        double[][] pheromoneMatrix = AntColonyHelper.constructPheromoneMatrix(cities);

        int[] bestCityRoute = null;
        double bestCityDistance = Double.MAX_VALUE;

        for (int iteration = 0; iteration < NUMBER_OF_ITERATIONS; iteration++) {
            int[][] cityRoutes = new int[NUMBER_OF_ANTS][numberOfCities + 1];
            double[] cityDistances = new double[NUMBER_OF_ANTS];

            for (int ant = 0; ant < NUMBER_OF_ANTS; ant++) {
                boolean[] visitedCities = new boolean[numberOfCities];
                cityRoutes[ant][0] = 0;
                visitedCities[0] = true;

                for (int stepTaken = 1; stepTaken < numberOfCities; stepTaken++) {
                    int currentCity = cityRoutes[ant][stepTaken - 1];
                    double[] probabilities = new double[numberOfCities];
                    double totalProbability = 0;

                    for (int nextCity = 0; nextCity < numberOfCities; nextCity++) {
                        if (!visitedCities[nextCity]) {
                            probabilities[nextCity] = Math.pow(pheromoneMatrix[currentCity][nextCity], ALPHA) * Math.pow(visibilityMatrix[currentCity][nextCity], BETA);
                            totalProbability += probabilities[nextCity];
                        }
                    }

                    Random random = new Random();
                    double randomValue = random.nextDouble() * totalProbability;
                    double cumulativeProbability = 0;
                    for (int nextCity = 0; nextCity < numberOfCities; nextCity++) {
                        if (!visitedCities[nextCity]) {
                            cumulativeProbability += probabilities[nextCity];
                            if (cumulativeProbability >= randomValue) {
                                cityRoutes[ant][stepTaken] = nextCity;
                                visitedCities[nextCity] = true;
                                break;
                            }
                        }
                    }
                }

                double cityRouteDistance = 0;
                for (int i = 0; i < numberOfCities - 1; i++) {
                    cityRouteDistance += distanceMatrix[cityRoutes[ant][i]][cityRoutes[ant][i + 1]];
                }
                cityRouteDistance += distanceMatrix[cityRoutes[ant][numberOfCities - 1]][cityRoutes[ant][0]];
                cityDistances[ant] = cityRouteDistance;

                if (cityRouteDistance < bestCityDistance) {
                    bestCityDistance = cityRouteDistance;
                    bestCityRoute = cityRoutes[ant];
                }
            }
            AntColonyHelper.evaporatePheromones(pheromoneMatrix, EVAPORATION_RATE, cities);
            AntColonyHelper.depositPheromones(pheromoneMatrix, NUMBER_OF_ANTS, cities, cityRoutes, cityDistances);
            writer.write("Best Distance = " + bestCityDistance + "\n");
            writer.flush();
        }
        if (bestCityRoute != null) {
            int[] bestCityRouteResult = new int[bestCityRoute.length];
            for (int i = 0; i < bestCityRoute.length; i++) {
                bestCityRouteResult[i] = bestCityRoute[i] + 1;
            }
            System.out.println("City Route: " + Arrays.toString(bestCityRouteResult));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            System.out.println("Distance Travelled: " + decimalFormat.format(bestCityDistance));
        }
    }
}