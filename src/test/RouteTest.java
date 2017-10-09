package test;

import main.City;
import main.Route;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class RouteTest {

    @Test
    public void getTotalDistance() throws Exception {
        City city1 = new City(0,0);
        City city2 = new City(10,10);
        City city3 = new City(20,20);
        Route route = new Route(Arrays.asList(city1,city2,city3));

        double distance1 = city1.distanceTo(city2);
        double distace2 = city2.distanceTo(city3);
        double distance3 = city3.distanceTo(city1);

        assertEquals(distance1+distace2+distance3,route.getTotalDistance());

    }

    @Test
    public void getTotalDistanceShouldReturnZero() throws Exception {
        City city = new City(100,100);
        Route route = new Route(Collections.singletonList(city));

        assertEquals(0,route.getTotalDistance());
    }

}