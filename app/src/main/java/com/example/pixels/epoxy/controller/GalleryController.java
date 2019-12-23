package com.example.pixels.epoxy.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.example.pixels.models.GalleryModel;
import com.example.pixels.epoxy.models.GalleyImage_;
import com.example.pixels.epoxy.models.ViewAlbums_;
import com.example.pixels.models.Image;

import java.util.ArrayList;
import java.util.List;

public class GalleryController extends Typed2EpoxyController<List<Image>, Boolean> {
    @AutoModel
    ViewAlbums_ viewAlbums;
    private Context context;
    GalleryModel.GalleryAdapterCallBacks callBacks;
    private boolean isSelectionEnabled = false;
    private ArrayList<Selected> selectedIndex = new ArrayList<>();
    private int selectionLimit = 3;

    public GalleryController(Context context, GalleryModel.GalleryAdapterCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
    }

    @Override
    protected void buildModels(List<Image> photos, Boolean isLoading) {
        viewAlbums.listener((model, parentView, clickedView, position)->{
            Log.d("Feedback", "lonstuff");
          callBacks.viewMoreClicked();
        }).addTo(this);

        for (int i = 0; i < photos.size(); i++) {
            int finalI = i;
            new GalleyImage_().context(context)
                    .clickListener((model, parentView, clickedView, position)->{
                        if (!isSelectionEnabled) {
                            callBacks.addImage(photos.get(finalI).getContent());
                        } else {
                            handleSelection(position, photos.get(position), false, model);
                        }
                    })
                    .longClickListener((model, parentView, clickedView, position) -> {
                        handleSelection(position, photos.get(position), true, model);
                        return true;
                    })
                    .URL(photos.get(i).getContent())
                    .id(i).addTo(this);
        }
    }
    public void handleSelection(int position, Image item, boolean longClick, GalleyImage_ model) {

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
        public Image item;

        public Selected(int position, Image item) {
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
