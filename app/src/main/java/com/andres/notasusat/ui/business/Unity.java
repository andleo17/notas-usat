package com.andres.notasusat.ui.business;

public class Unity {

    private Integer id;
    private String description;
    private Float weight;
    private Float grade;

    public Unity(Integer id, String description, Float weight, Float grade) {
        this.id = id;
        this.description = description;
        this.weight = weight;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
