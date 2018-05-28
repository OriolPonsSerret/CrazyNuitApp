package com.example.oriolpons.projectefinalandroid.Models;

import java.util.ArrayList;

/**
 * Created on 26/03/2018.
 */

public class routes {
    private int id;
    private String measure;
    private String name;
    private String description;
    private String creator;
    private Double assessment;
    private String city;
    private int icon;
    //private ArrayList<String> locals;

    public routes(int id, String measure, String name, String description, String creator, Double assessment, String city, int icon) {
        this.id = id;
        this.measure = measure;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.assessment = assessment;
        this.city = city;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.id = icon;
    }
}
