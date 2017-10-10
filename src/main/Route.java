package main;

import java.util.Random;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Route {
    private City[] cities;

    public Route(City[] cities) {
        this.cities = cities;
    }

    public Route(int length) {
        cities = new City[length];
    }

    public double getTotalDistance() {
        double totalDistance = 0;

        for (int i = 0; i < cities.length; i++) {
            if (i + 1 < cities.length) {
                totalDistance += cities[i].distanceTo(cities[i + 1]);
            } else {
                totalDistance += cities[i].distanceTo(cities[0]);
            }
        }

        return totalDistance;
    }

    public int length() {
        return cities.length;
    }

    public void setCity(City city, int index) {
        cities[index] = city;
    }

    public City getCityAtIndex(int index) {
        return cities[index];
    }

    public boolean contains(City city) {
        assert city !=null;
        boolean contains = false;
        for (int i=0; i<cities.length; i++){
            if (cities[i] !=null && cities[i].equals(city)){
                contains = true;
            }
        }
        return contains;
    }

    public void shuffleCities() {
        Random rnd = new Random();
        for (int i = cities.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            City a = cities[index];
            cities[index] = cities[i];
            cities[i] = a;
        }
    }

    public int findFirstFreeIndex() {
        int index = -1;
        boolean founded = false;

        for (int i = 0; i < cities.length && !founded; i++) {
            if (cities[i] == null) {
                index = i;
                founded = true;
            }
        }

        return index;
    }


    @Override
    public String toString() {
        String string = "";
        for (City city : cities) {
            if (city!=null){
                string += city.toString() + " ";
            }
        }
        return string;
    }
}
