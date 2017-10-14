package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountRetriever;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import edu.umuc.yourbudget.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.chart.*;

import java.io.IOException;
import java.sql.Date;

//Created by Bryan 10/05/17

public class ReportController {
    private User user;

    private double totalExpend;
    private double housingExpend;
    private double groceryExpend;
    private double carExpend;
    private double gasExpend;
    private double eatingExpend;
    private double entertainExpend;
    private double clothingExpend;
    private double beautyExpend;
    private double otherExpend;

    private ObservableList<PieChart.Data> pieChartData;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label loginStatusLabel;

    public void initialize(User user) {
        this.user = user;
        loginStatusLabel.setText("Logged in as " + user.getFirstName() + ".");
        setExpenditures();
        setPieChartData();
        pieChart.setData(pieChartData);
        pieChart.setStartAngle(45);
    }

    private void setExpenditures() {
        int time = 1450879900;
        Date date = new Date(time);
        BankAccountRetriever retriever = new BankAccountRetriever();
        totalExpend = retriever.getTotalUserExpenditures(user.getId(), date);
        housingExpend = retriever.getExpendituresByCategory(user.getId(), "Housing/Utilities", date);
        groceryExpend = retriever.getExpendituresByCategory(user.getId(), "Grocery", date);
        carExpend = retriever.getExpendituresByCategory(user.getId(), "Car Payment/Insurance", date);
        gasExpend = retriever.getExpendituresByCategory(user.getId(), "Gas", date);
        eatingExpend = retriever.getExpendituresByCategory(user.getId(), "Eating Out", date);
        entertainExpend = retriever.getExpendituresByCategory(user.getId(), "Entertainment", date);
        clothingExpend = retriever.getExpendituresByCategory(user.getId(), "Clothing", date);
        beautyExpend = retriever.getExpendituresByCategory(user.getId(), "Beauty", date);
        otherExpend = retriever.getExpendituresByCategory(user.getId(), "Other", date);
    }

    private void setPieChartData() {
        pieChartData = FXCollections.observableArrayList();
        addDataToPieChart("Housing/Utilities", getPercent(housingExpend));
        addDataToPieChart("Grocery", getPercent(groceryExpend));
        addDataToPieChart("Car Payment/Insurance", getPercent(carExpend));
        addDataToPieChart("Gas", getPercent(gasExpend));
        addDataToPieChart("Eating Out", getPercent(eatingExpend));
        addDataToPieChart("Entertainment", getPercent(entertainExpend));
        addDataToPieChart("Clothing", getPercent(clothingExpend));
        addDataToPieChart("Beauty", getPercent(beautyExpend));
        addDataToPieChart("Other", getPercent(otherExpend));
    }

    private double getPercent(double expense) {
        int scale = (int) Math.pow(10, 1);
        double value = expense / totalExpend * 100;
        return (double) Math.round(value * scale) / scale;
    }

    private void addDataToPieChart(String category, double percent) {
        if (percent > 0) {
            PieChart.Data data = new PieChart.Data(category, percent);
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " (", data.pieValueProperty(), "%)"
                    )
            );
            pieChartData.add(data);
        }
    }

    @FXML
    private void signOut(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } catch (Exception e) {
            System.out.println("Unable to open scene from Home.");
            e.printStackTrace();
        }
    }

    @FXML
    private void showHomeScene(ActionEvent event) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/home.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);

            HomeController controller = loader.getController();
            controller.initialize(user);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Unable to open Report scene from Home.");
            e.printStackTrace();
        }
    }


}