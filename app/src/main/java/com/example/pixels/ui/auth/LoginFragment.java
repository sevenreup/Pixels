package com.example.pixels.ui.auth;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pixels.R;
import com.example.pixels.Util.AuthListener;
import com.example.pixels.firebase.FirebaseSource;
import com.example.pixels.ui.MainActivity;
import com.example.pixels.vewmodels.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements FirebaseSource.AuthListener {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    AuthListener listener;
    private AuthViewModel authViewModel;

    public LoginFragment() {
    }

    public LoginFragment(AuthListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);
        authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);
        return v;
    }

    @OnClick(R.id.sign_in)
    public void signInClicked() {
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        authViewModel.login(emailStr, passwordStr, this);
    }
    @OnClick(R.id.fingerprint)
    public void fingerPrintClicked() {
        listener.googleClick();
    }

    @OnClick(R.id.google)
    public void googlelicked() {
        listener.googleClick();
    }

    @Override
    public void onSuccessAuth(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    @Override
    public void onSuccess(int ID, String info) {

    }

    @Override
    public void onFailure(int ID, String info) {

    }
}
