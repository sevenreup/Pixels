package com.example.pixels.models;

import androidx.annotation.Nullable;

public class Image extends PostContent {
    private String imageURI;

    public Image(int id, int type, String content) {
        super(id, type, content);
    }

    public Image() {
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Image) {
            return getContent().equals(((Image) obj).getContent());
        }
        return false;
    }
}
