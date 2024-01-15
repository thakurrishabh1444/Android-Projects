package com.example.tiffinwala.entity;

public class Mealmenu {
    private String title;
    private String price;
    private int imageResId;

    public Mealmenu(String title, String price, int imageResId) {
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
