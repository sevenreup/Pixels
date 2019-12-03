package com.example.pixels.ui.main;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pixels.R;
import com.example.pixels.Util.BaseFragment;
import com.example.pixels.vewmodels.SharedViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends BaseFragment {
    private SharedViewModel sharedViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        return v;
    }

    @OnClick(R.id.logout)
    public void logout() {
        sharedViewModel.logout();
    }

    public static Fragment newInstance (int instance) {
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARGS_INSTANCE, instance);
        Fragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
