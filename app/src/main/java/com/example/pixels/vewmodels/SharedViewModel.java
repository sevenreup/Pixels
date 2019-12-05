package com.example.pixels.vewmodels;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pixels.Util.Const;
import com.example.pixels.firebase.FireStorage;
import com.example.pixels.firebase.FireStore;
import com.example.pixels.firebase.FirebaseSource;
import com.example.pixels.models.Post;
import com.example.pixels.models.PostContent;
import com.example.pixels.models.UploadStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<Boolean> loginInfo = new MutableLiveData<>();
    public MutableLiveData<Post> uploadPost =  new MutableLiveData<>();
    public MutableLiveData<Post> uploadComplete = new MutableLiveData<>();

    private FirebaseSource firebaseSource = new FirebaseSource();
    private FireStore fireStore = new FireStore();
    private FireStorage fireStorage = new FireStorage();

    public void logout() {
        firebaseSource.logout();
        loginInfo.setValue(false);
    }

    public Boolean checkUser() {
        return firebaseSource.currentUser() != null;
    }

    public void startUpload(Post post, UploadStatus status) {
        fireStorage.startUpload(post, firebaseSource.currentUser(), uploadComplete, status);
    }

    public void completeUpload(Post post, UploadStatus status) {
        status.dataUpload(true);
        fireStore.uploadPost(post, status);
        Log.e("Complete post", post.toString());
    }
}
