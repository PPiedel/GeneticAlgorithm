package main.tabu_search;

import main.Util;
import main.model.City;
import main.model.Route;
import org.junit.jupiter.api.Test;

import static main.genetic_algorithm.GAMain.FILE_PATH;

/**
 * Created by Pawel_Piedel on 25.10.2017.
 */
public class TabuSearchAlgorithmTest {
    @Test
    public void tabuSearch2() throws Exception {
        TabuSearchAlgorithm ts = new TabuSearchAlgorithm();
        City[] cities = Util.openCities(FILE_PATH);
        Route route = ts.tabuSearch(cities);

        for (City c : cities){
            assert route.contains(c);
        }

    }

}