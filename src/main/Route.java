package main;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Route {
    private List<City> cities;

    public Route(List<City> cities) {
        this.cities = cities;
    }

    public double getTotalDistance() {
        double totalDistance = 0;

        for (int i = 0; i < cities.size(); i++) {
            if (i + 1 < cities.size()) {
                totalDistance += cities.get(i).distanceTo(cities.get(i + 1));
            } else {
                totalDistance += cities.get(i).distanceTo(cities.get(0));
            }
        }

        return totalDistance;
    }

    public int length() {
        return cities.size();
    }

    public void setCity(City city, int index) {
        cities.set(index, city);
    }

    public City getCityAtIndex(int index) {
        return cities.get(index);
    }

    public boolean contains(City city){
        return cities.contains(city);
    }

    public void shuffleCities(){
        Collections.shuffle(cities);
    }

    @Override
    public String toString() {
        String string = "";
        for (City city : cities){
            string+=city.toString()+" ";
        }
        return string;
    }
}
