package com.example.pixels.firebase;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

public class FirebaseSource {
    private FirebaseAuth firebaseAuth;

    public FirebaseSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password, AuthListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {

                        listener.onSuccessAuth(currentUser());
                    } else {
                        listener.onFailure(0, null);
                    }
                }
        );
    }

    public void register (String email, String password, String name,AuthListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        updateUserInformation(name, null, listener);
                        listener.onSuccessAuth(currentUser());
                    } else {
                        listener.onFailure(0, null);
                    }
                }
        );
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public FirebaseUser currentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void updateUserInformation(String name, String image, AuthListener listener) {
        currentUser().updateProfile(new UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .setPhotoUri(image == null ? Uri.EMPTY : Uri.parse(image))
                .build())
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                listener.onSuccess(0, null);
                            } else {
                                listener.onFailure(0, null);
                            }
                        });
    }

    public List<UserInfo> getUserInfo() {
        return (List<UserInfo>) currentUser().getProviderData();
    }

    public interface AuthListener{
        public void onSuccessAuth(FirebaseUser user);
        public void onSuccess(int ID, String info);
        public void onFailure(int ID, String info);
    }

}