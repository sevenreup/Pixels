package com.example.pixels.Util;

import androidx.annotation.Nullable;

public class GalleryClasses {
    public interface GalleryAdapterCallBacks {
        void viewMoreClicked();

        void selectionLimit();

        void selectionMode(boolean active);

        void addImage(String image);
    }

    public static class ImageFolder {
        private String path;
        private String folderName;
        private String firstPic;

        public ImageFolder(String path, String folderName) {
            this.path = path;
            this.folderName = folderName;
        }

        public ImageFolder() {
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFolderName() {
            return folderName;
        }

        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }

        public String getFirstPic() {
            return firstPic;
        }

        public void setFirstPic(String firstPic) {
            this.firstPic = firstPic;
        }
    }

    public static class ImageItem {
        private String imageURI;
        private String picturepath;
        private int id;

        public ImageItem(int id, String picturepath) {
            this.id = id;
            this.picturepath = picturepath;
        }

        public String getImageURI() {
            return imageURI;
        }

        public void setImageURI(String imageURI) {
            this.imageURI = imageURI;
        }

        public String getPicturepath() {
            return picturepath;
        }

        public void setPicturepath(String picturepath) {
            this.picturepath = picturepath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof ImageItem) {
                return getPicturepath().equals(((ImageItem) obj).getPicturepath());
            }
            return false;
        }
    }

    public static interface GalleryActionListener {
        public void isSelected(boolean status);
    }
}
