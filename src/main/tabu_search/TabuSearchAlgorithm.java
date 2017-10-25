package main.tabu_search;

import main.Algorithm;
import main.model.City;
import main.model.Route;
import main.mutation.Mutation;
import main.mutation.MutationFactory;
import main.mutation.MutationType;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Arrays;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class TabuSearchAlgorithm implements Algorithm {
    public static final int TABU_LIST_LENGTH = 30;
    public static final int SAMPLES_NUMBER = 500;
    public static final int K_NEAREST_NEIGHBOURS = 50;

    private Route bestRoute;
    private double bestDistance = Double.MAX_VALUE;

    public void tabuSearch(City[] cities) {
        for (int sampleNumber = 0; sampleNumber < SAMPLES_NUMBER; sampleNumber++) {
            CircularFifoQueue<Route> tabu = new CircularFifoQueue<>(TABU_LIST_LENGTH);
            Route current = new Route(cities);
            current.shuffleCities();

            boolean foundedLocalOptimum = false;
            while (!foundedLocalOptimum){
                Route[] nearestNeighbours = findKNearestNeighbours(current);
                Arrays.sort(nearestNeighbours);

                boolean found = false;
                for (int index = 0; index < nearestNeighbours.length && !found; index++) {
                    if (nearestNeighbours[index].getTotalDistance() < current.getTotalDistance()
                            && !tabu.contains(nearestNeighbours[index])) {

                        tabu.add(nearestNeighbours[index]);
                        current = nearestNeighbours[index];
                        found = true;
                    }
                }
                if (!found){
                    foundedLocalOptimum = true;
                }
            }


            saveStatistics(tabu);
        }


    }

    private Route[] findKNearestNeighbours(Route route) {
        Route[] nearestNeighbours = new Route[K_NEAREST_NEIGHBOURS];
        Mutation mutation = MutationFactory.createMutation(MutationType.INVERSE_MUTATION);

        for (int i = 0; i < K_NEAREST_NEIGHBOURS; i++) {
            Route nearestNeighbour = new Route(route.getCities());
            mutation.mutate(nearestNeighbour);
            nearestNeighbours[i] = nearestNeighbour;
        }

        return nearestNeighbours;
    }

    private void saveStatistics(CircularFifoQueue<Route> tabu) {
        for (Route route : tabu) {
            if (route.getTotalDistance() < bestDistance) {
                bestDistance = route.getTotalDistance();
                bestRoute = route;
            }
        }
    }


    @Override
    public double getBestDistance() {
        return bestRoute.getTotalDistance();
    }

    @Override
    public Route getFinalRoute() {
        return bestRoute;
    }
}
