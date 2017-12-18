package com.example.student.model;

import java.io.Serializable;

/**
 * Created by AndreeaT on 11/26/2017.
 */

public class Excursie implements Serializable {

    private String title;
    private String description;
    private String image;
    private String price;
    private Boolean favourite;

    public Excursie(){

    }

    public Excursie(String title, String description, String image, String price) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.favourite = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}