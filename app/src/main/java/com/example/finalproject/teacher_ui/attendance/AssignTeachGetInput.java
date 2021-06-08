package com.example.finalproject.teacher_ui.attendance;


public class AssignTeachGetInput
{
    String Branch;
    String section;
    String Batch;
    String subject;

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    String Semester;
    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subjectCode) {
        this.subject = subjectCode;
    }

}
