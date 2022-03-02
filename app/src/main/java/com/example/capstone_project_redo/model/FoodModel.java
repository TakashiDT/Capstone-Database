package com.example.capstone_project_redo.model;

public class FoodModel {
    String name, seller, price, imageUrl;

    FoodModel() {

    }

    public FoodModel(String name, String seller, String price, String imageUrl) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
