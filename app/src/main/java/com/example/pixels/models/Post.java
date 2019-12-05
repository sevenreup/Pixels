package com.example.pixels.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private List<PostContent> content;
    private String title;
    private String tags;
    private String image;



    public Post() {
        content = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<PostContent> getContent() {
        return content;
    }

    public void setContent(List<PostContent> content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "title : " + title + "\n" +
                "tags : " + tags + "\n" +
                "image :" + image + "\n" +
                content.toString();
    }
}
