package com.kelvin.enterprice.Model;

public class ModelLogin {


    private String login_id, login_user_name, login_password, login_type;

    public ModelLogin(String login_id, String login_user_name, String login_password, String login_type) {
        this.login_id = login_id;
        this.login_user_name = login_user_name;
        this.login_password = login_password;
        this.login_type = login_type;
    }

    public ModelLogin() {
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getLogin_user_name() {
        return login_user_name;
    }

    public void setLogin_user_name(String login_user_name) {
        this.login_user_name = login_user_name;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }
}
