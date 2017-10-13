package main;

import java.io.*;
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

    public static City[] openCities(String filePath){
        List<City> list = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text;

            while ((text = reader.readLine()) != null && !text.equals("EOF")) {
                String[] parts = text.split(" +");

                list.add(new City(Double.parseDouble(parts[1]),Double.parseDouble(parts[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignored) {
            }
        }

        return list.toArray(new City[list.size()]);
    }
}
