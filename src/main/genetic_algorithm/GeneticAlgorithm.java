package main.genetic_algorithm;

import main.model.City;
import main.model.Population;
import main.model.Route;
import main.mutation.Mutation;
import main.mutation.MutationFactory;
import main.mutation.MutationType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm {
    //params
    public static int POPULATION_SIZE = 600;
    public static int GENERATIONS_NUMBER = 300;
    public static double MUTATION_PROBABILITY = 0.8;
    public static double CROSSOVER_PROBABILTY = 0.6;
    public static int TOURNAMENT_SIZE = 51;
    public static boolean ELITISM = true;
    //currentStats
    public static List<Double> bests = new ArrayList<>();
    public static List<Double> avgs = new ArrayList<>();
    public static List<Double> worsts = new ArrayList<>();
    public static Route finalRoute;

    public void ga(City[] cities, long time) {
        Population population = new Population(POPULATION_SIZE);
        population.initializeRoutesInRandomOrder(cities);
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        int generationNumber = 0;
        while (elapsedTime < time) {
            population = evolveIntoNewPopulation(population, ELITISM);
            elapsedTime = (new Date()).getTime() - startTime;
            savePopulationStatistics(population, generationNumber);
            generationNumber++;
        }

        /*for (int generationNumber = 0; generationNumber < GENERATIONS_NUMBER; generationNumber++) {
            population = evolveIntoNewPopulation(population, ELITISM);
            savePopulationStatistics(population, generationNumber);


        }*/
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
        bests.add(population.getBestDistance());
        avgs.add(population.getAverageDistance());
        worsts.add(population.getWorseDistance());

        if (finalRoute == null) {
            finalRoute = population.getBestRoute();
        } else if (population.getBestDistance() < finalRoute.getTotalDistance()) {
            finalRoute = population.getBestRoute();
        }

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

    public double[][] calculateDistancesBetweenCities(City[] cities) {
        double[][] distances = new double[cities.length][cities.length];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                distances[i][j] = cities[i].distanceTo(cities[j]);
            }
        }
        return distances;
    }


    public double getBestDistance() {
        return finalRoute.getTotalDistance();
    }


    public Route getFinalRoute() {
        return finalRoute;
    }
}
