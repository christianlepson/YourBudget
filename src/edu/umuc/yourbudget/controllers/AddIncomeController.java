package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountRetriever;
import edu.umuc.yourbudget.database.BankAccountUpdater;
import edu.umuc.yourbudget.database.TransactionCreator;
import edu.umuc.yourbudget.model.Account;
import edu.umuc.yourbudget.model.ErrorDialog;
import edu.umuc.yourbudget.model.InputValidator;
import edu.umuc.yourbudget.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class AddIncomeController {

    private User user;
    private HomeController parent;
    private ArrayList<Account> accounts;

    @FXML private ChoiceBox<Account> accountsChoiceBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField totalTextField;
    @FXML private TextField descriptionTextField;

    public void initialize(User user, HomeController parent) {
        this.user = user;
        this.parent = parent;
        populateAccountChoiceBox();
    }

    private void populateAccountChoiceBox() {
        getUserAccounts();

        if (accounts.size() > 0) {
            accountsChoiceBox.getItems().addAll(accounts);
            accountsChoiceBox.setValue(accounts.get(0));
        }
    }

    private void getUserAccounts() {
        BankAccountRetriever retriever = new BankAccountRetriever();
        accounts = retriever.retrieveByUserId(user.getId());
    }

    @FXML
    private void addIncome(ActionEvent event) {
        if (InputValidator.isTextValid(descriptionTextField.getText())) {

            if (datePicker.getValue() != null) {

                if (InputValidator.isCurrencyValid(totalTextField.getText())) {

                    if (accountsChoiceBox.getValue() != null) {
                        Date date = Date.valueOf(datePicker.getValue());

                        TransactionCreator creator = new TransactionCreator();

                        creator.createIncome(
                                accountsChoiceBox.getValue().getId(),
                                user.getId(),
                                descriptionTextField.getText(),
                                date,
                                Double.parseDouble(totalTextField.getText())
                        );

                        BankAccountUpdater updater = new BankAccountUpdater();
                        updater.updateAccountWithIncome(
                                Double.parseDouble(totalTextField.getText()),
                                accountsChoiceBox.getValue().getId());

                        parent.updateUI();

                        closeWindow(event);
                    } else {
                        ErrorDialog.display("You must add a bank account before you can add income.");
                    }

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
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.close();
    }

}
