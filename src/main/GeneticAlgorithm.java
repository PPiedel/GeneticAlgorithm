package main;

import java.util.Random;

import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm implements Algorithm {
    private double mutationProbability;
    private double crossoverProbabilty;
    private int populationSize;

    public static final int GENERATIONS_NUMBER = 100;
    public static final int TOURNAMENT_SIZE = 10;

    private double[] bestDistances;

    public GeneticAlgorithm(int populationSize) {
        this.populationSize = populationSize;
        bestDistances = new double[populationSize];
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    @Override
    public double calculateShortestRoute() {
        return 0;
    }

    public void ga(City[] cities) {
        Population initialPopulation = new Population(cities.length);
        initialPopulation.initializeRoutesInRandomOrder(cities);

        for (int i = 0; i < GENERATIONS_NUMBER; i++) {

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
        return Math.random() < mutationProbability;
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
        System.out.println("Start : "+start);
        System.out.println("End "+end);
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
