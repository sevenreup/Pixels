package com.example.pixels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pixels.firebase.FirebaseSource;
import com.example.pixels.vewmodels.AuthViewModel;

public class AuthViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseSource firebaseSource;

    public AuthViewModelFactory(FirebaseSource firebaseSource) {
        this.firebaseSource = firebaseSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthViewModel();
    }
}
