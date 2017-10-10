package test;

import main.City;
import main.GeneticAlgorithm;
import main.Population;
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
        GeneticAlgorithm.MUTATION_PROBABILITY = 1;

        //prepare route
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);
        City city3 = new City(20, 20);
        Route route = new Route(new City[]{city1, city2, city3});

        //mutate route with probability 1
        geneticAlgorithm.mutate(route);

        //assert that after mutations each city is still in the route
        assertTrue(route.contains(city1));
        assertTrue(route.contains(city2));
        assertTrue(route.contains(city3));

    }

    @Test
    public void crossover() throws  Exception {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        GeneticAlgorithm.MUTATION_PROBABILITY = 1;

        //prepare cities
        City city1 = new City(0, 0);
        City city2 = new City(0,20);
        City city3 = new City(10, 20);
        City city4 = new City(10,0);

        //prepare routes
        Route route1 = new Route(new City[]{city1,city2,city3,city4}); //60
        Route route2 = new Route(new City[]{city4,city2,city1,city3}); //64,72

        System.out.println("Parents");
        System.out.println(route1.toString());
        System.out.println(route2.toString());

        Route[] childs = geneticAlgorithm.crossover(route1,route2);

        System.out.println("Childs");
        System.out.println(childs[0].toString());
        System.out.println(childs[1].toString());

        assertTrue(childs[0].contains(city1));
        assertTrue(childs[0].contains(city2));
        assertTrue(childs[0].contains(city3));
        assertTrue(childs[0].contains(city4));

        assertTrue(childs[1].contains(city1));
        assertTrue(childs[1].contains(city2));
        assertTrue(childs[1].contains(city3));
        assertTrue(childs[1].contains(city4));

    }

}