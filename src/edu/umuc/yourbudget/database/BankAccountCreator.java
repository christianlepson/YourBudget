package edu.umuc.yourbudget.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Christian on 9/30/17.
 */
public class BankAccountCreator {

    private final Connection connection;

    public BankAccountCreator() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public boolean createAccount(int userId, String description, String type, double balance) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO account(user_id, name, type, balance) VALUES(?, ?, ?, ?)";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, type);
            preparedStatement.setDouble(4, balance);
            preparedStatement.execute();
            isSuccessful = true;
        } catch (Exception e) {
            System.out.println("Unable to create bank account.");
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
