package main;

import java.util.List;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithm implements Algorithm {
    private double mutationProbability;
    private double crossoverProbabilty;
    private int populationSize;
    public static final int generationsNumber = 100;

    private double[] bestDistances;

    public GeneticAlgorithm(int populationSize) {
        this.populationSize = populationSize;
        bestDistances  = new double[populationSize];
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    @Override
    public double calculateShortestRoute() {
        return 0;
    }

    public void ga(List<City> cities){
        Population initialPopulation = new Population(cities.size());
        initialPopulation.initializeRoutesInRandomOrder(cities);

        for (int i=0; i<generationsNumber; i++){

        }
    }

    public void  mutate(Route route) {
        for (int i = 0; i < route.length(); i++) {
            if (shouldBeMutated()) {
                int secondPosition = randomWithRange(0, route.length() - 1);
                City firstCity = route.getCityAtIndex(i);

                //swap cities
                route.setCity(route.getCityAtIndex(secondPosition),i);
                route.setCity(firstCity,secondPosition);

            }
        }
    }

    public boolean shouldBeMutated() {
        return Math.random() < mutationProbability;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
