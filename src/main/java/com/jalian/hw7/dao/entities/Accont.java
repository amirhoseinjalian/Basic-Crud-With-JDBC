package com.jalian.hw7.dao.entities;

public class Accont extends BaseEntity {
    private double amount;
    private String authorUsername;

    public Accont(int id, double amount, String authorUsername) {
        super(id);
        this.amount = amount;
        this.authorUsername = authorUsername;
    }

    public Accont(double amount, String authorUsername) {
        super();
        this.amount = amount;
        this.authorUsername = authorUsername;
    }

    public Accont(int id) {
        super(id);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}