package com.example.projectapp.ObjectClass;

import java.io.Serializable;

public class OrderResponse implements Serializable {
    private int orderId;
    private int userId;
    private int status;
    private String orderDate;
    private String totalPrice;
    private String storeName;

    public OrderResponse() {
    }

    public OrderResponse(int orderId, int userId, int status, String orderDate, String totalPrice, String storeName) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.storeName = storeName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
