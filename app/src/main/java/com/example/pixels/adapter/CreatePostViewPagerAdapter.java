package com.example.pixels.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pixels.Util.Const;
import com.example.pixels.ui.upload.ArtUploadFirstFragment;
import com.example.pixels.ui.upload.ArtUploadSecondFragment;
import com.example.pixels.ui.upload.WritingFirstFragment;
import com.example.pixels.ui.upload.WritingSecondFragment;

public class CreatePostViewPagerAdapter extends FragmentStateAdapter {
    private int page = 0;

    public CreatePostViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int page) {
        super(fragmentActivity);
        this.page = page;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (page == Const.ART) {
            if (position == 0)
                return new ArtUploadFirstFragment();
            else
                return new ArtUploadSecondFragment();
        } else {
            if (position == 0)
                return new WritingFirstFragment();
            else
                return new WritingSecondFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
