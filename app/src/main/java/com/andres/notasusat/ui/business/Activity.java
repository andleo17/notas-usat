package com.andres.notasusat.ui.business;

public class Activity {
    private Integer id;
    private String description;
    private Float weight;
    private Float grade;

    public Activity(Integer id, String name, Float weight, Float grade) {
        this.id = id;
        this.description = name;
        this.weight = weight;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return description;
    }

    public void setName(String name) {
        this.description = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }
}
