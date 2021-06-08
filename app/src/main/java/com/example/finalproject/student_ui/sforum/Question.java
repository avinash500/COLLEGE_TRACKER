package com.example.finalproject.student_ui.sforum;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable
{
    public Question(){}
    public Question(String QUESTION, String name, String redgno, String date, String subject, String QNo) {
        this.QUESTION = QUESTION;
        this.name = name;
        this.redgno = redgno;
        this.date = date;
        this.subject = subject;
        this.QNo = QNo;
    }
    public String getQUESTION() {
        return QUESTION;
    }

    @Override
    public String toString() {
        return "Question{" +
                "QUESTION='" + QUESTION + '\'' +
                ", name='" + name + '\'' +
                ", redgno='" + redgno + '\'' +
                ", date='" + date + '\'' +
                ", subject='" + subject + '\'' +
                ", QNo='" + QNo + '\'' +
                '}';
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedgno() {
        return redgno;
    }

    public void setRedgno(String redgno) {
        this.redgno = redgno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public String getQNo() {
        return QNo;
    }

    public void setQNo(String QNo) {
        this.QNo = QNo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    String QUESTION, name, redgno, date,subject, QNo;
}
