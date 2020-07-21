package com.solanki.sahil.assignment.models;

public class Model_Product {
    private String name, image_url, weight_unit;
    private int weight, price, final_price;
    private float rating;
    private boolean in_stock;

    public Model_Product() {
    }

    public Model_Product(String name, String image_url, int weight, String weight_unit, int price, int final_price, float rating, boolean in_stock) {
        this.name = name;
        this.image_url = image_url;
        this.weight = weight;
        this.weight_unit = weight_unit;
        this.price = price;
        this.final_price = final_price;
        this.rating = rating;
        this.in_stock = in_stock;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int final_price) {
        this.final_price = final_price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }
}
