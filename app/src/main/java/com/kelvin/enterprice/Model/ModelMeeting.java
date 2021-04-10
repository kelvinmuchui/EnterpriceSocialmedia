package com.kelvin.enterprice.Model;

public class ModelMeeting {

    private String meeting_id, meeting_date_time
            ,meeting_title ,meeting_duration,meeting_link,
            meeting_User_id;


    public ModelMeeting() {
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_date_time() {
        return meeting_date_time;
    }

    public void setMeeting_date_time(String meeting_date_time) {
        this.meeting_date_time = meeting_date_time;
    }

    public String getMeeting_title() {
        return meeting_title;
    }

    public void setMeeting_title(String meeting_title) {
        this.meeting_title = meeting_title;
    }

    public String getMeeting_duration() {
        return meeting_duration;
    }

    public void setMeeting_duration(String meeting_duration) {
        this.meeting_duration = meeting_duration;
    }

    public String getMeeting_link() {
        return meeting_link;
    }

    public void setMeeting_link(String meeting_link) {
        this.meeting_link = meeting_link;
    }

    public String getMeeting_User_id() {
        return meeting_User_id;
    }

    public void setMeeting_User_id(String meeting_User_id) {
        this.meeting_User_id = meeting_User_id;
    }
}
