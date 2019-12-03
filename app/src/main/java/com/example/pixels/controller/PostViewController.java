package com.example.pixels.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.airbnb.epoxy.TypedEpoxyController;
import com.example.pixels.Util.Const;
import com.example.pixels.Util.TextEditor;
import com.example.pixels.Util.UploadClasses;
import com.example.pixels.models.upload.ImageModel_;
import com.example.pixels.models.upload.TextModel_;

import org.json.JSONException;

import java.util.List;

public class PostViewController extends TypedEpoxyController<List<UploadClasses.PostContent>> implements
        UploadClasses.EditTextInterface{
    public TextEditor textEditor = new TextEditor();
    private PostCallbacks callbacks;
    private Context context;


    public PostViewController(PostCallbacks callbacks, Context context) {
        this.callbacks = callbacks;
        this.context = context;
    }

    @Override
    protected void buildModels(List<UploadClasses.PostContent> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getType() == Const.POST_IMAGE) {
                new ImageModel_().id(data.get(i).getId()).context(context).URL(data.get(i).getContent())
                        .imageListener((model, parentView, clickedView, position) -> {
                        })
                        .replaceListener((model, parentView, clickedView, position) -> {
                            callbacks.replaceImage(position);
                        })
                        .deleteListener((model, parentView, clickedView, position) -> {
                            callbacks.removeComponent(position);
                        })
                        .addTo(this);
            } else {
                int finalI = i;
                new TextModel_().id(data.get(i).getId()).longClickListener((model, parentView, clickedView, position) -> {
                    return true;
                }).clickListener((model, parentView, clickedView, position) -> {
                    callbacks.showPopUp(clickedView, position);
                }).listener((v, hasFocus) -> {
                    if (hasFocus)
                    {
                        textEditor.setEditText(((EditText) v), finalI);
                    } else {
                        try {
                            callbacks.textInputChange(textEditor.getText(), finalI);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).addTo(this);
            }
        }
    }

    @Override
    public void changeText(Const.EDITOR STYLE) {
        if (textEditor.getEditText()) {
            textEditor.changeStyle(STYLE);
        }
    }

    @Override
    public void changeAlignment(Const.EDITOR STYLE) {
        textEditor.changeAlign(STYLE);
    }

    public UploadClasses.EditTextInterface getListener() {
        return this;
    }


    public void getProcessedData() {
    }

    public interface PostCallbacks {
        void addString(boolean onPos, int position);
        void addImage(boolean onPos, int position, String imgPath);
        void removeComponent(int position);
        void replaceImage(int position);
        void showPopUp(View anchor, int position);
        void textInputChange(String value, int pos);
    }
}
