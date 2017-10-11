package test;

import main.City;
import main.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class RouteTest {

    @Test
    public void getTotalDistance() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);
        City city3 = new City(20, 20);
        Route route = new Route(new City[]{city1, city2, city3});

        double distance1 = city1.distanceTo(city2);
        double distace2 = city2.distanceTo(city3);
        double distance3 = city3.distanceTo(city1);

        assertEquals(distance1 + distace2 + distance3, route.getTotalDistance());

    }

    @Test
    public void getTotalDistanceShouldReturnZero() throws Exception {
        City city = new City(100, 100);
        Route route = new Route(new City[]{city});

        assertEquals(0, route.getTotalDistance());
    }

    @Test
    public void shuffleCities() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(0,20);
        City city3 = new City(10, 20);
        City city4 = new City(10,0);
        City city5 = new City(20,20);
        City city6 = new City(25,15);
        City city7 = new City(28,35);
        City city8 = new City(34,28);
        City city9 = new City(42,34);
        Route route = new Route(new City[]{city1, city2, city3,city4,city5,city6,city7,city8,city9});
        System.out.println(""+route.toString());

     //   route.shuffleCities();

        System.out.println(""+route.toString());
    }

    @Test
    public void findFirstFreeIndexShouldReturnZero() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);

        Route route = new Route(3);
        route.setCity(city1,1);
        route.setCity(city2,2);

        int index = route.findFirstFreeIndex();

        assertEquals(0,index);
    }

    @Test
    public void findFirstFreeIndexShouldReturnMinusOne() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);
        City city3 = new City(20, 20);
        Route route = new Route(new City[]{city1, city2, city3});

        int index = route.findFirstFreeIndex();

        assertEquals(-1,index);
    }

    @Test
    public void findFirstFreeIndexShouldReturn1() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);

        Route route = new Route(3);
        route.setCity(city1,0);
        route.setCity(city2,2);

        int index = route.findFirstFreeIndex();

        assertEquals(1,index);
    }

    @Test
    public void findFirstFreeIndexShouldReturn2() throws Exception {
        City city1 = new City(0, 0);
        City city2 = new City(10, 10);
        City city3 = new City(20, 20);
        Route route = new Route(3);
        route.setCity(city1,0);
        route.setCity(city2,2);

        int index = route.findFirstFreeIndex();

        assertEquals(1,index);
    }


}