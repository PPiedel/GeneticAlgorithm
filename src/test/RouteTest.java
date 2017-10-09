package test;

import main.City;
import org.junit.jupiter.api.Test;


/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class RouteTest {

    @Test
    public void getTotalDistance() throws Exception {
        City city1 = new City(0,0);
        City city2 = new City(10,10);
        City city3 = new City(20,20);

        double distance1 = city1.distanceTo(city2);
    }

}