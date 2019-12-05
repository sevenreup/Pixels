package com.example.pixels.firebase;

import androidx.annotation.NonNull;

import com.example.pixels.models.Post;
import com.example.pixels.models.UploadStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FireStore {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseSource  firebaseSource = new FirebaseSource();

    public FireStore() {
    }

    public void uploadPost(Post post, UploadStatus status) {
        db.collection("users").document(firebaseSource.currentUser().getUid())
                .collection("posts").add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        status.dataUpload(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        status.onDataUploadFailed();
                    }
                });
    }
}
