package com.example.capstone_project_redo.model;

public class CategoryInsideModel {
    String name, category, seller, price, priceExtension, description, imageUrl, productId;

    CategoryInsideModel() {

    }

    public CategoryInsideModel(String name, String category, String seller, String price, String priceExtension, String description, String imageUrl, String productId) {
        this.name = name;
        this.category = category;
        this.seller = seller;
        this.price = price;
        this.priceExtension = priceExtension;
        this.description = description;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getPriceExtension() {
        return priceExtension;
    }

    public void setPriceExtension(String priceExtension) {
        this.priceExtension = priceExtension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
