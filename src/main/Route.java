package main;

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

        for (int i=0; i<cities.size(); i++){
            if (i+1 < cities.size()){
                totalDistance += cities.get(i).distanceTo(cities.get(i+1));
            }
            else {
                totalDistance += cities.get(i).distanceTo(cities.get(0));
            }
        }

        return totalDistance;
    }
}
