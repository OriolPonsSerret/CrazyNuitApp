package com.example.oriolpons.projectefinalandroid.Models;

/**
 * Created on 30/03/2018.
 */

public class local {

    private long id;
    private String name;
    private String description;
    private Double assessment;

    public local(long id, String name, String description, Double assessment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.assessment = assessment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAssessment() {
        return assessment;
    }

    public void setAssessment(Double assessment) {
        this.assessment = assessment;
    }
}
