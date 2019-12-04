package com.example.pixels.epoxy.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pixels.R;

import butterknife.BindView;

@EpoxyModelClass(layout = R.layout.model_gallery_image)
public class GalleyImage extends EpoxyModelWithHolder<GalleyImage.GIHolder>  {
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash) View.OnClickListener clickListener;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash) View.OnLongClickListener longClickListener;
    @EpoxyAttribute String URL;
    @EpoxyAttribute Context context;
    private View selection;

    @Override
    protected GIHolder createNewHolder() {
        return new GIHolder();
    }

    @Override
    public void bind(@NonNull GIHolder holder) {
        super.bind(holder);
        holder.imageHolder.setOnClickListener(clickListener);
        holder.imageHolder.setOnLongClickListener(longClickListener);
        Glide.with(context).load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
        selection = holder.selectIndicator;

    }
    @Override
    protected int getDefaultLayout() {
        return R.layout.model_gallery_image;
    }

    public void isSelected(boolean status) {
        if (status)
            selection.setVisibility(View.VISIBLE);
        else
            selection.setVisibility(View.GONE);
    }

    public static class GIHolder extends BaseEpoxyHolder {
        @BindView(R.id.galley_image)
        ImageView image;
        @BindView(R.id.image_holder)
        RelativeLayout imageHolder;
        @BindView(R.id.select_indicator) View selectIndicator;
    }
}
