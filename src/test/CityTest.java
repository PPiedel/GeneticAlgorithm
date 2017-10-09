package test;

import main.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class CityTest {

    @Test
    public void distanceTo() throws Exception {
        City city = new City(100, 200);
        City scndCity = new City(200, 400);

        double distance = city.distanceTo(scndCity);

        assertEquals(223.60679774997897, distance);

    }

    @Test
    void distanceToShuldReturnZero() throws Exception {
        City city = new City(100, 50);
        City scndCity = new City(100, 50);

        double distance = city.distanceTo(scndCity);

        assertEquals(0, distance);
    }

}