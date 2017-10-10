package main;

import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm {

    public static int POPULATION_SIZE = 50;
    public static int GENERATIONS_NUMBER = 50;
    public static double[] bests = new double[GENERATIONS_NUMBER];
    public static double[] avgs = new double[GENERATIONS_NUMBER];
    public static double[] worsts = new double[GENERATIONS_NUMBER];
    public static double MUTATION_PROBABILITY = 0.2;
    public static double CROSSOVER_PROBABILTY = 0.1;
    public static int TOURNAMENT_SIZE = 5;


    public GeneticAlgorithm() {}


    public void ga(City[] cities) {
        Population population = new Population(cities.length);
        population.initializeRoutesInRandomOrder(cities);

        for (int i = 0; i < GENERATIONS_NUMBER; i++) {
            Population newPopulation = new Population(POPULATION_SIZE);

            int numberOfIndividuals = 0;
            while (numberOfIndividuals < POPULATION_SIZE) {
                Route winner = population.selectRouteViaTournament();
                if (shouldBeCrossovered() && numberOfIndividuals+2 < POPULATION_SIZE){
                    Route winner2 = population.selectRouteViaTournament();
                    Route[] childs = crossover(winner,winner2);
                    newPopulation.setRoute(childs[0],numberOfIndividuals);
                    newPopulation.setRoute(childs[1],numberOfIndividuals+1);
                    numberOfIndividuals+=2;
                }
                else {
                    if (shouldBeMutated()){
                        mutate(winner);
                    }
                    newPopulation.setRoute(winner,numberOfIndividuals);
                    numberOfIndividuals++;
                }

            }

            population = newPopulation;
            bests[i] = population.getBestDistance();
            avgs[i] = population.getAverageDistance();
            worsts[i] = population.getWorseDistance();
        }
    }

    public void mutate(Route route) {
        for (int i = 0; i < route.length(); i++) {
            if (shouldBeMutated()) {
                int secondPosition = randomWithRange(0, route.length() - 1);
                City firstCity = route.getCityAtIndex(i);

                //swap cities
                route.setCity(route.getCityAtIndex(secondPosition), i);
                route.setCity(firstCity, secondPosition);

            }
        }
    }

    public boolean shouldBeMutated() {
        return Math.random() < MUTATION_PROBABILITY;
    }

    public boolean shouldBeCrossovered(){
        return Math.random() < CROSSOVER_PROBABILTY;
    }

    public Route[] crossover(Route parent1, Route parent2) {
        Route[] childs = new Route[2];
        Route child1 = new Route(parent1.length());
        Route child2 = new Route(parent1.length());

        int start = randomWithRange(0, parent1.length() - 1);
        int end = randomWithRange(0, parent1.length() - 1);

        if (start > end) {
            //swap values
            int temp = start;
            start = end;
            end = temp;
        }
        assert start <= end;

        //create childs
        for (int i = 0; i < parent1.length(); i++) {
            if (i >= start && i <= end) {
                child1.setCity(parent1.getCityAtIndex(i), i);
                child2.setCity(parent2.getCityAtIndex(i), i);
            }
        }
        for (int j = 0; j < parent1.length(); j++) {
            if (!child1.contains(parent2.getCityAtIndex(j))) {
                int firstFreeIndex = child1.findFirstFreeIndex(j);
                if (firstFreeIndex != -1) {
                    child1.setCity(parent2.getCityAtIndex(j), firstFreeIndex);
                }
            }
            if (!child2.contains(parent1.getCityAtIndex(j))) {
                int firstFreeIndex = child2.findFirstFreeIndex(j);
                if (firstFreeIndex != -1) {
                    child2.setCity(parent1.getCityAtIndex(j), firstFreeIndex);
                }
            }
        }


        childs[0] = child1;
        childs[1] = child2;

        return childs;
    }


}
