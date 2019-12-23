package com.example.pixels.epoxy.models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.epoxy.Carousel;

import org.jetbrains.annotations.NotNull;

public class ImageCarousel extends Carousel {
    public ImageCarousel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @NotNull
    @Override
    protected LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    public ImageCarousel(Context context) {
        super(context);
    }
}
