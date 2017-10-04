package edu.umuc.yourbudget.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountUpdater {

    private Connection connection;

    public BankAccountUpdater() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public boolean updateAccountWithExpense(double expense, int accountId) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE account SET balance = balance - ? WHERE id = ?";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, expense);
            preparedStatement.setInt(2, accountId);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to update account with expense.");
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

    public boolean updateAccountWithIncome(double income, int accountId) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE account SET balance = balance + ? WHERE id = ?";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, income);
            preparedStatement.setInt(2, accountId);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to update account with income.");
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
