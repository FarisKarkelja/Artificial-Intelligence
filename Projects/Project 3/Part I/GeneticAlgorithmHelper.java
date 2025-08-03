import java.util.*;

public class GeneticAlgorithmHelper {
    private static final int SELECTED_SIZE = 3;
    private static final double CROSSOVER_PROBABILITY = 0.85;
    private static final double INVERSION_PROBABILITY = 0.15;
    private static final double EXCHANGE_PROBABILITY = 0.15;

    //Calculate total distance between different cities
    public static double calculateTotalDistance(List<City> cityRoute) {
        double totalDistance = 0.0;
        for (int i = 0; i < cityRoute.size() - 1; i++) {
            City currentCity = cityRoute.get(i);
            City nextCity = cityRoute.get((i + 1) % cityRoute.size());
            totalDistance += currentCity.calculateDistance(nextCity);
        }
        return totalDistance;
    }

    //Randomly generate an initial population to start with
    public static List<List<City>> createInitialPopulation(List<City> cities, int populationSize) {
        List<List<City>> initialPopulation = new ArrayList<>();
        City firstCity = cities.get(0);
        for (int i = 0; i < populationSize; i++) {
            List<City> cityRoute = new ArrayList<>(cities.subList(1, cities.size()));
            Collections.shuffle(cityRoute);
            cityRoute.add(0, firstCity);
            cityRoute.add(firstCity);
            initialPopulation.add(cityRoute);
        }
        return initialPopulation;
    }

    //Select the best possible individual
    public static List<City> conductSelection(List<List<City>> population) {
        List<List<City>> selectedIndividuals = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < SELECTED_SIZE; i++) {
            selectedIndividuals.add(population.get(random.nextInt(population.size())));
        }
        List<City> bestIndividual = selectedIndividuals.get(0);
        double bestCityDistance = calculateTotalDistance(bestIndividual);

        for (List<City> individual : selectedIndividuals) {
            double distance = calculateTotalDistance(individual);
            if (distance < bestCityDistance) {
                bestIndividual = individual;
                bestCityDistance = distance;
            }
        }
        return bestIndividual;
    }

    //Implement crossover between two parent city routes while creating an offspring being a combination of both
    public static List<City> performCrossover(List<City> firstParent, List<City> secondParent) {
        Random random = new Random();
        int startPoint = random.nextInt(firstParent.size() - 2) + 1;
        int endPoint = random.nextInt(firstParent.size() - 2) + 1;
        if (startPoint > endPoint) {
            int temp = startPoint;
            startPoint = endPoint;
            endPoint = temp;
        }

        List<City> offspring = new ArrayList<>(firstParent.size());
        Set<City> setOfOffsprings = new HashSet<>();

        for (int i = 0; i < firstParent.size(); i++) {
            offspring.add(null);
        }

        offspring.set(0, firstParent.get(0));
        offspring.set(offspring.size() - 1, firstParent.get(0));
        setOfOffsprings.add(firstParent.get(0));

        for (int i = startPoint; i <= endPoint; i++) {
            offspring.set(i, firstParent.get(i));
            setOfOffsprings.add(firstParent.get(i));
        }

        int cityIndex = 1;
        for (City secondParentCity : secondParent) {
            if (!setOfOffsprings.contains(secondParentCity)) {
                while (offspring.get(cityIndex) != null) {
                    cityIndex++;
                }
                offspring.set(cityIndex, secondParentCity);
                setOfOffsprings.add(secondParentCity);
            }
        }
        return offspring;
    }

    //Reverse a city route segment to avoid local optima
    public static void implementInverseMutation(List<City> cityRoute) {
        Random random = new Random();
        int firstCityIndex = random.nextInt(cityRoute.size() - 2) + 1;
        int secondCityIndex = random.nextInt(cityRoute.size() - 2) + 1;
        if (firstCityIndex > secondCityIndex) {
            int temp = firstCityIndex;
            firstCityIndex = secondCityIndex;
            secondCityIndex = temp;
        }
        Collections.reverse(cityRoute.subList(firstCityIndex, secondCityIndex + 1));
    }

    //Make tiny changes to the city route providing diversity
    public static void implementExchangeMutation(List<City> cityRoute) {
        Random random = new Random();
        int firstCityIndex = random.nextInt(cityRoute.size() - 2) + 1;
        int secondCityIndex = random.nextInt(cityRoute.size() - 2) + 1;
        Collections.swap(cityRoute, firstCityIndex, secondCityIndex);
    }

    //Choose the best offspring from the population of individuals
    public static List<List<City>> evolvePopulation(List<List<City>> population, int populationSize) {
        List<List<City>> newPopulation = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < populationSize / 2; i++) {
            List<City> firstParent = conductSelection(population);
            List<City> secondParent = conductSelection(population);

            List<City> firstOffspring, secondOffspring;
            if (random.nextDouble() < CROSSOVER_PROBABILITY) {
                firstOffspring = performCrossover(firstParent, secondParent);
                secondOffspring = performCrossover(secondParent, firstParent);
            } else {
                firstOffspring = new ArrayList<>(firstParent);
                secondOffspring = new ArrayList<>(secondParent);
            }

            if (random.nextDouble() < INVERSION_PROBABILITY)
                implementInverseMutation(firstOffspring);
            if (random.nextDouble() < INVERSION_PROBABILITY)
                implementInverseMutation(secondOffspring);
            if (random.nextDouble() < EXCHANGE_PROBABILITY)
                implementExchangeMutation(firstOffspring);
            if (random.nextDouble() < EXCHANGE_PROBABILITY)
                implementExchangeMutation(secondOffspring);

            if (calculateTotalDistance(firstOffspring) < calculateTotalDistance(secondOffspring)) {
                newPopulation.add(firstOffspring);
                newPopulation.add(secondParent);
            } else {
                newPopulation.add(secondOffspring);
                newPopulation.add(firstParent);
            }
        }
        return newPopulation;
    }
}