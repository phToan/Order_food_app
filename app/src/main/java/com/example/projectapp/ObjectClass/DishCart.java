package com.example.projectapp.ObjectClass;

import java.io.Serializable;

public class DishCart implements Serializable {
    private String name;
    private int price;
    private int id;
    private int count;

    public DishCart(String name, int price, int id, int count) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
