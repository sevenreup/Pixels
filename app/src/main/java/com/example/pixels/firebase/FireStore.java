package com.example.pixels.firebase;

import androidx.annotation.NonNull;

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

    public void uploadPost(Map<String, Object> post, OnSuccessListener<DocumentReference> listener, OnFailureListener failureListener) {
        db.collection("users").document(firebaseSource.currentUser().getUid())
                .collection("posts").add(post).addOnSuccessListener(listener).addOnFailureListener(failureListener);
    }
}
