package com.example.pixels.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pixels.R;
import com.example.pixels.Util.AuthListener;
import com.example.pixels.adapter.AuthViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AuthFragment extends Fragment {
    @BindView(R.id.desc_holder)
    ViewPager viewPager;
    AuthListener listener;

    public AuthFragment() {
    }

    public AuthFragment(AuthListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, v);

        AuthViewPagerAdapter pagerAdapter = new AuthViewPagerAdapter(getContext());
        viewPager.setAdapter(pagerAdapter);
        return v;
    }

    @OnClick(R.id.sign_in)
    public void signInClicked() {
        getFragmentManager().beginTransaction().replace(R.id.auth_container, new LoginFragment(listener)).addToBackStack("Login").commit();
    }

    @OnClick(R.id.fingerprint)
    public void fingerPrintClicked() {
        listener.googleClick();
    }
    @OnClick(R.id.google)
    public void googlelicked() {
        listener.googleClick();
    }

}
