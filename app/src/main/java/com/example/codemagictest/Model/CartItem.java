package com.example.codemagictest.Model;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("prodId")
    private int prodId;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    private int qte;
    private float price;

    public CartItem(int prodId, String name, String image, int qte, float price) {
        this.prodId = prodId;
        this.name = name;
        this.image = image;
        this.qte = qte;
        this.price = price;
    }

    public int getProdId() {
        return prodId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
