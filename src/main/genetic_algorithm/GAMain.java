package main.genetic_algorithm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import main.Util;
import main.model.City;

import static main.Util.FILE_PATH;
import static main.genetic_algorithm.GeneticAlgorithm.*;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class GAMain extends Application {
    public static void main(String[] args) {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        City[] cities = Util.openCities(FILE_PATH);


        double sum = 0;
        double[] distances = new double[10];
        for (int i=0; i < 10; i++){
            geneticAlgorithm.ga(cities);
            sum+=geneticAlgorithm.getBestDistance();
            distances[i] = geneticAlgorithm.getBestDistance();
        }
        double average = sum / 10;

        double mean;
        double sum2=0;
        for (double distance : distances){
            sum2+=(distance-average)*(distance-average);
        }
        mean = Math.sqrt(sum2/10);


        System.out.printf("Sredni wynik : %.2f %n", average);
        System.out.printf("Odchylenie : %.2f ",mean);

       // System.out.println("Final distance : "+bests[GENERATIONS_NUMBER-1]);


        launch(args);

        //calculateBestParams();
    }

    private static void calculateBestParams() {
        double bestDistance = Double.MAX_VALUE;
        City[] cities = Util.openCities(FILE_PATH);
        for (int tournamentSize = 1; tournamentSize < 20 ;tournamentSize++){

            for (double mutationProbability = 0.01; mutationProbability < 0.2; mutationProbability+=0.01){

                for (double crossoverProbability = 0.1; crossoverProbability < 1; crossoverProbability+=0.1){
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    TOURNAMENT_SIZE = tournamentSize;
                    MUTATION_PROBABILITY = mutationProbability;
                    GeneticAlgorithm.CROSSOVER_PROBABILTY = crossoverProbability;
                    ELITISM = false;

                    geneticAlgorithm.ga(cities);

                    if (geneticAlgorithm.getFinalRoute().getTotalDistance() < bestDistance){
                        System.out.println("Tournament size : "+tournamentSize);
                        System.out.println("Mutation probability : "+mutationProbability);
                        System.out.println("Crossover probability : "+crossoverProbability);
                        bestDistance = geneticAlgorithm.getFinalRoute().getTotalDistance();
                        System.out.println("Best distance : "+bestDistance);
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
        xAxis.setLabel("Generation");
        yAxis.setLabel("Distance");

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