package com.kelvin.enterprice.Model;

public class ModelPost {

    private String post_id, post_user_id,post_message,post_date_time,UID;

    public ModelPost() {
    }


    public ModelPost(String post_id, String post_user_id, String post_message, String post_date_time) {
        this.post_id = post_id;
        this.post_user_id = post_user_id;
        this.post_message = post_message;
        this.post_date_time = post_date_time;
    }


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(String post_user_id) {
        this.post_user_id = post_user_id;
    }

    public String getPost_message() {
        return post_message;
    }

    public void setPost_message(String post_message) {
        this.post_message = post_message;
    }

    public String getPost_date_time() {
        return post_date_time;
    }

    public void setPost_date_time(String post_date_time) {
        this.post_date_time = post_date_time;
    }
}
