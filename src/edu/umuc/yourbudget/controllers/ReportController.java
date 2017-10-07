package edu.umuc.yourbudget.controllers;

import javafx.scene.Scene;
import edu.umuc.yourbudget.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

//Created by Bryan 10/05/17


public class ReportController {
    private User user;
    private HomeController parent;

    public void initialize(User user, HomeController parent) {
        this.user = user;
        this.parent = parent;
    }

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Report");
        stage.setWidth(500);
        stage.setHeight(500);

        //Example of values
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Housing/Utilities", 20),
                        new PieChart.Data("Grocery", 10),
                        new PieChart.Data("Car Payment/Insurance", 5),
                        new PieChart.Data("Gas", 10),
                        new PieChart.Data("Eating Out", 5),
                        new PieChart.Data("Entertainment", 10),
                        new PieChart.Data("Clothing", 10),
                        new PieChart.Data("Beauty", 10),
                        new PieChart.Data("Other", 20));

        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle("Report");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public double getPercent(double expense, double income) {
        double percent = 0;
        percent = Math.round(expense / income * 100);
        return percent;
    }


}