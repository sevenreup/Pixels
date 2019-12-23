package com.example.pixels.ui.upload;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pixels.R;

import butterknife.ButterKnife;

public class ArtUploadFirstFragment extends Fragment {


    public ArtUploadFirstFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_art_upload_first, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public class ArtPage {}
    public class PoemPage {}

}
