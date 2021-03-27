package com.kelvin.enterprice.Model;

public class ModelConversation {

    private String conversation_id, conversation_user_id, conversation_from_users_id,conversation_to_user_id, conversation_message, conversation_date;

    public ModelConversation(String conversation_id, String conversation_user_id, String conversation_from_users_id, String conversation_to_user_id, String conversation_message, String conversation_date) {
        this.conversation_id = conversation_id;
        this.conversation_user_id = conversation_user_id;
        this.conversation_from_users_id = conversation_from_users_id;
        this.conversation_to_user_id = conversation_to_user_id;
        this.conversation_message = conversation_message;
        this.conversation_date = conversation_date;
    }

    public ModelConversation() {
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getConversation_user_id() {
        return conversation_user_id;
    }

    public void setConversation_user_id(String conversation_user_id) {
        this.conversation_user_id = conversation_user_id;
    }

    public String getConversation_from_users_id() {
        return conversation_from_users_id;
    }

    public void setConversation_from_users_id(String conversation_from_users_id) {
        this.conversation_from_users_id = conversation_from_users_id;
    }

    public String getConversation_to_user_id() {
        return conversation_to_user_id;
    }

    public void setConversation_to_user_id(String conversation_to_user_id) {
        this.conversation_to_user_id = conversation_to_user_id;
    }

    public String getConversation_message() {
        return conversation_message;
    }

    public void setConversation_message(String conversation_message) {
        this.conversation_message = conversation_message;
    }

    public String getConversation_date() {
        return conversation_date;
    }

    public void setConversation_date(String conversation_date) {
        this.conversation_date = conversation_date;
    }
}
