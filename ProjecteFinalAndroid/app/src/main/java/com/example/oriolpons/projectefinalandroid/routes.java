package com.example.oriolpons.projectefinalandroid;

/**
 * Created on 26/03/2018.
 */

public class routes {
    private long id;
    private String name;
    private String description;
    private String creator;
    private Double assessment;

    public routes(long id, String name, String description, String creator, Double assessment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Double getAssessment() {
        return assessment;
    }

    public void setAssessment(Double assessment) {
        this.assessment = assessment;
    }
}
