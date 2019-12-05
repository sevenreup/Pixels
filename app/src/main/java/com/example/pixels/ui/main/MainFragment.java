package com.example.pixels.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pixels.R;
import com.example.pixels.Util.BaseFragment;
import com.example.pixels.models.Post;
import com.example.pixels.models.UploadStatus;
import com.example.pixels.vewmodels.SharedViewModel;
import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends BaseFragment implements UploadStatus {

    private SharedViewModel sharedViewModel;
    @BindView(R.id.upload_status)
    TextView uploadStatus;
    @BindView(R.id.post_upload)
    TextView uploadPostStatus;
    @BindView(R.id.upload_cont)
    MaterialCardView uploadContainer;

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        sharedViewModel.uploadPost.observe(getActivity(), post -> {
            uploadContainer.setVisibility(View.VISIBLE);
            uploadStatus.setText("Uploading");
            uploadPostStatus.setText("Waiting");
            sharedViewModel.startUpload(post, MainFragment.this);
        });
        sharedViewModel.uploadComplete.observe(getActivity(), new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                sharedViewModel.completeUpload(post, MainFragment.this);
            }
        });
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
}
