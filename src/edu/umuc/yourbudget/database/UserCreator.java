package edu.umuc.yourbudget.database;

import edu.umuc.yourbudget.database.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Christian on 9/22/17.
 */
public class UserCreator {

    private Connection connection;

    public UserCreator() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public boolean createUser(String firstName, String username, String password) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO user(first_name, username, password) VALUES(?, ?, ?)";
        boolean isSuccessful = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
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
