package com.example.pixels.ui.frags;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.ImagePicker;
import com.example.pixels.epoxy.controller.GalleryController;
import com.example.pixels.models.GalleryModel;
import com.example.pixels.models.Image;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePickerFragment extends BottomSheetDialogFragment implements GalleryModel.GalleryAdapterCallBacks {
    private GalleryController galleryController;
    @BindView(R.id.recycler_gallery)
    EpoxyRecyclerView recyclerView;
    private List<Image> images = new ArrayList<>();
    private ImagePicker.ImagePickerListener listener;
    private ImagePicker imagePicker;

    public ImagePickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_picker, container, false);
        ButterKnife.bind(this, v);
        galleryController = new GalleryController(getContext(), this, 0);
        setupRecyclerView();
            loadMorePictures(30);
        return v;
    }
    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView.setController(galleryController);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("SEVEN", "scolled");
                if (layoutManager.findLastVisibleItemPosition() == galleryController.getAdapter().getItemCount( ) -1) {
                    loadMorePictures(30);
                }
            }
        });
    }

    private void loadMorePictures(int pageSize) {
        if (imagePicker == null)
            imagePicker = new ImagePicker(getContext());
        images.addAll(imagePicker.fetchGalleryImages(pageSize));
        galleryController.setData(images, true);
    }

    @Override
    public void viewMoreClicked() {
        Toast.makeText(getContext(), "View more", Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectionLimit() {
        Toast.makeText(getContext(), "Selection Limit Reached", Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectionMode(boolean active) {
        Toast.makeText(getContext(), "selection mode toggle " + active, Toast.LENGTH_LONG).show();
        if (!active) {
        }
    }

    public void setListener(ImagePicker.ImagePickerListener listener) {
        this.listener = listener;
    }

    @Override
    public void addImage(String image) {
        Image item = new Image(0, 0,image);
        listener.singleSelect(item);
        this.dismiss();
    }
}
