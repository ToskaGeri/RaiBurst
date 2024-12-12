package com.raiburst.demo.Dto;

public class ChatState {
    private String state = "GREETING";
    private String recipientPhoneNumber;
    private double amount;
    private String currency;
    private String description;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void reset() {
        this.state = "GREETING";
        this.recipientPhoneNumber = null;
        this.amount = 0;
        this.currency = null;
        this.description = null;
    }
}
