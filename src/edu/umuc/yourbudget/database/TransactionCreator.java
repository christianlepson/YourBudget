package edu.umuc.yourbudget.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionCreator {

    private final Connection connection;

    public TransactionCreator() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public boolean createIncome(int accountId, int userId, String description, Date date, double total) {
        return create(accountId, userId, description, date, total, "Income");
    }

    public boolean createExpense(int accountId, int userId, String description, Date date, double total, String category) {
        return create(accountId, userId, description, date, total, category);
    }

    private boolean create(int accountId, int userId, String description, Date date, double total, String category) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO transactions(account_id, user_id, description, date, total, category) VALUES(?, ?, ?, ?, ?, ?)";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, description);
            preparedStatement.setDate(4, date);
            preparedStatement.setDouble(5, total);
            preparedStatement.setString(6, category);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to insert new user.");
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
