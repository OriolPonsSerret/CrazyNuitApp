package com.example.oriolpons.projectefinalandroid;

/**
 * Created on 26/03/2018.
 */

public class routes {
    private long id;
    private String name;
    private String description;
    private String creator;

    public routes(long id, String name, String description, String creator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
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
}
