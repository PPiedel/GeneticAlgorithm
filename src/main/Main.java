package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


import java.util.Arrays;

import static java.util.Arrays.stream;
import static main.GeneticAlgorithm.*;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class Main extends Application {
    public static final String FILE_PATH = "tsp_data/kroA100.tsp";
    public static final String SCND_FILE_PATH = "tsp_data/kroB100.tsp";



    public static void main(String[] args) {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        City[] cities = Util.openCities(FILE_PATH,SCND_FILE_PATH);
        //stream(cities).forEach(System.out::println);

        geneticAlgorithm.ga(cities);

        System.out.println("Final distance : "+bests[GENERATIONS_NUMBER-1]);

        System.out.println("Final route : "+geneticAlgorithm.getFinalRoute().toString());

        launch(args);
    }

    private static void calculateBestParams(boolean elitism) {
        double bestDistance = Double.MAX_VALUE;
        City[] cities = Util.openCities(FILE_PATH,SCND_FILE_PATH);
        for (int tournamentSize = 1; tournamentSize < 20 ;tournamentSize++){
            for (double mutationProbability = 0.01; mutationProbability < 0.03; mutationProbability+=0.01){
                for (double crossoverProbability = 0.4; crossoverProbability < 0.6; crossoverProbability+=0.1){
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    geneticAlgorithm.ga(cities);

                    if (geneticAlgorithm.getFinalRoute().getTotalDistance() < bestDistance){
                        System.out.println("Tournament size : "+tournamentSize);
                        System.out.println("Mutation probability : "+mutationProbability);
                        System.out.println("Crossover probability : "+crossoverProbability);
                        bestDistance = geneticAlgorithm.getFinalRoute().getTotalDistance();
                    }

                }
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        paintChart(stage);
    }

    private void paintChart(Stage stage) {
        stage.setTitle("Genetic Algorithm");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Pokolenie");
        yAxis.setLabel("Odleglosc");

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Best");

        XYChart.Series bestSeries = getBestSeries();
        XYChart.Series avgSeries = getAvgSeries();
        XYChart.Series worstSeries = getWorstSeries();

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(bestSeries);
        lineChart.getData().add(avgSeries);
        lineChart.getData().add(worstSeries);

        stage.setScene(scene);
        stage.show();
    }

    private XYChart.Series getWorstSeries() {
        XYChart.Series worstSeries = new XYChart.Series();
        worstSeries.setName("Worst");
        //populating the series with data
        for (int i=0; i< GENERATIONS_NUMBER; i++){
            worstSeries.getData().add(new XYChart.Data(i,worsts[i]));
        }
        return worstSeries;
    }

    private XYChart.Series getAvgSeries() {
        XYChart.Series avgSeries = new XYChart.Series();
        avgSeries.setName("Avg");
        //populating the series with data
        for (int i=0; i< GENERATIONS_NUMBER; i++){
            avgSeries.getData().add(new XYChart.Data(i,avgs[i]));
        }
        return avgSeries;
    }

    private XYChart.Series getBestSeries() {
        XYChart.Series bestSeries = new XYChart.Series();
        bestSeries.setName("Best");
        //populating the series with data
        for (int i=0; i< GENERATIONS_NUMBER; i++){
            bestSeries.getData().add(new XYChart.Data(i,bests[i]));
        }
        return bestSeries;
    }
}