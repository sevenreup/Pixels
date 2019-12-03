package com.example.pixels.models;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;

import butterknife.ButterKnife;

public class BaseEpoxyHolder extends EpoxyHolder {
    @Override
    protected void bindView(@NonNull View itemView) {
        ButterKnife.bind(this, itemView);
    }
}
