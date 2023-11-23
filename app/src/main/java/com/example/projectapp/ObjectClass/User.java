package com.example.projectapp.ObjectClass;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    private int userId;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dob")
    private String dob;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    public User(int userId, String username, String password, String email, String fullname, String gender, String dob, String phone, String address) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Các constructor, getter và setter
    @Override
    public String toString() {
        return "User{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", fullname='" + getFullname() + '\'' +
                '}';
    }
}




