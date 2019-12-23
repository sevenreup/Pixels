package com.example.pixels.ui.frags;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pixels.R;
import com.example.pixels.models.Post;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostViewFragment extends Fragment {
    private Post post;

    public PostViewFragment() {
    }

    public PostViewFragment(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_view, container, false);
    }

}
