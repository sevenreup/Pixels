package com.example.pixels.models;

public class ImageCont extends PostContent {
    private int alignment;

    public ImageCont(int id, int type, String content) {
        super(id, type, content);
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

}
