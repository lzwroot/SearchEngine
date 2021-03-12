package com.hust.searchengine.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback {
    Integer feedback_id;
    String username;
    String feedback;
    String managerid;
    String result;
    Date feedback_time;
    Date result_time;

    public String getResult_time() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(result_time);
    }

    public void setResult_time(Date result_time) {
        this.result_time = result_time;
    }

    public Integer getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(Integer feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFeedback_time() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(feedback_time);
    }

    public void setFeedback_time(Date feedback_time) {
        this.feedback_time = feedback_time;
    }
}
