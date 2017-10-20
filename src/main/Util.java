package main;

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
    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static City[] openCities(String firstFilePath, String scndFilePath) {
        List<City> list = new ArrayList<>();
        File distanceFile = new File(firstFilePath);
        File costsFile = new File(scndFilePath);
        BufferedReader reader1 = null;
        BufferedReader reader2 = null;

        try {
            reader1 = new BufferedReader(new FileReader(distanceFile));
            reader2 = new BufferedReader(new FileReader(costsFile));
            String distance;
            String cost;

            while ((distance = reader1.readLine()) != null
                    && (cost = reader2.readLine()) != null
                    && !distance.equals("EOF")
                    && !cost.equals("EOF")) {
                String[] distanceCoordinates = distance.split(" +");
                String[] costCoordinates = cost.split(" +");

                //first part of string is id number
                list.add(new City(Double.parseDouble(distanceCoordinates[1]), Double.parseDouble(distanceCoordinates[2]),
                        Double.parseDouble(costCoordinates[1]),Double.parseDouble(costCoordinates[2])));

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
