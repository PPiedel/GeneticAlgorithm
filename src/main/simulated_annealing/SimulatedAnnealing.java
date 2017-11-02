package main.simulated_annealing;

import main.model.City;
import main.model.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.tabu_search.TabuSearch.findKNearestNeighbours;

/**
 * Created by Pawel_Piedel on 26.10.2017.
 */
public class SimulatedAnnealing {
    //params
    private static final int NEIGHBOUR_HOOD_NUMBER = 5;
    private static final int INIT_TEMPERATURE = 1000;
    private static final double STABLE_ALLOY_CRITERIA_TEMP = 0.1;
    private static final double ALPHA = 0.999;
    //stats
    static List<Double> currentDistances = new ArrayList<>();

    public Route simulatedAnnealing(City[] cities) {
        Route current = new Route(cities);
        current.shuffleCities();

        double temperature = INIT_TEMPERATURE;
        while (!stableAlloyCriteria(temperature)) {
            Route[] nearestNeighbours = findKNearestNeighbours(current, NEIGHBOUR_HOOD_NUMBER);
            Arrays.sort(nearestNeighbours);
            Route bestNeighbour = nearestNeighbours[0];

            if (bestNeighbour.getTotalDistance() < current.getTotalDistance()) {
                current = bestNeighbour;
            } else if (acceptWorseSolution(current, bestNeighbour, temperature)) {
                current = bestNeighbour;
            }


            temperature = updateTemperature(temperature);

            saveStatistics(current);
        }
        return current;
    }

    private void saveStatistics(Route current) {
        currentDistances.add(current.getTotalDistance());
    }

    private double updateTemperature(double temperature) {
        return temperature * ALPHA;
    }

    private boolean stableAlloyCriteria(double temperature) {
        return temperature < STABLE_ALLOY_CRITERIA_TEMP;
    }

    private boolean acceptWorseSolution(Route current, Route neighbour, double temperature) {
        return Math.random() < Math.exp((current.getTotalDistance() - neighbour.getTotalDistance()) / temperature);
    }

}
