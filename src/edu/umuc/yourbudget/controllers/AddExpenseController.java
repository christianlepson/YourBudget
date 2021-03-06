package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountRetriever;
import edu.umuc.yourbudget.database.BankAccountUpdater;
import edu.umuc.yourbudget.database.TransactionCreator;
import edu.umuc.yourbudget.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class AddExpenseController {

    private User user;
    private HomeController parent;
    private ArrayList<Account> accounts;

    @FXML private TextField descriptionTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField totalTextField;
    @FXML private ChoiceBox<Account> accountsChoiceBox;
    @FXML private ChoiceBox<String> categoryChoiceBox;


    public void initialize(User user, HomeController parent) {
        this.user = user;
        this.parent = parent;
        populateAccountChoiceBox();
        populateCategoryChoiceBox();
    }

    private void getUserAccounts() {
        BankAccountRetriever retriever = new BankAccountRetriever();
        accounts = retriever.retrieveByUserId(user.getId());
    }

    private void populateAccountChoiceBox() {
        getUserAccounts();

        if (accounts.size() > 0) {
            accountsChoiceBox.getItems().addAll(accounts);
            accountsChoiceBox.setValue(accounts.get(0));
        }
    }

    private void populateCategoryChoiceBox() {
        ArrayList<String> categories = SpendingCategories.get();
        categoryChoiceBox.getItems().addAll(categories);
        categoryChoiceBox.setValue(categories.get(0));
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.close();
    }

    @FXML
    private void addExpense(ActionEvent event) {

        if (accountsChoiceBox.getValue() != null) {

            if (InputValidator.isTextValid(descriptionTextField.getText())) {

                if (datePicker.getValue() != null) {

                    if (InputValidator.isCurrencyValid(totalTextField.getText())) {
                        Date date = Date.valueOf(datePicker.getValue());

                        TransactionCreator creator = new TransactionCreator();
                        creator.createExpense(
                                accountsChoiceBox.getValue().getId(),
                                user.getId(),
                                descriptionTextField.getText(),
                                date,
                                Double.parseDouble(totalTextField.getText()),
                                categoryChoiceBox.getValue()
                        );

                        BankAccountUpdater updater = new BankAccountUpdater();
                        updater.updateAccountWithExpense(
                                Double.parseDouble(totalTextField.getText()),
                                accountsChoiceBox.getValue().getId()
                        );

                        parent.updateUI();

                        closeWindow(event);

                    } else {
                        ErrorDialog.display("Please enter a valid total." +
                                "\nex. 999.99");
                    }

                } else {
                    ErrorDialog.display("Please enter the date of the transaction.");
                }

            } else {
                ErrorDialog.display("Please enter a valid description." +
                        "\nDescriptions cannot be blank or longer than 120 characters.");
            }

        } else {
            ErrorDialog.display("You must add a bank account before you can add expenses.");
        }

    }

}
