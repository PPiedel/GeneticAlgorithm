package main.mutation;

import main.model.City;
import main.model.Route;

import static main.Util.randomWithRange;
import static main.genetic_algorithm.GeneticAlgorithm.MUTATION_PROBABILITY;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class InverseMutation implements Mutation {
    @Override
    public void mutate(Route route) {
        if (shouldBeMutated()){
            int start = randomWithRange(0, route.length() - 1);
            int end = randomWithRange(0, route.length() - 1);

            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }

            for (int x=0; x<=(end-start)/2; x++)
            {
                int index1 = start+x;
                int index2 = end-x;

                City temp = route.getCities()[index1];
                route.setCity(route.getCityAtIndex(index2),index1);
                route.setCity(temp,index2);
            }
        }
    }

    public boolean shouldBeMutated() {
        return Math.random() < MUTATION_PROBABILITY;
    }


}
