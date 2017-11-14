package main.tabu_search;

import main.model.City;
import main.model.Route;
import main.mutation.Mutation;
import main.mutation.MutationFactory;
import main.mutation.MutationType;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class TabuSearch {
    public static final int TABU_LIST_LENGTH = 100;
    public static final int K_NEAREST_NEIGHBOURS = 200;
    //public static final int SAMPLES_NUMBER = 1000;

    public static List<Double> currentStats = new ArrayList<>();
    public static List<Double> bestStats = new ArrayList<>();

    public static Route[] findKNearestNeighbours(Route route, int kNearestNeighbours) {
        Route[] nearestNeighbours = new Route[kNearestNeighbours];
        Mutation mutation = MutationFactory.createMutation(MutationType.INVERSE_MUTATION);

        for (int i = 0; i < kNearestNeighbours; i++) {
            Route nearestNeighbour = new Route(route.getCities());
            mutation.mutate(nearestNeighbour);
            nearestNeighbours[i] = nearestNeighbour;
        }

        return nearestNeighbours;
    }

    public Route tabuSearch(City[] cities, long time) {
        CircularFifoQueue<Route> tabu = new CircularFifoQueue<>(TABU_LIST_LENGTH);
        Route current = new Route(cities);
        current.shuffleCities();
        Route best = new Route(current.getCities());
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        int sampleNumber = 0;
        while (elapsedTime < time) {
            Route[] nearestNeighbours = findKNearestNeighbours(current, K_NEAREST_NEIGHBOURS);
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
                    elapsedTime = (new Date()).getTime() - startTime;
                    saveStatistics(current, best, sampleNumber);
                }
            }
            sampleNumber++;

        }

        return best;
    }

    private void saveStatistics(Route current, Route best, int sampleNumber) {
        currentStats.add(current.getTotalDistance());
        bestStats.add(best.getTotalDistance());
    }


}
