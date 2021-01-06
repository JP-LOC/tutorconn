package com.example.tutorscape;
public class Contacts {
    private	int	id;
    private	String name;
    private	String phno;
    private String title;
    private String image;
    private String phone;

    private String weeklyRate;
    private String trial;

    public Contacts(Integer id, String name, String phno, String title, String image, String weeklyRate, String trial, String phone) {
        this.name = name;
        this.phno = phno;
        this.title = title;
        this.image=image;

        this.id=id;
        this.weeklyRate=weeklyRate;
        this.trial=trial;
        this.phone=phone;
    }
    public Contacts( String name, String phno, String title, String image, String weeklyRate, String trial, String phone) {
        this.name = name;
        this.phno = phno;
        this.title = title;
        this.image=image;

        this.weeklyRate=weeklyRate;
        this.trial=trial;
        this.phone=phone;
    }

    public Contacts() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
