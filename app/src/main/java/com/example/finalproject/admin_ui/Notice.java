package com.example.finalproject.admin_ui;

public class Notice
{
    String date;
    String title;

    public Notice(String title, String desc , String date) {
        this.date = date;
        this.title = title;
        this.desc = desc;
    }

    public Notice() {

    }
    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String desc;
}
