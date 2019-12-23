package com.example.pixels.ui.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pixels.R;
import com.example.pixels.Util.BaseFragment;
import com.example.pixels.ui.UploadActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.open)
    public void open() {
        startActivity(new Intent(getContext(), UploadActivity.class));
    }

    public static Fragment newInstance (int instance) {
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARGS_INSTANCE, instance);
        Fragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
