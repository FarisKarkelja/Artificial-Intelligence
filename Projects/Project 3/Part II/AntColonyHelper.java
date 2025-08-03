import java.util.*;

public class AntColonyHelper {
    //Make a distance matrix to keep track of distances between cities
    public static double[][] constructDistanceMatrix(List<City> cities) {
        int numberOfCities = cities.size();
        double[][] distanceMatrix = new double[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i != j) {
                    distanceMatrix[i][j] = cities.get(i).calculateDistance(cities.get(j));
                } else {
                    distanceMatrix[i][j] = Double.MAX_VALUE;
                }
            }
        }
        return distanceMatrix;
    }

    //Visibility matrix representing the visibility range for an ant, meaning the shorter the distance, the higher the visibility
    public static double[][] constructVisibilityMatrix(List<City> cities) {
        int numberOfCities = cities.size();
        double[][] visibilityMatrix = new double[numberOfCities][numberOfCities];
        double[][] distanceMatrix = constructDistanceMatrix(cities);
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (distanceMatrix[i][j] != Double.MAX_VALUE) {
                    visibilityMatrix[i][j] = 1.0 / distanceMatrix[i][j];
                } else {
                    visibilityMatrix[i][j] = 0;
                }
            }
        }
        return visibilityMatrix;
    }

    //Representation of pheromones to provide paths for other ants travelling
    public static double[][] constructPheromoneMatrix(List<City> cities) {
        int numberOfCities = cities.size();
        double[][] pheromoneMatrix = new double[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] = 0.1;
            }
        }
        return pheromoneMatrix;
    }

    //Simulate pheromone evaporation in order to provide diversity and not get stuck in similar solutions
    public static void evaporatePheromones(double[][] pheromoneMatrix, double evaporationRate, List<City> cities) {
        int numberOfCities = cities.size();
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] = (1 - evaporationRate) * pheromoneMatrix[i][j];
            }
        }
    }

    //Simulate pheromone deposit assuring that the shorter path leaves more pheromones on the way
    public static void depositPheromones(double[][] pheromoneMatrix, int numberOfAnts, List<City> cities, int[][] cityRoutes, double[] cityDistances) {
        int numberOfCities = cities.size();
        for (int ant = 0; ant < numberOfAnts; ant++) {
            double pheromoneDeposit = 1.0 / cityDistances[ant];
            for (int i = 0; i < numberOfCities - 1; i++) {
                pheromoneMatrix[cityRoutes[ant][i]][cityRoutes[ant][i + 1]] += pheromoneDeposit;
            }
            pheromoneMatrix[cityRoutes[ant][numberOfCities - 1]][cityRoutes[ant][0]] += pheromoneDeposit;
        }
    }
}