package com.example.pixels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.pixels.R;

import java.util.ArrayList;
import java.util.List;

public class AuthViewPagerAdapter extends PagerAdapter {
    private List<String> titles  = new ArrayList<>();
    private List<String> words  = new ArrayList<>();

    private Context context;

    public AuthViewPagerAdapter(Context context) {
        this.context = context;

        titles.add("First");
        words.add("Second");

        titles.add("Second");
        words.add("Second");
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.auth_viewpager_layout, container, false);

        TextView title = viewGroup.findViewById(R.id.main_tag);
        TextView description = viewGroup.findViewById(R.id.tag_desc);

        title.setText(this.titles.get(position));
        description.setText(this.words.get(position));

        container.addView(viewGroup);
        return viewGroup;
    }
}
