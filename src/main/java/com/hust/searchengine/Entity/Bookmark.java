package com.hust.searchengine.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Bookmark {
    private String username;
    private String doi;
    private Date insert_time;

    public String getInsert_time() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(insert_time);
        return time;
    }

    public void setInsert_time(Date insert_time) {
        this.insert_time = insert_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }
}
