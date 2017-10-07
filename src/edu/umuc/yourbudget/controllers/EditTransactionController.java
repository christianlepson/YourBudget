package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountRetriever;
import edu.umuc.yourbudget.database.BankAccountUpdater;
import edu.umuc.yourbudget.database.TransactionCreator;
import edu.umuc.yourbudget.database.TransactionUpdater;
import edu.umuc.yourbudget.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class EditTransactionController {

    private HomeController parent;
    private Transaction transaction;
    private ArrayList<Account> accounts;

    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField totalTextField;
    @FXML
    private ChoiceBox<Account> accountsChoiceBox;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private HBox categoryHBox;

    public void initialize(Transaction transaction, HomeController parent) {
        this.parent = parent;
        this.transaction = transaction;
        populateForm();
    }

    private void populateForm() {
        descriptionTextField.setText(transaction.getDescription());
        datePicker.setValue(transaction.getDate().toLocalDate());
        totalTextField.setText(String.valueOf(transaction.getTotal()));
        populateAccountChoiceBox();
        if (transaction.getCategory().equalsIgnoreCase("Income")) {
            categoryHBox.setVisible(false);
        } else {
            populateCategoryChoiceBox();
        }
    }

    private void populateAccountChoiceBox() {
        getUserAccounts();

        if (accounts.size() > 0) {
            accountsChoiceBox.getItems().addAll(accounts);
        }

        int id = transaction.getAccountId();
        for (Account account : accounts) {
            if (account.getId() == id) {
                accountsChoiceBox.setValue(account);
            }
        }

    }

    private void getUserAccounts() {
        BankAccountRetriever retriever = new BankAccountRetriever();
        accounts = retriever.retrieveByUserId(transaction.getUserId());
    }

    private void populateCategoryChoiceBox() {
        ArrayList<String> categories = SpendingCategories.get();
        categoryChoiceBox.getItems().addAll(categories);
        categoryChoiceBox.setValue(transaction.getCategory());
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.close();
    }

    @FXML
    private void updateTransaction(ActionEvent event) {
        if (accountsChoiceBox.getValue() != null) {

            if (InputValidator.isTextValid(descriptionTextField.getText())) {

                if (datePicker.getValue() != null) {

                    if (InputValidator.isCurrencyValid(totalTextField.getText())) {
                        double initialTotal = transaction.getTotal();
                        double newTotal = Double.parseDouble(totalTextField.getText());
                        double totalDifference = newTotal - transaction.getTotal();
                        int initialAccountId = transaction.getAccountId();
                        int newAccountId = accountsChoiceBox.getValue().getId();
                        boolean accountChanged = initialAccountId != newAccountId;

                        transaction.setDescription(descriptionTextField.getText());
                        transaction.setAccountId(newAccountId);
                        transaction.setDate(Date.valueOf(datePicker.getValue()));
                        transaction.setTotal(newTotal);

                        if (categoryChoiceBox.getValue() != null) {
                            transaction.setCategory(categoryChoiceBox.getValue());
                        }

                        TransactionUpdater transactionUpdater = new TransactionUpdater();
                        transactionUpdater.updateTransaction(
                                transaction.getId(),
                                transaction.getAccountId(),
                                transaction.getDescription(),
                                transaction.getDate(),
                                transaction.getTotal(),
                                transaction.getCategory()
                        );

                        if (accountChanged) {
                            BankAccountUpdater bankAccountUpdater = new BankAccountUpdater();
                            String type = transaction.getType();

                            if (type.equalsIgnoreCase("expense")) {
                                bankAccountUpdater.updateAccountWithIncome(initialTotal, initialAccountId);
                                bankAccountUpdater.updateAccountWithExpense(newTotal, newAccountId);
                            } else {
                                bankAccountUpdater.updateAccountWithExpense(initialTotal, initialAccountId);
                                bankAccountUpdater.updateAccountWithIncome(newTotal, newAccountId);
                            }

                        }

                        if (!accountChanged && totalDifference != 0) {
                            BankAccountUpdater bankAccountUpdater = new BankAccountUpdater();
                            String type = transaction.getType();

                            if (type.equalsIgnoreCase("expense")) {
                                bankAccountUpdater.updateAccountWithExpense(totalDifference, transaction.getAccountId());
                            } else {
                                bankAccountUpdater.updateAccountWithIncome(totalDifference, transaction.getAccountId());
                            }

                        }

                        parent.getTransactionsTable().refresh();
                        parent.getTransactionsTable().sort();

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
