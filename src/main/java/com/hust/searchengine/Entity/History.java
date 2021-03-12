package com.hust.searchengine.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class History {
    private String username;
    private String his_doi;
    private Date access_time;

    public Date getAccess_time() {
        return this.access_time;
    }

    public void setAccess_time(Date access_time) {
        this.access_time = access_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHis_doi() {
        return his_doi;
    }

    public void setHis_doi(String his_doi) {
        this.his_doi = his_doi;
    }
}
