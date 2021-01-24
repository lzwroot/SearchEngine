package com.hust.searchengine.Entity;

import com.hust.searchengine.Mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Subscription {
    private String username;

    @Autowired
    private SearchMapper searchMapper;

    private List<Author> authorList;
    private List<Field> fieldList;
    private List<Journal> journalList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        authorList = searchMapper.findAllSubAuthorsByUsername(username);
        fieldList = searchMapper.findAllSubFieldsByUsername(username);
        journalList = searchMapper.findAllSubJournalsByUsername(username);
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public List<Journal> getJournalList() {
        return journalList;
    }

    public void setJournalList(List<Journal> journalList) {
        this.journalList = journalList;
    }
}

