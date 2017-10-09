package test;

import main.City;
import main.Population;
import main.Route;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class PopulationTest {

    @Test
    public void evaluate() throws Exception {
        //prepare cities
        City city1 = new City(0, 0);
        City city2 = new City(0,20);
        City city3 = new City(10, 20);
        City city4 = new City(10,0);

        //prepare routes
        Route route1 = new Route(Arrays.asList(city1,city2,city3,city4)); //60
        Route route2 = new Route(Arrays.asList(city1,city3,city2,city4)); //64,72

        //create population
        Population population = new Population(new Route[]{route1,route2});

        double bestDistance = population.getBestDistance();

        assertEquals(60,bestDistance);

    }

}