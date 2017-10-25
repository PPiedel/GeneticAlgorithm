package main.tabu_search;

import main.model.City;
import main.Util;
import main.model.Route;

import static main.genetic_algorithm.GAMain.FILE_PATH;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class TSMain {
    public static void main(String[] args) {
        TabuSearchAlgorithm tabuSearch = new TabuSearchAlgorithm();
        City[] cities = Util.openCities(FILE_PATH);

        Route best = tabuSearch.tabuSearch(cities);

        System.out.println("Best distance : "+best.getTotalDistance());
        System.out.println("Route : "+best.toString());
    }
}
