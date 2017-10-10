package main;

import org.junit.jupiter.api.Test;


/**
 * Created by Pawel_Piedel on 10.10.2017.
 */
public class UtilTest {

    @Test
    public void randomWithRange() throws Exception {
        int randomAmount = 1000;
        int startValue = 0;
        int endValue = 5;

        int[] numbers = new int[randomAmount];
        for (int i=0; i< randomAmount; i++){
            numbers[i] = Util.randomWithRange(startValue,endValue);
            assert numbers[i] >= startValue && numbers[i] <= endValue;
        }


    }

    @Test
    public void randomWithRangeZero() throws Exception {
        int randomAmount = 1000;
        int startValue = 0;
        int endValue = 0;

        int[] numbers = new int[randomAmount];
        for (int i=0; i< randomAmount; i++){
            numbers[i] = Util.randomWithRange(startValue,endValue);
            assert numbers[i] >= startValue && numbers[i] <= endValue;
        }


    }

}