package main.simulated_annealing;

import main.model.City;
import main.model.Route;
import main.mutation.Mutation;
import main.mutation.MutationFactory;
import main.mutation.MutationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel_Piedel on 26.10.2017.
 */
public class SimulatedAnnealing {
    public static final int NEIGHBOUR_HOOD_NUMBER = 5;
    public static final int INIT_TEMPERATURE = 1000;
    public static final double STABLE_ALLOY_CRITERIA_TEMP = 0.1;
    public static final double ALPHA = 0.999;
    public static List<Double> current = new ArrayList<>();

    public Route simulatedAnnealing(City[] cities) {
        Mutation mutation = MutationFactory.createMutation(MutationType.INVERSE_MUTATION);

        Route current = new Route(cities);
        Route neighbour = null;
        current.shuffleCities();

        double temperature = INIT_TEMPERATURE;
        while (!stableAlloyCriteria(temperature)) {
            for (int i = 0; i < NEIGHBOUR_HOOD_NUMBER; i++) {
                neighbour = new Route(current.getCities());
                mutation.mutate(neighbour);

                if (neighbour.getTotalDistance() < current.getTotalDistance()) {
                    current = neighbour;
                } else if (acceptWorseSolution(current, neighbour, temperature)) {
                    current = neighbour;
                }
            }
            temperature = updateTemperature(temperature);

            saveStatistics(current);
        }
        return current;
    }

    private void saveStatistics(Route current) {
        SimulatedAnnealing.current.add(current.getTotalDistance());
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
