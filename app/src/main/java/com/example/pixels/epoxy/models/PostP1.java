package com.example.pixels.epoxy.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pixels.R;
import com.example.pixels.Util.PaletteColorTransformation;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

@EpoxyModelClass(layout = R.layout.model_post_type_p1)
public class PostP1 extends EpoxyModelWithHolder<PostP1.PostP1ViewHolder> {
    @EpoxyAttribute
    StorageReference postImage;
    @EpoxyAttribute
    Context context;

    @Override
    public void bind(@NonNull PostP1ViewHolder holder) {
        super.bind(holder);
        Glide.with(context)
                .load(postImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(
                        new BlurTransformation(8))
                .into(holder.postImage);
    }

    @Override
    protected PostP1ViewHolder createNewHolder() {
        return new PostP1ViewHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_post_type_p1;
    }

    public class PostP1ViewHolder extends BaseEpoxyHolder {
        @BindView(R.id.post_image)
        ImageView postImage;
    }
}
