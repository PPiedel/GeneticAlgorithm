package main;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Population {
    private Route[] routes;

    public Population(int populationSize) {
        routes = new Route[populationSize];
    }

    public Population(Route[] routes) {
        this.routes = routes;
    }

    public double evaluate(){
        double bestDistance = Integer.MAX_VALUE;
        for (Route route : routes){
            if (route.getTotalDistance() < bestDistance){
                bestDistance = route.getTotalDistance();
            }
        }
        return bestDistance;
    }

    public void initializeRoutesInRandomOrder(List<City> cities){
        for (int i=0; i< routes.length; i++){
            Route randomRoute = new Route(cities);
            randomRoute.shuffleCities();

            routes[i] = randomRoute;
        }
    }
}
