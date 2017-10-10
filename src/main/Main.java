package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.GeneticAlgorithm.GENERATIONS_NUMBER;
import static main.GeneticAlgorithm.bests;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Main extends Application {
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        //prepare test cities
        City city1 = new City(0, 0);
        City city2 = new City(0,20);
        City city3 = new City(10, 20);
        City city4 = new City(10,0);
        City city5 = new City(20,20);
        City city6 = new City(25,15);


        City[] cities = new City[]{city1,city2,city3,city4,city5,city6};

        geneticAlgorithm.ga(cities);

        System.out.println("Final distance : "+bests[GENERATIONS_NUMBER-1]);

        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Genetic Algorithm");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Pokolenie");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Best");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Best");
        //populating the series with data
        for (int i=0; i< GENERATIONS_NUMBER; i++){
            series.getData().add(new XYChart.Data(i,bests[i]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}