package main;

import static main.GeneticAlgorithm.POPULATION_SIZE;
import static main.GeneticAlgorithm.TOURNAMENT_SIZE;
import static main.Util.randomWithRange;

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

    public Route[] getRoutes() {
        return routes;
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


    public Route getBestRoute(){
        int bestIndex = 0;
        double bestDistance = getBestDistance();
        for (int i=0; i<routes.length; i++){
            if (routes[i].getTotalDistance() == bestDistance){
                bestIndex = i;
            }
        }
        return routes[bestIndex];
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
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Route randomRoute = new Route(cities);
            randomRoute.shuffleCities();

            routes[i] = randomRoute;
        }
    }

    public Route selectRouteViaTournament(){
        int bestIndex = Integer.MAX_VALUE;
        int bestValue = Integer.MAX_VALUE;

        for (int i=0; i< TOURNAMENT_SIZE; i++){
            int index = randomWithRange(0,routes.length-1);
            if (routes[index].getTotalDistance() < bestValue){
                bestIndex = index;
            }
        }

        return routes[bestIndex];
    }

    public void setRoute(Route route, int index){
        routes[index] = route;
    }
}
