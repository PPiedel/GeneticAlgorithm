package main.simulated_annealing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import main.Util;
import main.model.City;
import main.model.Route;

import static main.simulated_annealing.SimulatedAnnealing.currentDistances;
import static main.tabu_search.TSMain.ITER_NUMBER;

/**
 * Created by Pawel_Piedel on 26.10.2017.
 */
public class SAMain extends Application {

    public static void main(String[] args) {
        City[] cities = Util.openCities(Util.FILE_PATH);
        SimulatedAnnealing simulatedAnnealingAlgorithm = new SimulatedAnnealing();

        double sum = 0;
        double[] distances = new double[ITER_NUMBER];
        Route current;
        for (int i = 0; i < ITER_NUMBER; i++) {
            SimulatedAnnealing.currentDistances.clear();
            current = simulatedAnnealingAlgorithm.simulatedAnnealing(cities);
            sum += current.getTotalDistance();
            distances[i] = current.getTotalDistance();
            System.out.println("" + i);
        }
        double average = sum / ITER_NUMBER;

        double mean;
        double sum2 = 0;
        for (double distance : distances) {
            sum2 += (distance - average) * (distance - average);
        }
        mean = Math.sqrt(sum2 / 10);

        System.out.printf("Average distance %.2f : \n", average);
        System.out.printf("Mean : %.2f ", mean);


        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        paintChart(primaryStage);
    }

    public void paintChart(Stage stage) {
        stage.setTitle("SA Algorithm");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Distance");

        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series currentSeries = getCurrentSeries();

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(currentSeries);
        stage.setScene(scene);
        stage.show();
    }

    private XYChart.Series getCurrentSeries() {
        XYChart.Series currentSeries = new XYChart.Series();
        currentSeries.setName("Current distance");

        for (int i = 0; i < currentDistances.size(); i = i + 10) {
            currentSeries.getData().add(new XYChart.Data(i, currentDistances.get(i)));
        }
        return currentSeries;
    }
}
