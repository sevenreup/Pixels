package com.example.pixels.models;

import androidx.annotation.Nullable;

public class GalleryModel {
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

}
