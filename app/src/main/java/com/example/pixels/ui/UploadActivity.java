package com.example.pixels.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.Const;
import com.example.pixels.models.GalleryModel;
import com.example.pixels.epoxy.controller.GalleryController;
import com.example.pixels.models.Post;
import com.example.pixels.ui.upload.SelectPostBSFragment;
import com.example.pixels.vewmodels.GalleryViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends AppCompatActivity implements GalleryModel.GalleryAdapterCallBacks {
    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    GalleryViewModel galleryViewModel;
    private GalleyBottom galleyBottom = new GalleyBottom();
    private List<GalleryModel.ImageItem> images = new ArrayList<>();
    GalleryController galleryController;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        ButterKnife.bind(galleyBottom, bottomSheet);

        galleryController = new GalleryController(this, this);
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        setupRecyclerView();
        setUpBottomSheet();

        galleryViewModel.mutableLiveData.observe(this, imageItems -> {
            images.addAll(imageItems);
            galleryController.setData(images, true);
        });

        galleryViewModel.openSelector.observe(this, aBoolean -> {
            if (aBoolean)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        });

        galleryViewModel.currentPage.observe(this, upload -> {
            switch (upload) {
                case POSTCONT:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetBehavior.setHideable(false);
                    break;
                case UPLOADINFO:
                    bottomSheetBehavior.setHideable(true);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    break;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.upload_container, new SelectPostBSFragment()).commit();
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        galleyBottom.recyclerView.setController(galleryController);
        galleyBottom.recyclerView.setLayoutManager(layoutManager);
        galleyBottom.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    if (galleryViewModel.mutableLiveData.getValue().isEmpty())
                        loadMorePictures(30);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        galleyBottom.editInfo.setOnClickListener(v->{galleryViewModel.bottom_Selected.setValue(Const.UPBTMSELECTION.FINISH);});
        galleyBottom.addImage.setOnClickListener(v->{galleryViewModel.bottom_Selected.setValue(Const.UPBTMSELECTION.IMAGE);});
        galleyBottom.addText.setOnClickListener(v->{galleryViewModel.bottom_Selected.setValue(Const.UPBTMSELECTION.TEXT);});
    }

    private void loadMorePictures(int pageSize) {
        galleryViewModel.getImagesFromGalley(this, pageSize);
    }

    @Override
    public void viewMoreClicked() {
        Toast.makeText(this, "View more", Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectionLimit() {
        Toast.makeText(this, "Selection Limit Reached", Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectionMode(boolean active) {
        Toast.makeText(this, "selection mode toggle " + active, Toast.LENGTH_LONG).show();
        if (!active) {
        }
    }

    @Override
    public void addImage(String image) {
        if (galleryViewModel.currentPage.getValue() == Const.UPLOAD.POSTCONT) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            galleryViewModel.setImage(image);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            Post post = galleryViewModel.postInEdit.getValue();
            post.setImage(image);
            galleryViewModel.postInEdit.setValue(post);
        }

    }

    static class GalleyBottom {
        @BindView(R.id.recycler_view) EpoxyRecyclerView recyclerView;
        @BindView(R.id.add_image)ImageView addImage;
        @BindView(R.id.add_text) ImageView addText;
        @BindView(R.id.edit_info) ImageView editInfo;
    }
}
