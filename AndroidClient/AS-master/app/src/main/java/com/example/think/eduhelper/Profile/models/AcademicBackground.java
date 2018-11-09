package com.example.think.eduhelper.Profile.models;

public class AcademicBackground {
    private String majorin;
    private String degreelevel;
    private String university;
    private String advatages;

    public String getMajorin() {
        return majorin;
    }

    public void setMajorin(String majorin) {
        this.majorin = majorin;
    }

    public String getDegreelevel() {
        return degreelevel;
    }

    public void setDegreelevel(String degreelevel) {
        this.degreelevel = degreelevel;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAdvatages() {
        return advatages;
    }

    public void setAdvatages(String advatages) {
        this.advatages = advatages;
    }

    public AcademicBackground(String majorin, String degreelevel, String university, String advatages) {
        this.majorin = majorin;
        this.degreelevel = degreelevel;
        this.university = university;
        this.advatages = advatages;
    }
}
