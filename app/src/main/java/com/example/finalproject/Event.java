package com.example.finalproject;

import android.widget.Button;
import android.widget.ImageView;

public class Event {
    String clubName;
    String imgView;
    String comment;
    long like;

    public String getClubName() {
        return clubName;
    }

    public Event(){}
    public Event(String clubName, String imgView, String comment, long like) {
        this.clubName = clubName;
        this.imgView = imgView;
        this.comment = comment;
        this.like = like;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getImgView() {
        return imgView;
    }

    public void setImgView(String imgView) {
        this.imgView = imgView;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }
}
