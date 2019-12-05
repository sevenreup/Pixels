package com.example.pixels.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pixels.models.Post;
import com.example.pixels.models.PostContent;
import com.example.pixels.models.UploadStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.pixels.Util.Const.POST_IMAGE;

public class FireStorage implements OnSuccessListener<UploadTask.TaskSnapshot>, OnFailureListener {
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Post uploadPost;
    private List<PostContent> postContentList;
    private int count = 0;
    private int total = 0;
    private String userId = null;

    private void uploadPostImage(TaskType jobType, String imagePath,
                                 OnProgressListener<UploadTask.TaskSnapshot> progressListener, OnFailureListener failureListener,
                                 OnSuccessListener<UploadTask.TaskSnapshot> successListener) {
        Uri path = Uri.fromFile(new File(imagePath));
        StorageReference filePath = null;
        switch (jobType) {
            case MAIN_IMAGE:
            case POST_IMAGE:
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
                Date date = new Date();
                filePath = storageReference.child(userId + "/posts/" + path.getLastPathSegment());
                break;
            case PROFILE_IMAGE:
                filePath = storageReference.child(userId + "/profile/" + path.getLastPathSegment());
                break;
        }

        UploadTask task = filePath.putFile(path);
        task.addOnProgressListener(progressListener).addOnFailureListener(failureListener).addOnSuccessListener(successListener);
    }

    public void startUpload(Post post, FirebaseUser firebaseUser, MutableLiveData<Post> mutablePost, UploadStatus status) {
        uploadPost = post;
        userId = firebaseUser.getUid();
        status.uploadStatus(true);
        uploadPostImage(TaskType.MAIN_IMAGE, post.getImage(), new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                status.uploadImageFailed();
            }
        }, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadPost.setImage(taskSnapshot.getMetadata().getPath());

                postContentList = uploadPost.getContent();
                total = getTotalSize();
                for (int i = 0; i < uploadPost.getContent().size(); i++) {
                    if (uploadPost.getContent().get(i).getType() == POST_IMAGE) {
                        PostContent content = uploadPost.getContent().get(i);
                        int finalI = i;
                        uploadPostImage(TaskType.POST_IMAGE, content.getContent(),
                                new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                    }
                                }, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                }, taskSnapshot2 -> {
                                    Log.e("Upload", taskSnapshot2.getMetadata().getPath());
                                    content.setContent(taskSnapshot2.getMetadata().getPath());
                                    postContentList.set(finalI, content);
                                    count++;
                                    Log.e("COUNT", "total: " + total + " position : " + count);
                                    if (count == total) {
                                        uploadPost.setContent(postContentList);
                                        mutablePost.setValue(uploadPost);
                                        Log.e("Final i think", "total: " + total + " position : " + count);
                                        status.uploadStatus(false);
                                    }
                                });
                    }

                }
            }
        });

    }

    private int getTotalSize() {
        int counter = 0;
        for (PostContent post:
             postContentList) {
            if (post.getType() == POST_IMAGE)
                counter++;
        }
        return counter;
    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

    }

    public static enum TaskType {
        MAIN_IMAGE,
        POST_IMAGE,
        PROFILE_IMAGE
    }
}
