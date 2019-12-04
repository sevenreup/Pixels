package com.example.pixels.ui.upload;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.pixels.R;
import com.example.pixels.Util.Const;
import com.example.pixels.epoxy.controller.PostViewController;
import com.example.pixels.models.EditTextInterface;
import com.example.pixels.models.ImageCont;
import com.example.pixels.models.PostContent;
import com.example.pixels.models.TextCont;
import com.example.pixels.vewmodels.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pixels.Util.Const.POST_IMAGE;


public class UploadFragment extends Fragment implements PostViewController.PostCallbacks {
    @BindView(R.id.edit_recycler)
    EpoxyRecyclerView recyclerView;
    private GalleryViewModel galleryViewModel;
    private PostViewController postViewController;
    private EditTextInterface listener;
    private List<PostContent> postList = new ArrayList<>();
    private int randId = 0;
    private boolean replaceMode = false;
    private int replaceLocation;

    public UploadFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.bind(this, v);

        galleryViewModel = ViewModelProviders.of(getActivity()).get(GalleryViewModel.class);
        galleryViewModel.currentPage.setValue(Const.UPLOAD.POSTCONT);
        galleryViewModel.selectedImage.observe(getActivity(), s -> {
            if (replaceMode) {
                ImageCont rep = (ImageCont) postList.get(replaceLocation);
                rep.setContent(s);
                postList.set(replaceLocation, rep);
                postViewController.setData(postList);
                replaceMode = false;
            } else {
                addImage(false, 0, s);
            }


        });
        galleryViewModel.bottom_Selected.observe(getActivity(), upbtmselection -> {
            switch (upbtmselection) {
                case TEXT:
                    addString(false, 0);
                    break;
                case IMAGE:
                    galleryViewModel.openSelector.setValue(true);
                    break;
                case FINISH:
                    postViewController.textEditor.clearFocus();
                    consolidateData();
//                    galleryViewModel.editMode.setValue(true);
//                    getFragmentManager().beginTransaction().replace(R.id.upload_container, new SelectPostBSFragment()).commit();
                    break;
            }
        });

        postViewController = new PostViewController(this, getContext());
        listener = postViewController.getListener();
        recyclerView.setController(postViewController);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        postViewController.setData(postList);
        return v;
    }
    @OnClick(R.id.italic)
    public void onItalicClick() {
        listener.changeText(Const.EDITOR.ITALIC);
    }
    @OnClick(R.id.strikethrough)
    public void onStrikeClick() {
        listener.changeText(Const.EDITOR.STRIKE_THROUGH);
    }
    @OnClick(R.id.underline)
    public void onUnderlineClick() {
        listener.changeText(Const.EDITOR.UNDERLINE);
    }
    @OnClick(R.id.bold)
    public void onBoldClick() {
        listener.changeText(Const.EDITOR.BOLD);
    }

    @Override
    public void addString(boolean onPos, int position) {
        if (!onPos)
            postList.add(new TextCont(++randId, Const.POST_TEXT, ""));
        else
            postList.add(position, new TextCont(++randId, Const.POST_TEXT,""));

        postViewController.setData(postList);
    }

    @Override
    public void addImage(boolean onPos, int position, String imgPath) {
        if (!onPos)
            postList.add(new ImageCont(++randId, POST_IMAGE,imgPath));
        else
            postList.add(position, new ImageCont(++randId, POST_IMAGE,imgPath));

        postViewController.setData(postList);
    }

    @Override
    public void replaceImage(int position) {
        replaceMode = true;
        replaceLocation = (position);
        galleryViewModel.openSelector.setValue(true);
    }

    @Override
    public void removeComponent(int position) {
        Log.e("TAG", "pos: " + position);
        postList.remove(position);
        postViewController.setData(postList);
    }

    @Override
    public void showPopUp(View anchor, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.edit_widget_add, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.add_image:
                    galleryViewModel.openSelector.setValue(true);
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

    public void consolidateData() {
        galleryViewModel.postInEdit.getValue().setContent(postList);
    }

}
