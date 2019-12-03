package com.example.pixels.Util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UploadClasses {
    public interface EditTextInterface {
        public void changeText(Const.EDITOR STYLE);
        public void changeAlignment(Const.EDITOR STYLE);
    }

    public static class Post {
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
    }
    public static class TextCont extends PostContent {

        public TextCont(int id, int type, String content) {
            super(id, type, content);
        }
    }
    public static class ImageCont extends PostContent {
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

    public static class PostContent {
        private int type;
        private String content;
        private int id;

        public PostContent(int id, int type, String content) {
            this.type = type;
            this.content = content;
            this.id = id;
        }

        @NonNull
        @Override
        public String toString() {
            return "Type: " + type + " content : { "  + content + " }";
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
}
