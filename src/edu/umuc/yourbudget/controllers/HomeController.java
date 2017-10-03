package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Christian on 9/30/17.
 */
public class HomeController {
    @FXML private Label loginStatusLabel;

    private User user;

    public void initialize(User user) {
        this.user = user;
        loginStatusLabel.setText("Logged in as " + user.getFirstName() + ".");
    }

    @FXML
    private void signOut(ActionEvent event) {
        showScene(event, "/fxml/welcome.fxml");
    }

    @FXML
    private void showAddExpenseScene(ActionEvent event) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/add_expense.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);

            AddExpenseController controller = loader.getController();
            controller.initialize(user);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            System.out.println("Unable to open Add Expense from Home.");
            e.printStackTrace();
        }
    }

    @FXML
    private void showAddIncomeScene(ActionEvent event) {
//        Parent parent = null;
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/add_income.fxml"));
//            parent = loader.load();
//            Scene scene = new Scene(parent);
//
//            AddIncomeController controller = loader.getController();
//            controller.initialize(user);
//
//            Stage window = new Stage();
//            window.initModality(Modality.APPLICATION_MODAL);
//            window.setScene(scene);
//            window.show();
//        } catch (IOException e) {
//            System.out.println("Unable to open Add Expense from Home.");
//            e.printStackTrace();
//        }
    }

    @FXML
    private void showAddBankAccountScene(ActionEvent event) {
//        Parent parent = null;
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/add_bank_account.fxml"));
//            parent = loader.load();
//            Scene scene = new Scene(parent);
//
//            AddBankAccountController controller = loader.getController();
//            controller.initialize(user);
//
//            Stage window = new Stage();
//            window.initModality(Modality.APPLICATION_MODAL);
//            window.setScene(scene);
//            window.show();
//        } catch (IOException e) {
//            System.out.println("Unable to open Add Expense from Home.");
//            e.printStackTrace();
//        }
    }

    @FXML
    private void showReportScene(ActionEvent event) {
        //        Parent parent = null;
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/fxml/report.fxml"));
//            parent = loader.load();
//            Scene scene = new Scene(parent);
//
//            ReportController controller = loader.getController();
//            controller.initialize(user);
//
//            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            appStage.setScene(scene);
//            appStage.show();
//        } catch (IOException e) {
//            System.out.println("Unable to open Report scene from Home.");
//            e.printStackTrace();
    }

    private void showScene(ActionEvent event, String scenePath) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(scenePath));
            Scene scene = new Scene(parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } catch (Exception e) {
            System.out.println("Unable to open scene from Home.");
            e.printStackTrace();
        }
    }

}
