package com.example.finalproject.teacher_ui.chat;


public class MyMessage {
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String message;
    public MyMessage()
    {}
    public MyMessage(String id, String name, String message)
    {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
