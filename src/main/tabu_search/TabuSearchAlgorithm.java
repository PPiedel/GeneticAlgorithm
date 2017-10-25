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
public class TabuSearchAlgorithm {
    public static final int TABU_LIST_LENGTH = 30;
    public static final int SAMPLES_NUMBER = 500;
    public static final int K_NEAREST_NEIGHBOURS = 30;

    private Route bestRoute;
    private double bestDistance = Double.MAX_VALUE;


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
                if (!tabu.contains(nearestNeighbours[index])) { //najlepszy sasiad, ktore nie jest w tabu
                    found = true;
                    current = nearestNeighbours[index];
                    if (current.getTotalDistance() < best.getTotalDistance()) {
                        best = current;
                    }

                    tabu.add(nearestNeighbours[index]);
                }
            }
            if (!found) { //wszyscy sasiedzi w tabu

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

}
