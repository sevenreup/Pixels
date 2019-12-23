package com.example.pixels.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.BaseFragment;
import com.example.pixels.Util.MarginItemDecoration;
import com.example.pixels.epoxy.controller.MainPostController;
import com.example.pixels.models.NavDir;
import com.example.pixels.models.Post;
import com.example.pixels.models.UploadStatus;
import com.example.pixels.vewmodels.SharedViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends BaseFragment implements UploadStatus, MainPostController.PostListener {

    private SharedViewModel sharedViewModel;
    @BindView(R.id.upload_status)
    TextView uploadStatus;
    @BindView(R.id.post_upload)
    TextView uploadPostStatus;
    @BindView(R.id.upload_cont)
    MaterialCardView uploadContainer;
    @BindView(R.id.all_posts_recycler)
    EpoxyRecyclerView recyclerView;
    @BindView(R.id.all_post_shimmer)
    ShimmerFrameLayout shimmerLayout;
    private MainPostController controller;

    public MainFragment() {
    }


    @Override
    public void onStop() {
        super.onStop();
        shimmerLayout.stopShimmer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        shimmerLayout.startShimmer();
        sharedViewModel.uploadPost.observe(getActivity(), post -> {
            uploadContainer.setVisibility(View.VISIBLE);
            uploadStatus.setText("Uploading");
            uploadPostStatus.setText("Waiting");
            sharedViewModel.startUpload(post, MainFragment.this);
        });
        controller = new MainPostController(getContext(), this);
        recyclerView.setController(controller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelSize(R.dimen.standard_spacing)));
        sharedViewModel.mainPosts.observe(this, posts -> {
            shimmerLayout.setVisibility(View.GONE);
            shimmerLayout.stopShimmer();
            controller.setData(posts);
        });
        sharedViewModel.getAllPosts();
        sharedViewModel.uploadComplete.observe(getActivity(), post -> sharedViewModel.completeUpload(post, MainFragment.this));
        return v;
    }

    public static Fragment newInstance (int instance) {
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARGS_INSTANCE, instance);
        Fragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void uploadStatus(boolean isUpload) {
        if (isUpload){
            uploadStatus.setText("Uploading");
        } else {
            uploadStatus.setText("Done Uploading");
        }
    }

    @Override
    public void uploadProgress(double status) {

    }

    @Override
    public void uploadImageFailed() {
        uploadStatus.setText("Uploading failed");
    }

    @Override
    public void dataUpload(boolean isUpload) {
        if (isUpload) {
            uploadPostStatus.setText("Uploading");
        } else {
            uploadPostStatus.setText("Done uploading");
        }
    }

    @Override
    public void onDataUploadFailed() {
        uploadPostStatus.setText("UploadFailed");
    }

    @Override
    public void onPostClicked(Post post) {
        sharedViewModel.navigation.setValue(new NavDir(NavDir.NavDest.POST, post));
    }
}
