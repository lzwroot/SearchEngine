package com.hust.searchengine.Entity;

import java.util.Date;

public class ClassInfo {
    private Integer clsid;
    private String clsName;
    private Integer clsStuNum;
    private String clsTeacher;
    private Date createDate = new Date();

    public Integer getClsid() {
        return clsid;
    }

    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getClsStuNum() {
        return clsStuNum;
    }

    public void setClsStuNum(Integer clsStuNum) {
        this.clsStuNum = clsStuNum;
    }

    public String getClsTeacher() {
        return clsTeacher;
    }

    public void setClsTeacher(String clsTeacher) {
        this.clsTeacher = clsTeacher;
    }
}
