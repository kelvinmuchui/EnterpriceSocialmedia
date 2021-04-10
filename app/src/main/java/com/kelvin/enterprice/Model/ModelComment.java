package com.kelvin.enterprice.Model;

public class ModelComment {
   private String Comment_id;
    private String Comment_user_id;
    private String Comment_post_id;
    private String Comment_message;
    private String Comment_date;

    public ModelComment() {
    }

    public String getComment_id() {
        return Comment_id;
    }

    public void setComment_id(String comment_id) {
        Comment_id = comment_id;
    }

    public String getComment_user_id() {
        return Comment_user_id;
    }

    public void setComment_user_id(String comment_user_id) {
        Comment_user_id = comment_user_id;
    }

    public String getComment_post_id() {
        return Comment_post_id;
    }

    public void setComment_post_id(String comment_post_id) {
        Comment_post_id = comment_post_id;
    }

    public String getComment_message() {
        return Comment_message;
    }

    public void setComment_message(String comment_message) {
        Comment_message = comment_message;
    }

    public String getComment_date() {
        return Comment_date;
    }

    public void setComment_date(String comment_date) {
        Comment_date = comment_date;
    }
}
