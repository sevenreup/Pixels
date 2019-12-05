package com.example.pixels.models;

public interface UploadStatus {
    void uploadStatus(boolean isUpload);
    void uploadProgress(double status);
    void uploadImageFailed();
    void dataUpload(boolean isUpload);
    void onDataUploadFailed();
}
