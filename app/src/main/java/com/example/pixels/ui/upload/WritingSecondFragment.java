package com.example.pixels.ui.upload;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.Const;
import com.example.pixels.Util.ImagePicker;
import com.example.pixels.epoxy.controller.PostViewController;
import com.example.pixels.models.Image;
import com.example.pixels.models.PostContent;
import com.example.pixels.models.TextCont;
import com.example.pixels.ui.frags.ImagePickerFragment;
import com.example.pixels.vewmodels.UploadViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pixels.Util.Const.POST_IMAGE;

public class WritingSecondFragment extends Fragment implements PostViewController.PostCallbacks {

    @BindView(R.id.image_input)
    ImageView imageInput;
    @BindView(R.id.text_input)
    ImageView textInput;
    @BindView(R.id.upload_recycler_view)
    EpoxyRecyclerView recyclerView;
    @BindView(R.id.text_control_cont)
    MaterialCardView controlContainer;

    private boolean replaceMode = false;
    private int replaceLocation;
    private ImagePickerFragment imagePicker = new ImagePickerFragment();

    private PostViewController postViewController;
    private List<PostContent> postList = new ArrayList<>();

    private UploadViewModel uploadViewModel;

    private int randId = 0;

    public WritingSecondFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_writing_second, container, false);
        ButterKnife.bind(this, v);
        postViewController = new PostViewController(this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setController(postViewController);
        setUpClicks();
        controlContainer.setVisibility(View.GONE);
        imagePicker.setListener(new ImagePicker.ImagePickerListener() {
            @Override
            public void singleSelect(Image imageItem) {
                if (replaceMode) {
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
        });

        uploadViewModel = ViewModelProviders.of(getActivity()).get(UploadViewModel.class);

        uploadViewModel.consolidate.observe(this, aBoolean -> {
            uploadViewModel.postInEdit.getValue().getContent().setContent(postList);
            Log.e("second", "observed");
        });
        return v;
    }

    private void setUpClicks() {
        imageInput.setOnClickListener(v -> imagePicker.show(getFragmentManager(), "SelectImage"));
        textInput.setOnClickListener(v -> addString(false, 0));
    }

    @Override
    public void addString(boolean onPos, int position) {
        if (!onPos)
            postList.add(new TextCont(++randId, Const.POST_TEXT, ""));
        else
            postList.add(position, new TextCont(++randId, Const.POST_TEXT,""));

        postViewController.setData(postList);
        updateContent();
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
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.edit_widget_add, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.add_image:
                    imagePicker.show(getFragmentManager(), "SelectImage");
                    break;
                case R.id.add_text:
                    addString(true, (position));
                    break;
                case R.id.delete:
                    removeComponent((position));
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void textInputChange(String value, int pos) {
        postList.get(pos).setContent(value);
    }

    @Override
    public void toggleControls(boolean state) {
        Toast.makeText(getContext(), "all the smoke", Toast.LENGTH_SHORT).show();
        if (state)
            controlContainer.setVisibility(View.VISIBLE);
        else
            controlContainer.setVisibility(View.GONE);
    }

    private void updateContent()
    {
        uploadViewModel.postInEdit.getValue()
                .getContent()
                .setContent(postList);
    }

    @OnClick(R.id.bold_action) public void actionBold() {
        postViewController.textEditor.changeStyle(Const.EDITOR.BOLD);
    }
    @OnClick(R.id.italic_action) public void actionItalic() {
        postViewController.textEditor.changeStyle(Const.EDITOR.ITALIC);
    }
    @OnClick(R.id.strike_action) public void actionStrike() {
        postViewController.textEditor.changeStyle(Const.EDITOR.STRIKE_THROUGH);
    }
    @OnClick(R.id.underline_action) public void actionUnderline() {
        postViewController.textEditor.changeStyle(Const.EDITOR.UNDERLINE);
    }
}
