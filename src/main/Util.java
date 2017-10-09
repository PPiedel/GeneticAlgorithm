package main;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Util {
    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
