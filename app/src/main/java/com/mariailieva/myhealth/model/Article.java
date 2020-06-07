package com.mariailieva.myhealth.model;

import java.io.Serializable;

public class Article implements Serializable {

    private String title;
    private String text;
    private String imageURL;

    public Article(String title, String text, String imageURL) {
        this.title = title;
        this.text = text;
        this.imageURL = imageURL;
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
