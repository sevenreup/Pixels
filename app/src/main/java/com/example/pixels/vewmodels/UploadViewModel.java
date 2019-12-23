package com.example.pixels.vewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pixels.Util.Const;
import com.example.pixels.Util.ImagePicker;
import com.example.pixels.models.ArtType;
import com.example.pixels.models.GalleryModel;
import com.example.pixels.models.Image;
import com.example.pixels.models.Post;
import com.example.pixels.models.WritingType;

import java.util.ArrayList;
import java.util.List;

public class UploadViewModel extends ViewModel {
    public MutableLiveData<Post> postInEdit = new MutableLiveData<>();
    public MutableLiveData<Boolean> consolidate = new MutableLiveData<>();

    public UploadViewModel() {
        postInEdit.setValue(new Post());
    }

    public void setUpUploads(int page) {
        if (page == Const.ART) {
            postInEdit.getValue().setContent(new ArtType());
        } else {
            postInEdit.getValue().setContent(new WritingType());
        }
    }
}
