package com.example.tiffinwala.entity;

public class Order {

    double totalAmount;
    int subscriptionId;
    int userId;

    public Order(double totalAmount, int subscriptionId, int userId) {
        this.totalAmount = totalAmount;
        this.subscriptionId = subscriptionId;
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "totalAmount=" + totalAmount +
                ", subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                '}';
    }
}
