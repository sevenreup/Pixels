package com.example.pixels.ui.upload;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pixels.R;
import com.example.pixels.epoxy.controller.GalleryController;
import com.example.pixels.vewmodels.UploadViewModel;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtUploadSecondFragment extends Fragment {
    GalleryController galleryController;
    UploadViewModel uploadViewModel;

    public ArtUploadSecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_art_upload_second, container, false);
        ButterKnife.bind(this, v);
        uploadViewModel = ViewModelProviders.of(getActivity()).get(UploadViewModel.class);
        return v;
    }

}
