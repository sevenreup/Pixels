package com.example.pixels.models;

import androidx.annotation.NonNull;

public class PostContent {
    private int type;
    private String content;
    private int id;

    public PostContent() {
    }

    public PostContent(int id, int type, String content) {
        this.type = type;
        this.content = content;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ Type: " + type + " content : { "  + content + " } }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
