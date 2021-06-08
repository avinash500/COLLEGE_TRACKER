package com.example.finalproject.student_ui.sforum;

public class Solution {
    public Solution(String name, String answer) {
        this.name = name;
        this.answer = answer;
    }

    public Solution() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String name,answer;
}
