package com.example.codemagictest.Model;

import android.util.Log;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order {

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("stat")
    private String stat; //shipping, arrived

    private int qte;
    private float price;


    public Order(String name, String image, String stat, float price) {
        this.name = name;
        this.image = image;
        this.stat = stat;
        this.price = price;
    }

    public Order(String name, String image, int qte, float price) {
        this.name = name;
        this.image = image;
        this.qte = qte;
        this.price = price;
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

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

}

