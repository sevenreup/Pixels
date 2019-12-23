package com.example.pixels.ui.upload;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.ImagePicker;
import com.example.pixels.epoxy.controller.PostViewController;
import com.example.pixels.models.Image;
import com.example.pixels.models.PostContent;
import com.example.pixels.ui.frags.ImagePickerFragment;
import com.example.pixels.vewmodels.UploadViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pixels.Util.Const.POST_IMAGE;

public class ArtUploadFirstFragment extends Fragment implements ImagePicker.ImagePickerListener, PostViewController.PostCallbacks {
    @BindView(R.id.upload_recycler_view)
    EpoxyRecyclerView recyclerView;
    private ImagePickerFragment imagePicker;
    private boolean replaceMode = false;
    private int replaceLocation, randId = 0;
    private PostViewController postViewController;
    private List<PostContent> postList = new ArrayList<>();
    private UploadViewModel uploadViewModel;

    public ArtUploadFirstFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_art_upload_first, container, false);
        ButterKnife.bind(this, view);

        uploadViewModel = ViewModelProviders.of(getActivity()).get(UploadViewModel.class);
        imagePicker = new ImagePickerFragment();
        imagePicker.setListener(this);

        postViewController = new PostViewController(this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setController(postViewController);

        return view;
    }

    @OnClick(R.id.image_input)
    public void addImages() {
        imagePicker.show(getFragmentManager(), "pickImage");
    }

    @Override
    public void singleSelect(Image imageItem) {
        if (replaceMode) {
            imageItem.setId(postList.get(replaceLocation).getId());
            imageItem.setType(postList.get(replaceLocation).getType());
            postList.set(replaceLocation, imageItem);
            postViewController.setData(postList);
            replaceMode = false;
        } else {
            addImage(false, 0, imageItem);
        }
    }

    @Override
    public void multiSelect(List<Image> imageList) {

    }

    @Override
    public void addString(boolean onPos, int position) {

    }

    @Override
    public void addImage(boolean onPos, int position, Image imgPath) {
        imgPath.setId(++randId);
        imgPath.setType(POST_IMAGE);
        if (!onPos)
            postList.add(imgPath);
        else
            postList.add(position, imgPath);

        postViewController.setData(postList);
        updateContent();
    }

    @Override
    public void removeComponent(int position) {
        postList.remove(position);
        postViewController.setData(postList);
        updateContent();
    }

    @Override
    public void replaceImage(int position) {
        replaceMode = true;
        replaceLocation = (position);
        imagePicker.show(getFragmentManager(), "SelectImage");
    }

    @Override
    public void showPopUp(View anchor, int position) {

    }

    @Override
    public void textInputChange(String value, int pos) {

    }

    @Override
    public void toggleControls(boolean state) {

    }
    private void updateContent()
    {
        uploadViewModel.postInEdit.getValue()
                .getContent()
                .setContent(postList);
    }
}
