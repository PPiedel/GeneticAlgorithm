package main.tabu_search;

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
public class TabuSearch {
    public static final int TABU_LIST_LENGTH = 10;
    public static final int K_NEAREST_NEIGHBOURS = 5000;
    public static final int SAMPLES_NUMBER = 100;

    public static double[] currentStats = new double[SAMPLES_NUMBER];
    public static double[] bestStats = new double[SAMPLES_NUMBER];

    public Route tabuSearch(City[] cities) {
        CircularFifoQueue<Route> tabu = new CircularFifoQueue<>(TABU_LIST_LENGTH);
        Route current = new Route(cities);
        current.shuffleCities();
        Route best = new Route(current.getCities());

        for (int sampleNumber = 0; sampleNumber < SAMPLES_NUMBER; sampleNumber++) {
            Route[] nearestNeighbours = findKNearestNeighbours(current);
            Arrays.sort(nearestNeighbours);

            boolean found = false;
            for (int index = 0; index < nearestNeighbours.length && !found; index++) {
                if (!tabu.contains(nearestNeighbours[index])) {
                    found = true;
                    current = nearestNeighbours[index];
                    if (current.getTotalDistance() < best.getTotalDistance()) {
                        best = current;
                    }

                    tabu.add(nearestNeighbours[index]);

                    saveStatistics(current, best, sampleNumber);
                }
            }

        }

        return best;
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

    private void saveStatistics(Route current, Route best, int sampleNumber) {
        currentStats[sampleNumber] = current.getTotalDistance();
        bestStats[sampleNumber] = best.getTotalDistance();
    }



}
