package com.example.codehero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Events {
    @SerializedName("available")
    @Expose
    private String available;

    @SerializedName("returned")
    @Expose
    private String returned;

    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;

    @SerializedName("items")
    @Expose
    private List<ItemEvents> items = null;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<ItemEvents> getItems() {
        return items;
    }

    public void setItems(List<ItemEvents> items) {
        this.items = items;
    }
}
