package com.example.projectapp.ObjectClass;

import com.example.projectapp.ui.Home.StoreItem;

import java.io.Serializable;

public class Dish implements Serializable {
    private String name;
    private int quantity;
    private int id;
    private String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private String avatar;
    private String price;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Dish() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(String name, int quantity, int id) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }
}
