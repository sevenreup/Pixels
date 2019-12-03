package com.example.pixels.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.example.pixels.Util.GalleryClasses;
import com.example.pixels.models.GalleyImage_;
import com.example.pixels.models.ViewAlbums_;

import java.util.ArrayList;
import java.util.List;

public class GalleryController extends Typed2EpoxyController<List<GalleryClasses.ImageItem>, Boolean> {
    @AutoModel
    ViewAlbums_ viewAlbums;
    private AppCompatActivity activity;
    GalleryClasses.GalleryAdapterCallBacks callBacks;
    private boolean isSelectionEnabled = false;
    private ArrayList<Selected> selectedIndex = new ArrayList<>();
    private int selectionLimit = 3;
    private GalleryClasses.GalleryActionListener listener;

    public GalleryController(AppCompatActivity activity, GalleryClasses.GalleryAdapterCallBacks callBacks) {
        this.activity = activity;
        this.callBacks = callBacks;
    }

    @Override
    protected void buildModels(List<GalleryClasses.ImageItem> photos, Boolean isLoading) {
        viewAlbums.listener((model, parentView, clickedView, position)->{
            Log.d("Feedback", "lonstuff");
          callBacks.viewMoreClicked();
        }).addTo(this);

        for (int i = 0; i < photos.size(); i++) {
            int finalI = i;
            new GalleyImage_().context(activity.getBaseContext())
                    .clickListener((model, parentView, clickedView, position)->{
                        if (!isSelectionEnabled) {
                            callBacks.addImage(photos.get(finalI).getPicturepath());
                        } else {
                            handleSelection(position, photos.get(position), false, model);
                        }
                    })
                    .longClickListener((model, parentView, clickedView, position) -> {
                        handleSelection(position, photos.get(position), true, model);
                        return true;
                    })
                    .URL(photos.get(i).getPicturepath())
                    .id(i).addTo(this);
        }
    }
    public void handleSelection(int position, GalleryClasses.ImageItem item, boolean longClick, GalleyImage_ model) {

        if (longClick && !isSelectionEnabled) {
            Selected selectedItem = new Selected(position, item);
            selectedIndex.add(selectedItem);
            model.isSelected(true);
            isSelectionEnabled = true;
            callBacks.selectionMode(isSelectionEnabled);
        } else if (isSelectionEnabled) {
            Selected selectedItem = new Selected(position, item);
            if (!selectedIndex.contains(selectedItem))
            {
                selectedIndex.add(selectedItem);
                model.isSelected(true);
                Log.e("STUFF", "status-doesnt: " + selectedIndex.size() + " " +selectedIndex.contains(selectedItem));
            } else {
                    selectedIndex.remove(selectedItem);
                    model.isSelected(false);
                    if (selectedIndex.isEmpty()) isSelectionEnabled = false;
                    Log.e("STUFF", "status-does: " + selectedIndex.size()+ " " +selectedIndex.contains(selectedItem));
                    callBacks.selectionMode(isSelectionEnabled);
            }

        }
    }

    public class Selected {
        public int position;
        public GalleryClasses.ImageItem item;

        public Selected(int position, GalleryClasses.ImageItem item) {
            this.position = position;
            this.item = item;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof Selected) {
                return item.getId() == ((Selected)obj).item.getId();
            }
            return false;
        }
    }
}
