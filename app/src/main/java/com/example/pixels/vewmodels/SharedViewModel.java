package com.example.pixels.vewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pixels.firebase.FirebaseSource;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<Boolean> loginInfo = new MutableLiveData<>();
    private FirebaseSource firebaseSource = new FirebaseSource();

    public void logout() {
        firebaseSource.logout();
        loginInfo.setValue(false);
    }

    public Boolean checkUser() {
        return firebaseSource.currentUser() != null;
    }
}
