package main.genetic_algorithm;

import main.Algorithm;
import main.City;
import main.Population;
import main.Route;
import main.mutation.Mutation;
import main.mutation.MutationFactory;
import main.mutation.MutationType;

import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm implements Algorithm {
    //params
    public static int POPULATION_SIZE = 100;
    public static int GENERATIONS_NUMBER = 5000;
    public static double MUTATION_PROBABILITY = 0.1;
    public static double CROSSOVER_PROBABILTY = 0.5;
    public static int TOURNAMENT_SIZE = 15;
    public static final boolean ELITISM = false;
    //stats
    public static double[] bests = new double[GENERATIONS_NUMBER];
    public static double[] avgs = new double[GENERATIONS_NUMBER];
    public static double[] worsts = new double[GENERATIONS_NUMBER];
    public static Route finalRoute;

    public void ga(City[] cities) {
        Population population = new Population(POPULATION_SIZE);
        population.initializeRoutesInRandomOrder(cities);

        for (int generationNumber = 0; generationNumber < GENERATIONS_NUMBER; generationNumber++) {
            population = evolveIntoNewPopulation(population, ELITISM);
            savePopulationStatistics(population, generationNumber);
        }
    }

    private Population evolveIntoNewPopulation(Population population, boolean elitism) {
        Population newPopulation = new Population(POPULATION_SIZE);
        int numberOfIndividuals = 0;
        if (elitism) {
            Route eliteRoute = population.getBestRoute();
            newPopulation.setRoute(eliteRoute, numberOfIndividuals);
            numberOfIndividuals++;
        }

        Mutation mutation = MutationFactory.createMutation(MutationType.INVERSE_MUTATION);

        while (numberOfIndividuals < POPULATION_SIZE) {

            Route winner = population.selectRouteViaTournament();

            if (shouldBeCrossovered() && numberOfIndividuals + 2 < POPULATION_SIZE) {
                Route winner2 = population.selectRouteViaTournament();
                Route[] childs = crossover(winner, winner2);

                mutation.mutate(childs[0]);
                mutation.mutate(childs[1]);

                newPopulation.setRoute(childs[0], numberOfIndividuals);
                newPopulation.setRoute(childs[1], numberOfIndividuals + 1);

                numberOfIndividuals += 2;
            } else {
                newPopulation.setRoute(winner, numberOfIndividuals);
                numberOfIndividuals++;
            }

        }

        return newPopulation;
    }


    private void savePopulationStatistics(Population population, int i) {
        bests[i] = population.getBestDistance();
        avgs[i] = population.getAverageDistance();
        worsts[i] = population.getWorseDistance();

        finalRoute = population.getBestRoute();
    }

    public void mutation(Route route, MutationType type) {
        switch (type) {
            case SCRAMBLE_MUTATION:
                scrambleMutation(route);
                break;
            default:
                //ignore
        }

    }

    private void scrambleMutation(Route route) {
        for (int i = 0; i < route.length(); i++) {
            if (shouldBeMutated()) {
                swapCityAtGivenIndexWithRandomAnotherCity(route, i);
            }
        }
    }

    private void swapCityAtGivenIndexWithRandomAnotherCity(Route route, int firstCityIndex) {
        City firstCity = route.getCityAtIndex(firstCityIndex);
        int secondPosition = randomWithRange(0, route.length() - 1);

        route.setCity(route.getCityAtIndex(secondPosition), firstCityIndex);
        route.setCity(firstCity, secondPosition);
    }

    public boolean shouldBeMutated() {
        return Math.random() < MUTATION_PROBABILITY;
    }

    public boolean shouldBeCrossovered() {
        return Math.random() < CROSSOVER_PROBABILTY;
    }

    public Route[] crossover(Route parent1, Route parent2) {
        int crossPoint = randomWithRange(0, parent1.length() - 1);

        return createChildsFromParents(parent1, parent2, crossPoint);
    }


    private Route[] createChildsFromParents(Route parent1, Route parent2, int crossPoint) {
        Route child1 = new Route(parent1.length());
        Route child2 = new Route(parent1.length());

        for (int i = 0; i < parent1.length(); i++) {
            if (i <= crossPoint) {
                child1.setCity(parent1.getCityAtIndex(i), i);
                child2.setCity(parent2.getCityAtIndex(i), i);
            }
        }
        if (crossPoint < parent1.length() - 1) {
            int child1FirstFreeIndex = crossPoint + 1;
            int child2FirstFreeIndex = crossPoint + 1;
            for (int j = 0; j < parent1.length(); j++) {
                if (!child1.contains(parent2.getCityAtIndex(j))) {
                    child1.setCity(parent2.getCityAtIndex(j), child1FirstFreeIndex);
                    child1FirstFreeIndex++;
                }
                if (!child2.contains(parent1.getCityAtIndex(j))) {
                    child2.setCity(parent1.getCityAtIndex(j), child2FirstFreeIndex);
                    child2FirstFreeIndex++;
                }
            }
        }

        return new Route[]{child1, child2};
    }

    /*public void paes(City[] cities, int archiveSize) {
        Set<Route> archive = new HashSet<>();
        Route solution = new Route(cities);
        solution.shuffleCities();

        Route candidate;

        for (int generationNumber = 0; generationNumber < GENERATIONS_NUMBER; generationNumber++) {
            candidate = new Route(solution.getCities());
            mutation(candidate,MutationType.SCRAMBLE_MUTATION);

            if (solution.dominates(candidate)){

            }
        }

    }*/

    public double[][] calculateDistancesBetweenCities(City[] cities) {
        double[][] distances = new double[cities.length][cities.length];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                distances[i][j] = cities[i].distanceTo(cities[j]);
            }
        }
        return distances;
    }

    @Override
    public double getBestDistance() {
        return bests[GENERATIONS_NUMBER - 1];
    }

    @Override
    public Route getFinalRoute() {
        return finalRoute;
    }
}
