package edu.umuc.yourbudget.database;

import edu.umuc.yourbudget.model.Transaction;
import edu.umuc.yourbudget.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class TransactionRetriever {

    private Connection connection;

    public TransactionRetriever() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public ArrayList<Transaction> retrieveByUserId(int userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM transactions WHERE user_id = ?;";
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int accountId = resultSet.getInt("account_id");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                double total = resultSet.getDouble("total");
                String category = resultSet.getString("category");

                Transaction transaction = new Transaction(id, accountId, userId, description, date, total, category);
                transactions.add(transaction);
            }
            return transactions;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
