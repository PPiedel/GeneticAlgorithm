package test;

import main.model.City;
import main.model.Route;
import main.mutation.InverseMutation;
import org.junit.jupiter.api.Test;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class InverseMutationTest {
    @Test
    public void mutate() throws Exception {
        Route route = new Route(5);
        route.setCities(new City[]{
                new City(0,0),
                new City(1,1),
                new City(2,2),
                new City(3,3),
                new City(4,4)});
        System.out.println(route.toString());
        InverseMutation mutation = new InverseMutation();
        mutation.mutate(route);

        System.out.println(route.toString());

    }

}