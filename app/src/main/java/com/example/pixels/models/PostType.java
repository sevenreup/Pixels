package com.example.pixels.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostType implements Serializable {
    private List<PostContent> content;

    public PostType() {
        this.content = new ArrayList<>();
    }

    public List<PostContent> getContent() {
        return content;
    }

    public void setContent(List<PostContent> content) {
        this.content = content;
    }

}
