package main.tabu_search;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import main.Util;
import main.model.City;
import main.model.Route;

import static main.Util.FILE_PATH;
import static main.genetic_algorithm.GAMain.TIME;
import static main.model.Route.EVALUATE_FUNCTION_NUMBER;
import static main.tabu_search.TabuSearch.bestStats;
import static main.tabu_search.TabuSearch.currentStats;

/**
 * Created by Pawel_Piedel on 23.10.2017.
 */
public class TSMain extends Application {
    public static final int ITER_NUMBER = 1;

    public static void main(String[] args) {
        TabuSearch tabuSearch = new TabuSearch();
        City[] cities = Util.openCities(FILE_PATH);

        double sum = 0;
        double[] distances = new double[ITER_NUMBER];
        Route best;
        for (int i = 0; i < ITER_NUMBER; i++) {
            best = tabuSearch.tabuSearch(cities, TIME);
            sum += best.getTotalDistance();
            distances[i] = best.getTotalDistance();
            System.out.println("Iteracja : " + i);
        }
        double average = sum / ITER_NUMBER;

        double mean;
        double sum2 = 0;
        for (double distance : distances) {
            sum2 += (distance - average) * (distance - average);
        }
        mean = Math.sqrt(sum2 / 10);

        System.out.printf("Average distance %.2f : \n", average);
        System.out.printf("Mean : %.2f \n", mean);
        System.out.println("Liczba obliczen funkcji oceny : " + EVALUATE_FUNCTION_NUMBER / ITER_NUMBER);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        paintChart(primaryStage);
    }

    public void paintChart(Stage stage) {
        stage.setTitle("Genetic Algorithm");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Sample number");
        yAxis.setLabel("Distance");

        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series currentSeries = getCurrentSeries();
        XYChart.Series bestSeries = getBestSeries();

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(currentSeries);
        lineChart.getData().add(bestSeries);
        stage.setScene(scene);
        stage.show();
    }

    private XYChart.Series getCurrentSeries() {
        XYChart.Series currentSeries = new XYChart.Series();
        currentSeries.setName("Current distance");

        for (int i = 0; i < currentStats.size(); i++) {
            currentSeries.getData().add(new XYChart.Data(i, currentStats.get(i)));
        }
        return currentSeries;
    }

    private XYChart.Series getBestSeries() {
        XYChart.Series bestSeries = new XYChart.Series();
        bestSeries.setName("Best distance");

        for (int i = 0; i < bestStats.size(); i++) {
            bestSeries.getData().add(new XYChart.Data(i, bestStats.get(i)));
        }
        return bestSeries;
    }
}
