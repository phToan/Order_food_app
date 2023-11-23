package com.example.projectapp.ObjectClass;

import java.util.List;

public class OrderRequest {
    private int userid;
    private List<FoodOrderRes> products;
    private String total_price;
    private String store_name;

    public OrderRequest(int userid, List<FoodOrderRes> products, String total_price, String store_name) {
        this.userid = userid;
        this.products = products;
        this.total_price = total_price;
        this.store_name = store_name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public List<FoodOrderRes> getProducts() {
        return products;
    }

    public void setProducts(List<FoodOrderRes> products) {
        this.products = products;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
}

