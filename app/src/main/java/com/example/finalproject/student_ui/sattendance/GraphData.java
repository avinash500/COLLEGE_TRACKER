package com.example.finalproject.student_ui.sattendance;

public class GraphData


{
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public long getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(long totalPresent) {
        this.totalPresent = totalPresent;
    }

    public long getTotalAbsent() {
        return totalAbsent;
    }

    public GraphData() {
    }

    public GraphData(String subjectName, long totalPresent, long totalAbsent) {
        this.subjectName = subjectName;
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
    }

    public void setTotalAbsent(long totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    private String subjectName ;
    private long  totalPresent;
    private long totalAbsent;

    @Override
    public String toString() {
        return "GraphData{" +
                "subjectName='" + subjectName + '\'' +
                ", totalPresent=" + totalPresent +
                ", totalAbsent=" + totalAbsent +
                '}';
    }
}
