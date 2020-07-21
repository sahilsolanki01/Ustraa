package com.solanki.sahil.assignment.models;

public class Model_Tab {
    private String name, image_url, id;

    public Model_Tab() {
    }

    public Model_Tab(String name, String image_url, String id) {
        this.name = name;
        this.image_url = image_url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
