package com.andres.notasusat.ui.business;

public class Course {

    private Integer id;
    private String code;
    private String name;
    private String teacher;
    private Integer credits;
    private Integer grade;
    private Boolean state;

    public Course(Integer id, String code, String name, String teacher, Integer credits, Integer grade, Boolean state) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
        this.grade = grade;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
