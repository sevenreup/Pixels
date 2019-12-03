package com.example.pixels.vewmodels;

import com.example.pixels.firebase.FirebaseSource;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {
    private FirebaseSource firebaseSource = new FirebaseSource();

    public AuthViewModel() {  }

    public FirebaseUser getUser () {
        return firebaseSource.currentUser();
    }

    public void login(String email, String password, FirebaseSource.AuthListener listener) {
        firebaseSource.login(email, password, listener);
    }

    public void register(String email, String password, String name, FirebaseSource.AuthListener listener) {
        firebaseSource.register(email, password, name,listener);
    }

    public Boolean checkUser() {
        return getUser() != null;
    }

    public void logout () {
        firebaseSource.logout();
    }
}
