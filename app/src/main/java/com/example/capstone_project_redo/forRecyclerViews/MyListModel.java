package com.example.capstone_project_redo.forRecyclerViews;

public class MyListModel {
    String id, name, category, imageUrl;

    MyListModel()
    {

    }

    public MyListModel(String id, String name, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}