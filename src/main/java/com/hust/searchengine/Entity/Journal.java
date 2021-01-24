package com.hust.searchengine.Entity;

import com.hust.searchengine.Mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Journal {
    private String username;
    private String journal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }
}
