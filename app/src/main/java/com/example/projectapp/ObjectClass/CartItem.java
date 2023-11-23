package com.example.projectapp.ObjectClass;

import java.io.Serializable;
import java.util.List;

public class CartItem implements Serializable {
    private int storeID;
    private String storeName;
    private String avatarURL;
    private int quantity;
    private List<Cart> foodList;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public List<Cart> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Cart> foodList) {
        this.foodList = foodList;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
