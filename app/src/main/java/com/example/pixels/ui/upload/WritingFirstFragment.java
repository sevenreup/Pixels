package com.example.pixels.ui.upload;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pixels.R;
import com.example.pixels.Util.ImagePicker;
import com.example.pixels.models.Image;
import com.example.pixels.models.Post;
import com.example.pixels.models.WritingType;
import com.example.pixels.ui.frags.ImagePickerFragment;
import com.example.pixels.vewmodels.UploadViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class WritingFirstFragment extends Fragment {
    private UploadViewModel uploadViewModel;
    private WritingType writingType;

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.tags)
    EditText tags;
    @BindView(R.id.description)
    EditText desc;
    @BindView(R.id.select_image)
    CircleImageView selectImage;
    private ImagePickerFragment imagePicker = new ImagePickerFragment();

    public WritingFirstFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_writing_first, container, false);
        ButterKnife.bind(this, v);
        uploadViewModel = ViewModelProviders.of(getActivity()).get(UploadViewModel.class);
        writingType = (WritingType) uploadViewModel.postInEdit.getValue().getContent();

        uploadViewModel.consolidate.observe(this, aBoolean -> {
            String titleStr = title.getText().toString();
            String tagsStr = tags.getText().toString();
            String descStr = desc.getText().toString();

            Post post = uploadViewModel.postInEdit.getValue();
            post.setTitle(titleStr);
            post.setTags(tagsStr);

            writingType.setDesc(descStr);

            post.setContent(writingType);
            uploadViewModel.postInEdit.setValue(post);
        });
        return v;
    }
    @OnClick(R.id.select_image)
    public void selectImage() {
        imagePicker.setListener(new ImagePicker.ImagePickerListener() {
            @Override
            public void singleSelect(Image imageItem) {
                writingType.setImage(imageItem);
                Glide.with(getContext())
                        .load(imageItem.getContent())
                        .into(selectImage);
            }

            @Override
            public void multiSelect(List<Image> imageList) {

            }
        });
        imagePicker.show(getFragmentManager(), "select");
    }
}
