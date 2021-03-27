package com.kelvin.enterprice.Model;

public class ModelUser {


    private String user_id, user_name, user_address,user_firstname,user_lastname, user_mobile, user_age, user_login_id;

    public ModelUser() {
    }

    public ModelUser(String user_id, String user_name, String user_address, String user_firstname, String user_lastname, String user_mobile, String user_age, String user_login_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_address = user_address;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_mobile = user_mobile;
        this.user_age = user_age;
        this.user_login_id = user_login_id;
    }

    public String getUser_login_id() {
        return user_login_id;
    }

    public void setUser_login_id(String user_login_id) {
        this.user_login_id = user_login_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }
}
