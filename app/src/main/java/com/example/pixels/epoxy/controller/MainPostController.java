package com.example.pixels.epoxy.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.TypedEpoxyController;
import com.example.pixels.Util.Const;
import com.example.pixels.epoxy.models.PostM1_;
import com.example.pixels.epoxy.models.PostP1;
import com.example.pixels.epoxy.models.PostP1_;
import com.example.pixels.firebase.FireStorage;
import com.example.pixels.models.Post;
import com.example.pixels.models.WritingType;

import java.util.List;

public class MainPostController extends TypedEpoxyController<List<Post>> {
    private PostListener callbacks;
    public MainPostController(Context context, PostListener callbacks) {
        this.context = context;
        this.callbacks = callbacks;
    }

    private Context context;

    @Override
    protected void buildModels(List<Post> data) {
        for (int i = 0; i < data.size(); i++) {
            Log.e("tag", data.get(i).getType() + "" );
            if (data.get(i).getType() == Const.ART) {
                new PostM1_().id(i).postText(data.get(i).getTitle())
                        .images(data.get(i).getContent().getContent())
                        .userRef(FireStorage.getRefWithUrl(data.get(i).getUserImage()))
                        .likes("23")
                        .comments("100")
                        .userName(data.get(i).getUsername())
                        .clickListener((model, parentView, clickedView, position) -> callbacks.onPostClicked(data.get(position)))
                        .context(context)
                        .addTo(this);
            } else {
                WritingType writingType = (WritingType) data.get(i).getContent();
                new PostP1_().id(i)
                        .context(context)
                        .postImage(FireStorage.getRefWithPath(writingType.getImage().getContent()))
                        .cardClick((model, parentView, clickedView, position) -> callbacks.onPostClicked(data.get(position)))
                        .content(writingType.getDesc())
                        .title(data.get(i).getTitle())
                        .addTo(this);
            }

        }
    }

    public interface PostListener {
        void onPostClicked(Post post);
    }
}
