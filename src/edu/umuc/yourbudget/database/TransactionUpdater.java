package edu.umuc.yourbudget.database;

import edu.umuc.yourbudget.model.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionUpdater {

    private final Connection connection;

    public TransactionUpdater() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public boolean updateTransaction(int id,
                                     int accountId,
                                     String description,
                                     Date date,
                                     double total,
                                     String category) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE transactions SET account_id = ?, description = ?, date = ?, total = ?, category = ? WHERE id = ?";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, date);
            preparedStatement.setDouble(4, total);
            preparedStatement.setString(5, category);
            preparedStatement.setInt(6, id);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to update transaction.");
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return isSuccessful;
        }
    }

    public boolean deleteTransaction(int id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM transactions WHERE id = ?";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to delete transaction.");
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return isSuccessful;
        }
    }

}
