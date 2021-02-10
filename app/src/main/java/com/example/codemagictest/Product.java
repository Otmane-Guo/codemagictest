package com.example.codemagictest;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String[] images;
    private String name;
    private float price;
    private String description;
    private float rating;

    public Product(String[] images, String name, float price, String description, float rating) {
        this.images = images;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public Product(int id, String[] images, String name, float price, String description, float rating) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    // Temporary -- waiting to update the API
    public Product(int id, String name, float price, String description, float rating) {
        this.id = id;
        //this.images = images;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
