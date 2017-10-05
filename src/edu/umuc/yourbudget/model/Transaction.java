package edu.umuc.yourbudget.model;

import java.sql.Date;

public class Transaction {

    private int id;
    private int accountId;
    private int userId;
    private String description;
    private Date date;
    private double total;
    private String category;
    private String type;

    public Transaction(int id, int accountId, int userId, String description, Date date, double total, String category) {
        this.id = id;
        this.accountId = accountId;
        this.userId = userId;
        this.description = description;
        this.date = date;
        this.total = total;
        this.category = category;
        setType(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String category) {
        if (category.equals("Income") || category.equals("income")) {
            type = "Income";
        } else {
            type = "Expense";
        }
    }
}
