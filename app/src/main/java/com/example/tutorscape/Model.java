package com.example.tutorscape;


public class Model {
    private String name;
    private String title;
    private String image;
    private String phone;
    private String subject;
    private String weeklyRate;
    private String trial;
    private Model(){}

    public Model(String name, String title,String image,String subject,String weeklyRate, String trial,String phone) {
        this.name = name;
        this.title = title;
        this.image=image;
        this.subject=subject;
        this.weeklyRate=weeklyRate;
        this.trial=trial;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getWeeklyRate() {
        return weeklyRate;
    }

    public void setWeeklyRate(String weeklyRate) {
        this.weeklyRate = weeklyRate;
    }
    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}