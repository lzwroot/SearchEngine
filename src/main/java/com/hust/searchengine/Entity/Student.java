package com.hust.searchengine.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private Integer stuid;
    private String stu_name;
    private String stu_image_url;
    private boolean stu_sex;
    private String password;
    private String stu_phone;
    private String stu_address;
    private Date stu_birthday;
    private String stu_email;
    private String stu_education;
    private String stu_interest;
    private String my_color;
    private Integer lucky_number;
    private String re_mark;
    private Date create_date = new Date();
    private Integer clsid;
    private String clsName;

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

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_image_url() {
        return stu_image_url;
    }

    public void setStu_image_url(String stu_image_url) {
        this.stu_image_url = stu_image_url;
    }

    public boolean isStu_sex() {
        return stu_sex;
    }

    public void setStu_sex(boolean stu_sex) {
        this.stu_sex = stu_sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStu_phone() {
        return stu_phone;
    }

    public void setStu_phone(String stu_phone) {
        this.stu_phone = stu_phone;
    }

    public String getStu_address() {
        return stu_address;
    }

    public void setStu_address(String stu_address) {
        this.stu_address = stu_address;
    }

    public String getStu_birthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = sdf.format(stu_birthday);
        return birthday;
    }

    public void setStu_birthday(Date stu_birthday) {
        this.stu_birthday = stu_birthday;
    }

    public String getStu_email() {
        return stu_email;
    }

    public void setStu_email(String stu_email) {
        this.stu_email = stu_email;
    }

    public String getStu_education() {
        return stu_education;
    }

    public void setStu_education(String stu_education) {
        this.stu_education = stu_education;
    }

    public String getStu_interest() {
        return stu_interest;
    }

    public void setStu_interest(String stu_interest) {
        this.stu_interest = stu_interest;
    }

    public String getMy_color() {
        return my_color;
    }

    public void setMy_color(String my_color) {
        this.my_color = my_color;
    }

    public Integer getLucky_number() {
        return lucky_number;
    }

    public void setLucky_number(Integer lucky_number) {
        this.lucky_number = lucky_number;
    }

    public String getRe_mark() {
        return re_mark;
    }

    public void setRe_mark(String re_mark) {
        this.re_mark = re_mark;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = new Date();
    }
}
