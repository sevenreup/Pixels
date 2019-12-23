package com.example.pixels.epoxy.models;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.airbnb.epoxy.EpoxyRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pixels.R;
import com.example.pixels.firebase.FireStorage;
import com.example.pixels.models.PostContent;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

@EpoxyModelClass(layout = R.layout.model_post_type_m1)
public class PostM1 extends EpoxyModelWithHolder<PostM1.PostM1Holder> {
    @EpoxyAttribute String postText;
    @EpoxyAttribute String userName;
    @EpoxyAttribute String likes;
    @EpoxyAttribute String comments;
    @EpoxyAttribute
    List<PostContent> images;
    @EpoxyAttribute
    StorageReference userRef;
    @EpoxyAttribute
    Context context;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener clickListener;

    @Override
    protected PostM1Holder createNewHolder() {
        return new PostM1Holder();
    }

    @Override
    public void bind(@NonNull PostM1Holder holder) {
        super.bind(holder);
        holder.userName.setText(userName);
        holder.comments.setText(comments);
        holder.likes.setText(likes);
        holder.postCont.setOnClickListener(clickListener);

        holder.imageCarousel.buildModelsWith(new EpoxyRecyclerView.ModelBuilderCallback() {
            @Override
            public void buildModels(@NotNull EpoxyController epoxyController) {
                for (int i = 0; i < images.size(); i++) {
                    new CarouselModel_().id(i).context(context)
                            .image(FireStorage.getRefWithPath(images.get(i).getContent()))
                            .addTo(epoxyController);
                }
            }
        });

        Glide.with(context)
                .load(userRef)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userImage);
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_post_type_m1;
    }

    public class PostM1Holder extends BaseEpoxyHolder {
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.image_carousel)
        ImageCarousel imageCarousel;
        @BindView(R.id.user_image)
        ImageView userImage;
        @BindView(R.id.text_comments)
        TextView comments;
        @BindView(R.id.text_likes)
        TextView likes;

        @BindView(R.id.post_container)
        MaterialCardView postCont;
    }
}
