package test;

import main.City;
import main.GeneticAlgorithm;
import main.Route;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GeneticAlgorithmTest {

    @Test
    public void mutate() throws Exception {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        geneticAlgorithm.setMutationProbability(1);

        //prepare route
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);
        City city3 = new City(20, 20);
        Route route = new Route(Arrays.asList(city1, city2, city3));
        //System.out.println(route.toString());

        //mutate route with probability 1
        geneticAlgorithm.mutate(route);

       // System.out.println(route.toString());

        //assert that after mutations each city is still in the route
        assertTrue(route.contains(city1));
        assertTrue(route.contains(city2));
        assertTrue(route.contains(city3));

    }

}