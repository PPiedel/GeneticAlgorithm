package main;

import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm implements Algorithm {

    //params
    public static int POPULATION_SIZE = 200;
    public static int GENERATIONS_NUMBER = 100;
    public static double MUTATION_PROBABILITY = 0.02;
    public static double CROSSOVER_PROBABILTY = 0.6;
    public static int TOURNAMENT_SIZE = 15;

    //stats
    public static double[] bests = new double[GENERATIONS_NUMBER];
    public static double[] avgs = new double[GENERATIONS_NUMBER];
    public static double[] worsts = new double[GENERATIONS_NUMBER];
    public static Route finalRoute;

    public void ga(City[] cities) {
        Population population = new Population(POPULATION_SIZE);
        population.initializeRoutesInRandomOrder(cities);

        for (int generationNumber = 0; generationNumber < GENERATIONS_NUMBER; generationNumber++) {
            population = evolveIntoNewPopulation(population);
            savePopulationStatistics(population, generationNumber);
        }
    }


    private Population evolveIntoNewPopulation(Population population) {
        Population newPopulation = new Population(POPULATION_SIZE);
        int numberOfIndividuals = 0;
        while (numberOfIndividuals < POPULATION_SIZE) {

            Route winner = population.selectRouteViaTournament();

            if (shouldBeCrossovered() && numberOfIndividuals + 2 < POPULATION_SIZE) {
                Route winner2 = population.selectRouteViaTournament();
                Route[] childs = crossover(winner, winner2);
                mutation(childs[0], MutationType.SCRAMBLE_MUTATION);
                mutation(childs[1], MutationType.SCRAMBLE_MUTATION);

                newPopulation.setRoute(childs[0], numberOfIndividuals);
                newPopulation.setRoute(childs[1], numberOfIndividuals + 1);

                numberOfIndividuals += 2;
            } else {
                mutation(winner, MutationType.SCRAMBLE_MUTATION);

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
                for (int i = 0; i < route.length(); i++) {
                    if (shouldBeMutated()) {
                        swapCityAtGivenIndexWithRandomAnotherCity(route, i);
                    }
                }
                break;
            case INVERSE_MUTATION:
                break;
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
        int start = randomWithRange(0, parent1.length() - 1);
        int end = randomWithRange(0, parent1.length() - 1);

        if (start > end) {
            //swap values
            int temp = start;
            start = end;
            end = temp;
        }
        assert start <= end;

        return createChildsFromParents(parent1, parent2, start, end);
    }


    private Route[] createChildsFromParents(Route parent1, Route parent2, int start, int end) {
        Route child1 = new Route(parent1.length());
        Route child2 = new Route(parent1.length());

        for (int i = 0; i < parent1.length(); i++) {
            if (i >= start && i <= end) {
                child1.setCity(parent1.getCityAtIndex(i), i);
                child2.setCity(parent2.getCityAtIndex(i), i);
            }
        }
        for (int j = 0; j < parent1.length(); j++) {
            if (!child1.contains(parent2.getCityAtIndex(j))) {
                int firstFreeIndex = child1.findFirstFreeIndex();
                if (firstFreeIndex != -1) {
                    child1.setCity(parent2.getCityAtIndex(j), firstFreeIndex);
                }
            }
            if (!child2.contains(parent1.getCityAtIndex(j))) {
                int firstFreeIndex = child2.findFirstFreeIndex();
                if (firstFreeIndex != -1) {
                    child2.setCity(parent1.getCityAtIndex(j), firstFreeIndex);
                }
            }
        }

        return new Route[]{child1, child2};
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
