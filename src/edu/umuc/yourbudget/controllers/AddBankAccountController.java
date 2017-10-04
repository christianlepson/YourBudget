package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountCreator;
import edu.umuc.yourbudget.model.ErrorDialog;
import edu.umuc.yourbudget.model.InputValidator;
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

public class AddBankAccountController {

    @FXML private TextField accountName;
    @FXML private ChoiceBox<String> accountType;
    @FXML private TextField accountBalance;

    private User user;
    private HomeController parent;

    public void initialize(User user, HomeController parent) {
        this.user = user;
        this.parent = parent;
        accountType.getItems().addAll("Checking", "Savings");
        accountType.setValue("Checking");
    }

    @FXML
    private void submitAccount(ActionEvent event) {

        if(InputValidator.isTextValid(accountName.getText())) {

            if (InputValidator.isCurrencyValid(accountBalance.getText())) {
                BankAccountCreator creator = new BankAccountCreator();
                creator.createAccount(
                        user.getId(),
                        accountName.getText(),
                        getAccountType(accountType),
                        getFormattedBalance(accountBalance.getText())
                );
                parent.updateUI();
                closeWindow(event);
            } else {
                ErrorDialog.display("Please enter a valid account balance." +
                        "\nex. 999.99");
            }

        } else {
            ErrorDialog.display("Account names cannot be blank or longer than 124 characters.");
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

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.close();
    }

}
