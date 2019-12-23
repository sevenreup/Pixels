package com.example.pixels.epoxy.models;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.airbnb.epoxy.ModelView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pixels.R;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;

@EpoxyModelClass(layout = R.layout.model_carousel_view)
public class CarouselModel extends EpoxyModelWithHolder<CarouselModel.CarouselViewModel> {
    @EpoxyAttribute
    StorageReference image;
    @EpoxyAttribute
    Context context;

    @Override
    public void bind(@NonNull CarouselViewModel holder) {
        super.bind(holder);
        Glide.with(context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    protected CarouselViewModel createNewHolder() {
        return new CarouselViewModel();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_carousel_view;
    }

    public class CarouselViewModel extends BaseEpoxyHolder {
        @BindView(R.id.carousel_item)
        ImageView imageView;
    }
}
