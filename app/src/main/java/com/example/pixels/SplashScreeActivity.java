package com.example.pixels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.pixels.ui.AuthActivity;
import com.example.pixels.ui.MainActivity;
import com.example.pixels.vewmodels.SharedViewModel;

public class SplashScreeActivity extends AppCompatActivity {
    SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        setContentView(R.layout.activity_splash_scree);
        if (!sharedViewModel.checkUser()) {
            startActivity(new Intent(this, AuthActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
