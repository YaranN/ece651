package com.example.think.eduhelper.Profile.models;

public class Profile {
    private String username;
    private String gender;
    private String nationality;
    private AcademicBackground academicbackground;
    private String uid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public AcademicBackground getAcademicbackground() {
        return academicbackground;
    }

    public void setAcademicbackground(AcademicBackground academicbackground) {
        this.academicbackground = academicbackground;
    }

    public Profile(String username, String gender, String nationality, AcademicBackground academicbackground, String uid) {
        this.username = username;
        this.gender = gender;
        this.nationality = nationality;
        this.academicbackground = academicbackground;
        this.uid = uid;
    }
}
