package com.example.codehero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comics {
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
    private List<ItemComics> items = null;

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

    public List<ItemComics> getItems() {
        return items;
    }

    public void setItems(List<ItemComics> items) {
        this.items = items;
    }
}
