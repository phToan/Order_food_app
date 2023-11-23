package com.example.projectapp.ObjectClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stores implements Serializable {
    @SerializedName("store_id")
    private int storeId;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("avatar")
    private String avatarUrl;

    @SerializedName("address")
    private String address;

    public Stores(int storeId, String storeName, String avatarUrl, String address, String phone, String rate, String timeOpen, String timeClose, int storeType) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phone = phone;
        this.rate = rate;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.storeType = storeType;
    }

    public Stores(){}

    @SerializedName("phone")
    private String phone;

    @SerializedName("rate")
    private String rate;

    @SerializedName("time_open")
    private String timeOpen;

    @SerializedName("time_close")
    private String timeClose;

    @SerializedName("store_type")
    private int storeType;

    // Getters and setters for the fields
    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getRate() {
        return rate;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }
}

