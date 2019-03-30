package com.example.tanthinh.local4fun.models;

public class User {


    private String fullname;
    private String email;
    private String phone;
    private String description;
    private String password;
    private String imgUrl;

    public User(String fullname, String email, String phone, String description){
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean comparePassword(String password){
        return this.password.equalsIgnoreCase(password) ? true : false;
    }
}
