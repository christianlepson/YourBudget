package edu.umuc.yourbudget.database;

import edu.umuc.yourbudget.database.SQLiteConnection;
import edu.umuc.yourbudget.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Christian on 9/22/17.
 */
public class UserRetriever {

    private final Connection connection;

    public UserRetriever() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public User retrieveByUsername(String user) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM user WHERE username = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String username = resultSet.getString("username");
                int id = resultSet.getInt("id");
                return new User(firstName, username, id);
            } else {
                return null;
            }

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

    public User retrieveByCredentials(String user, String pass) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.toLowerCase());
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String username = resultSet.getString("username");
                int id = resultSet.getInt("id");
                return new User(firstName, username, id);
            } else {
                return null;
            }

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
