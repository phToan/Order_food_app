package com.example.projectapp.ObjectClass;

public class FoodOrderRes {
    private int product_id;
    private int quantity;
    private int price;

    public FoodOrderRes(int product_id, int quantity, int price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public FoodOrderRes() {

    }
}
