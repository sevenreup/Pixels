package com.example.pixels.models.upload;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pixels.R;
import com.example.pixels.models.BaseEpoxyHolder;

import butterknife.BindView;

@EpoxyModelClass(layout = R.layout.model_image_holder)
public class ImageModel extends EpoxyModelWithHolder<ImageModel.ImageHolder> {
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener imageListener = null;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener deleteListener = null;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener replaceListener = null;
    @EpoxyAttribute String URL;
    @EpoxyAttribute Context context;

    @Override
    public void bind(@NonNull ImageHolder holder) {
        super.bind(holder);
        holder.image.setOnClickListener(imageListener);
        holder.delete.setOnClickListener(deleteListener);
        holder.replace.setOnClickListener(replaceListener);
        Log.e("STUFF", URL);
        Glide.with(context).load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    protected ImageHolder createNewHolder() {
        return new ImageHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_image_holder;
    }

    public class ImageHolder extends BaseEpoxyHolder {
        @BindView(R.id.selected_image)
        ImageView image;
        @BindView(R.id.delete_image)
        Button delete;
        @BindView(R.id.replace_image)
        Button replace;
    }
}
