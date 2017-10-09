package main;

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

    public double getBestDistance() {
        double bestDistance = Integer.MAX_VALUE;
        for (Route route : routes) {
            if (route.getTotalDistance() < bestDistance) {
                bestDistance = route.getTotalDistance();
            }
        }
        return bestDistance;
    }

    public double getAverageDistance() {
        double sum = 0;
        for (Route route : routes) {
            sum += route.getTotalDistance();
        }
        return sum / routes.length;
    }

    public double getWorseDistance() {
        double worseDistance = Integer.MIN_VALUE;
        for (Route route : routes) {
            if (route.getTotalDistance() > worseDistance) {
                worseDistance = route.getTotalDistance();
            }
        }

        return worseDistance;
    }

    public void initializeRoutesInRandomOrder(City[] cities) {
        for (int i = 0; i < routes.length; i++) {
            Route randomRoute = new Route(cities);
            randomRoute.shuffleCities();

            routes[i] = randomRoute;
        }
    }
}
