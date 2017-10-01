package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountCreator;
import edu.umuc.yourbudget.model.ErrorDialog;
import edu.umuc.yourbudget.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Christian on 9/30/17.
 */
public class InitialSetupController {
    @FXML private TextField accountName;
    @FXML private ChoiceBox<String> accountType;
    @FXML private TextField accountBalance;

    private User user;

    public void initialize(User user) {
        this.user = user;
        accountName.setText(user.getFirstName() + "'s Checking Account");
        accountType.getItems().addAll("Checking", "Savings");
        accountType.setValue("Checking");
    }

    @FXML
    private void abortAccountCreation(ActionEvent event) {
        showHomeScene(event);
    }

    @FXML
    private void submitAccount(ActionEvent event) {

        if (isBalanceValid(accountBalance.getText())) {
            BankAccountCreator creator = new BankAccountCreator();
            creator.createAccount(
                    Integer.parseInt(user.getId()),
                    accountName.getText(),
                    getAccountType(accountType),
                    getFormattedBalance(accountBalance.getText())
            );
            showHomeScene(event);
        } else {
            ErrorDialog.display("Please enter a valid account balance." +
                    "\nex. 999.99");
        }

    }

    private void showHomeScene(ActionEvent event) {
        Parent setupParent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/home.fxml"));
            setupParent = loader.load();
            Scene homeScene = new Scene(setupParent);

            HomeController controller = loader.getController();
            controller.initialize(user);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(homeScene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Unable to start Home scene from Initial Setup.");
            e.printStackTrace();
        }
    }

    private boolean isBalanceValid(String balanceString) {
        try {
            balanceString = balanceString.replaceAll(",", "");
            Double.parseDouble(balanceString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getAccountType(ChoiceBox<String> choiceBox) {
        return choiceBox.getValue().toLowerCase();
    }

    private double getFormattedBalance(String balanceString) {
        balanceString = balanceString.replaceAll(",", "");
        double total = Double.valueOf(balanceString);
        total = Math.round(total*100)/100.0d;
        return total;
    }


}
