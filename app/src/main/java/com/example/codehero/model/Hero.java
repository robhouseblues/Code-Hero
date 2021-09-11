package com.example.codehero.model;

import java.io.Serializable;
import java.util.List;

public class Hero implements Serializable {
    private String id;
    private String name;
    private String description;
    private String url;
    private List<ItemSeries> series;
    private List<ItemEvents> events;

    public Hero(String id, String name, String description, String url, List<ItemSeries> series, List<ItemEvents> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.series = series;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ItemSeries> getSeries() {
        return series;
    }

    public void setSeries(List<ItemSeries> series) {
        this.series = series;
    }

    public List<ItemEvents> getEvents() {
        return events;
    }

    public void setEvents(List<ItemEvents> events) {
        this.events = events;
    }
}
