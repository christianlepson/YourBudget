package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.BankAccountRetriever;
import edu.umuc.yourbudget.database.TransactionRetriever;
import edu.umuc.yourbudget.model.Transaction;
import edu.umuc.yourbudget.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Christian on 9/30/17.
 */
public class HomeController {
    @FXML private Label loginStatusLabel;
    @FXML private Label checkingLabel;
    @FXML private Label savingsLabel;
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> descCol;
    @FXML private TableColumn<Transaction, Date> dateCol;
    @FXML private TableColumn<Transaction, String> catCol;
    @FXML private TableColumn<Transaction, String> totalCol;

    private User user;

    public void initialize(User user) {
        this.user = user;
        updateUI();
    }

    public void updateUI() {
        loginStatusLabel.setText("Logged in as " + user.getFirstName() + ".");
        setAccountBalanceLabels();
        populateTransactionsTable();
    }

    private void setAccountBalanceLabels() {
        BankAccountRetriever retriever = new BankAccountRetriever();
        double checkingBalance = retriever.getTotalCheckingBalance(user.getId());
        double savingsBalance = retriever.getTotalSavingsBalance(user.getId());
        checkingLabel.setText(getFormattedCheckingBalanceMessage(checkingBalance));
        savingsLabel.setText(getFormattedSavingsBalanceMessage(savingsBalance));
    }


    private String getFormattedCheckingBalanceMessage(double balance) {
        return getFormattedBalanceMessage("checking", balance);
    }

    private String getFormattedSavingsBalanceMessage(double balance) {
        return getFormattedBalanceMessage("savings", balance);
    }

    private String getFormattedBalanceMessage(String accountType, double balance) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formattedBalance = formatter.format(balance);
        String message = "Current ";

        switch (accountType) {
            case "checking":
                message += "Checking";
                break;
            case "savings":
                message += "Savings";
                break;
            default:
                System.out.println("Invalid account type entered for balance message formatting.");
                return null;
        }

        message += " Balance: " + formattedBalance;
        return message;
    }

    private void populateTransactionsTable() {
        TransactionRetriever retriever = new TransactionRetriever();
        ArrayList<Transaction> transactionsArrayList = retriever.retrieveByUserId(user.getId());
        if (transactionsArrayList.size() > 0 ) {
            ObservableList<Transaction> transactions = FXCollections.observableArrayList();
            transactions.addAll(transactionsArrayList);
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            totalCol.setCellValueFactory(new PropertyValueFactory<>("formattedTotal"));
            transactionsTable.setItems(transactions);
            dateCol.setSortType(TableColumn.SortType.ASCENDING);
            transactionsTable.getSortOrder().add(dateCol);
            transactionsTable.sort();
        }
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
            controller.initialize(user, this);

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
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/add_income.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);

            AddIncomeController controller = loader.getController();
            controller.initialize(user, this);

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
    private void showAddBankAccountScene(ActionEvent event) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/add_bank_account.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);

            AddBankAccountController controller = loader.getController();
            controller.initialize(user, this);

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
