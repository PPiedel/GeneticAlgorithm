package main.simulated_annealing;

import main.model.City;
import main.model.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static main.genetic_algorithm.GAMain.TIME;
import static main.tabu_search.TabuSearch.findKNearestNeighbours;

/**
 * Created by Pawel_Piedel on 26.10.2017.
 */
public class SimulatedAnnealing {
    //params
    private static final int NEIGHBOUR_HOOD_NUMBER = 30;
    private static final int INIT_TEMPERATURE = 2000;
    private static final double STABLE_ALLOY_CRITERIA_TEMP = 0.01;
    private static final double ALPHA = 0.999;
    //stats
    static List<Double> currentDistances = new ArrayList<>();

    public Route simulatedAnnealing(City[] cities) {
        Route current = new Route(cities);
        current.shuffleCities();
        Route bestNeighbour;
        Route[] nearestNeighbours;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        double temperature = INIT_TEMPERATURE;
        while (elapsedTime < TIME) {
            nearestNeighbours = findKNearestNeighbours(current, NEIGHBOUR_HOOD_NUMBER);
            Arrays.sort(nearestNeighbours);
            bestNeighbour = nearestNeighbours[0];

            if (bestNeighbour.getTotalDistance() < current.getTotalDistance()) {
                current = bestNeighbour;
            } else if (acceptWorseSolution(current, bestNeighbour, temperature)) {
                current = bestNeighbour;
            }


            temperature = updateTemperature(temperature);
            //System.out.println(temperature);
            elapsedTime = (new Date()).getTime() - startTime;
            saveStatistics(current);
        }
        return current;
    }

    private boolean stableAlloyCriteria(double temperature) {
        return temperature < STABLE_ALLOY_CRITERIA_TEMP;
    }

    private boolean acceptWorseSolution(Route current, Route neighbour, double temperature) {
        double exp = Math.exp((current.getTotalDistance() - neighbour.getTotalDistance()) / temperature);
        return Math.random() < exp;
    }

    private double updateTemperature(double temperature) {
        return temperature * ALPHA;
    }

    private void saveStatistics(Route current) {
        currentDistances.add(current.getTotalDistance());
    }

}
