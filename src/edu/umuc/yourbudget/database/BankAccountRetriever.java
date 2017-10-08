package edu.umuc.yourbudget.database;

import edu.umuc.yourbudget.model.Account;

import java.sql.*;
import java.util.ArrayList;

public class BankAccountRetriever {

    private final Connection connection;

    public BankAccountRetriever() {
        connection = SQLiteConnection.connect();
        if (connection == null) {
            System.out.println("Unable to connect to database.");
        }
    }

    public ArrayList<Account> retrieveByUserId(int userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM account WHERE user_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                double balance = resultSet.getDouble("balance");
                Account account = new Account(id, user_id, name, type, balance);
                accounts.add(account);
            }
            return accounts;
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

    public double getTotalCheckingBalance(int userId) {
        return getTotalAccountBalance(userId, "checking");
    }

    public double getTotalSavingsBalance(int userId) {
        return getTotalAccountBalance(userId, "savings");
    }

    private double getTotalAccountBalance(int userId, String accountType) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT TOTAL(balance) FROM account WHERE type = ? AND user_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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


    public double getTotalUserExpenditures(int userId, Date date) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT TOTAL(total) FROM transactions WHERE user_id = ? AND date > ? AND UPPER(category) != UPPER('income');";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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

    public double getExpendituresByCategory(int userId, String category, Date date) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT TOTAL(total) FROM transactions WHERE user_id = ? AND date > ? AND UPPER(category) = UPPER(?);";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, date);
            preparedStatement.setString(3, category);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
