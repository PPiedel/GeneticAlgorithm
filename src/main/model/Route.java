package main.model;

import java.util.Random;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Route implements Comparable<Route> {
    private City[] cities;

    public Route() {
    }

    public Route(City[] cities) {
        this.cities = new City[cities.length];

        System.arraycopy(cities, 0, this.cities, 0, cities.length);
    }

    public Route(int length) {
        cities = new City[length];
    }

    public City[] getCities() {
        return cities;
    }

    public void setCities(City[] cities) {
        this.cities = cities;
    }

    public double getTotalDistance() {
        double totalDistance = 0;

        for (int i = 0; i < cities.length; i++) {
            if (i + 1 < cities.length) {
                totalDistance += cities[i].distanceTo(cities[i + 1]);
            } else {
                //close the circle
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
        boolean contains = false;
        for (int i = 0; i < cities.length && !contains; i++) {
            if (cities[i] != null && cities[i].equals(city)) {
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
            City tempCopy = cities[index];
            cities[index] = cities[i];
            cities[i] = tempCopy;
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
            if (city != null) {
                string += city.toString() + " ";
            }
        }
        return string;
    }

    @Override
    public int compareTo(Route other) {
        if (getTotalDistance() == other.getTotalDistance()){
            return 0;
        }
        else if (getTotalDistance() > other.getTotalDistance()){
            return 1;
        }
        else return -1;
    }
}
