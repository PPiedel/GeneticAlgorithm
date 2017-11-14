package main;

import main.model.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Util {
    public static final String FILE_PATH = "tsp_data/uruguay.tsp";


    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static City[] openCities(String firstFilePath) {
        List<City> list = new ArrayList<>();
        File distanceFile = new File(firstFilePath);

        BufferedReader reader1 = null;

        try {
            reader1 = new BufferedReader(new FileReader(distanceFile));
            String distance;
            while ((distance = reader1.readLine()) != null
                    && !distance.equals("EOF")
                   ) {
                String[] distanceCoordinates = distance.split(" +");

                //first part of string is id number
                list.add(new City(Double.parseDouble(distanceCoordinates[1]), Double.parseDouble(distanceCoordinates[2])));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader1 != null) {
                    reader1.close();
                }
            } catch (IOException ignored) {
            }
        }

        return list.toArray(new City[list.size()]);
    }
}
