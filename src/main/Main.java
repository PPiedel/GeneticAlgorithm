package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100);

        //prepare test cities
        City city1 = new City(0, 0);
        City city2 = new City(0,20);
        City city3 = new City(10, 20);
        City city4 = new City(10,0);
        City[] cities = new City[]{city1,city2,city3,city4};

        geneticAlgorithm.ga(cities);
    }
}
