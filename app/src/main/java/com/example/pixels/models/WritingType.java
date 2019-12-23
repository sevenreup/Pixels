package com.example.pixels.models;

import androidx.annotation.NonNull;

public class WritingType extends PostType {
    private Image image;
    private String desc;

    public WritingType() {
        super();
        image = new Image();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Image: " + image + "\n" + "Description: " + desc +
                "\n Content: " + getContent().toString();
    }
}
