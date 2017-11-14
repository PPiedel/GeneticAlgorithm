package test;

import main.Util;
import main.model.City;
import main.model.Route;
import main.tabu_search.TabuSearch;
import org.junit.jupiter.api.Test;

import static main.Util.FILE_PATH;
import static main.genetic_algorithm.GAMain.TIME;

/**
 * Created by Pawel_Piedel on 25.10.2017.
 */
public class TabuSearchTest {
    @Test
    public void tabuSearch2() throws Exception {
        TabuSearch ts = new TabuSearch();
        City[] cities = Util.openCities(FILE_PATH);
        Route route = ts.tabuSearch(cities, TIME);

        for (City c : cities) {
            assert route.contains(c);
        }

    }

}