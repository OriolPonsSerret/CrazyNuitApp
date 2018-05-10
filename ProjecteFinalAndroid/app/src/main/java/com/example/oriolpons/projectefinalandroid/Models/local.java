package com.example.oriolpons.projectefinalandroid.Models;

/**
 * Created on 30/03/2018.
 */

public class local {

    private long id;
    private String type;
    private String name;
    private String description;
    private Double assessment;
    private String address;
    private String opening_hours;
    private String schedule_close;
    private String gastronomy;
    private int category;
    private Double entrance_price;
    private int icon;

    public local(long id, String type, String name, String description, Double assessment, String address, String opening_hours, String schedule_close, String gastronomy, int category, Double entrance_price, int icon) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.assessment = assessment;
        this.address = address;
        this.opening_hours = opening_hours;
        this.schedule_close = schedule_close;
        this.gastronomy = gastronomy;
        this.category = category;
        this.entrance_price = entrance_price;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getSchedule_close() {
        return schedule_close;
    }

    public void setSchedule_close(String schedule_close) {
        this.schedule_close = schedule_close;
    }

    public String getGastronomy() {
        return gastronomy;
    }

    public void setGastronomy(String gastronomy) {
        this.gastronomy = gastronomy;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Double getEntrance_price() {
        return entrance_price;
    }

    public void setEntrance_price(Double entrance_price) {
        this.entrance_price = entrance_price;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
