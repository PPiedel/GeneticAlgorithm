package main.mutation;

import main.City;
import main.Route;

import static main.GeneticAlgorithm.MUTATION_PROBABILITY;
import static main.Util.randomWithRange;

/**
 * Created by Pawel_Piedel on 19.10.2017.
 */
public class ScrambleMutation implements Mutation {
    @Override
    public void mutate(Route route) {
        for (int i = 0; i < route.length(); i++) {
            if (shouldBeMutated()) {
                swapCityAtGivenIndexWithRandomAnotherCity(route, i);
            }
        }
    }

    private void swapCityAtGivenIndexWithRandomAnotherCity(Route route, int firstCityIndex) {
        City firstCity = route.getCityAtIndex(firstCityIndex);
        int secondPosition = randomWithRange(0, route.length() - 1);

        route.setCity(route.getCityAtIndex(secondPosition), firstCityIndex);
        route.setCity(firstCity, secondPosition);
    }

    public boolean shouldBeMutated() {
        return Math.random() < MUTATION_PROBABILITY;
    }
}
