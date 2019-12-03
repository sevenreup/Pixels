package com.example.pixels.ui.upload;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.pixels.R;
import com.example.pixels.Util.Const;
import com.example.pixels.Util.UploadClasses;
import com.example.pixels.ui.MainActivity;
import com.example.pixels.vewmodels.GalleryViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectPostBSFragment extends Fragment {
    @BindView(R.id.cover)
    MaterialCardView cardView;
    @BindView(R.id.selection_container)
    LinearLayout linearLayout;
    @BindView(R.id.type_chips)
    ChipGroup postTypeGrp;
    @BindView(R.id.post_title)
    EditText postTitle;
    @BindView(R.id.post_tags) EditText postTags;
    @BindView(R.id.post_cover)
    ImageView postCover;
    private GalleryViewModel galleryViewModel;

    private int typeSelected = -1;
    UploadClasses.Post postInfo = new UploadClasses.Post();

    public SelectPostBSFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_post_b, container, false);
        ButterKnife.bind(this, v);
        galleryViewModel = ViewModelProviders.of(getActivity()).get(GalleryViewModel.class);
        galleryViewModel.currentPage.setValue(Const.UPLOAD.UPLOADINFO);
        postTypeGrp.setOnCheckedChangeListener((chipGroup, i) -> {
            switch (i) {
                case R.id.art:
                    typeSelected = Const.ART;
                    break;
                case R.id.poem:
                    typeSelected = Const.POEM;
                    break;

            }
            Toast.makeText(getContext(), "" + typeSelected, Toast.LENGTH_LONG).show();
        });

        postCover.setOnClickListener(view->{
            galleryViewModel.openSelector.setValue(true);
        });

        galleryViewModel.postInEdit.observe(getActivity(), s -> {
            postInfo.setImage(s.getImage());

            if (isAdded()) loadImage(s.getImage());
        });

        if (galleryViewModel.editMode.getValue()) {
            setUpEdit();
        }
        return v;
    }

    private void setUpEdit() {
        linearLayout.setVisibility(View.GONE);
        postInfo = galleryViewModel.postInEdit.getValue();

        postTitle.setText(postInfo.getTitle());
        postTags.setText(postInfo.getTags());
        loadImage(postInfo.getImage());
    }

    private void loadImage(String path) {
        Glide.with(getContext()).load(path).into(postCover);
    }

    @OnClick(R.id.bt_continue)
    public void continueClicked() {
        if (!galleryViewModel.editMode.getValue()) {
            postInfo.setTitle(postTitle.getText().toString());
            postInfo.setTitle(postTitle.getTag().toString());
            galleryViewModel.postInEdit.setValue(postInfo);
            getFragmentManager().beginTransaction().replace(R.id.upload_container, new UploadFragment()).commit();
        } else {
            galleryViewModel.postInEdit.getValue().setTitle(postInfo.getTitle());
            galleryViewModel.postInEdit.getValue().setTags(postInfo.getTags());
            startActivity(new Intent(getContext(), MainActivity.class));

        }
    }

}
