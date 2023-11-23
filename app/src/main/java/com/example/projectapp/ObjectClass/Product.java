package com.example.projectapp.ObjectClass;

import java.io.Serializable;

public class Product implements Serializable {
    private int count;

    private int productId;
    private String productName;
    private int storeId;
    private String avatarUrl;
    private String size;
    private double price;
    private double discount;
    private boolean status;
    private double rate;
    private String description;

    // Constructors
    public Product() {
    }

    public Product(int productId, String productName, int storeId, String avatarUrl, String size, double price, double discount, boolean status, double rate, String description) {
        this.productId = productId;
        this.productName = productName;
        this.storeId = storeId;
        this.avatarUrl = avatarUrl;
        this.size = size;
        this.price = price;
        this.discount = discount;
        this.status = status;
        this.rate = rate;
        this.description = description;
    }

    // Getters and Setters


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

