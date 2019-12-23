package com.example.pixels.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.pixels.R;

public class SegmentedButton extends FrameLayout {
    private TextView left, right;
    private RelativeLayout lCont, rCOnt;
    private RippleDrawable lDrawable;
    private Toggle position = Toggle.LEFT;
    private ButtonStateListener listener = null;

    public SegmentedButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComp();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.segmentedButton, 0, 0);
        String leftText = a.getString(R.styleable.segmentedButton_left_text);
        String rightText = a.getString(R.styleable.segmentedButton_right_text);
        boolean setInit = a.getBoolean(R.styleable.segmentedButton_select_left, false);
        left.setText(leftText);
        right.setText(rightText);
        setUptoggleButton();
        if (setInit)
            toggleButton();
    }

    private void initComp() {
        inflate(getContext(), R.layout.custom_segmented_button, this);
        left = findViewById(R.id.left_cust);
        right = findViewById(R.id.right_cust);
        lCont = findViewById(R.id.left_cont);
        rCOnt = findViewById(R.id.right_cont);
    }

    private void setUptoggleButton() {
        lCont.setOnClickListener(v->{
            if (position != Toggle.LEFT)
                position = Toggle.LEFT;
            toggleButton();
        });
        rCOnt.setOnClickListener(v->{
            if (position != Toggle.RIGHT)
                position = Toggle.RIGHT;
            toggleButton();
        });
    }

    public void init(Toggle position) {
        this.position = position;
        toggleButton();
    }

    public void setOnStateListener(ButtonStateListener listener) {
        this.listener = listener;
    }

    private void toggleButton() {
        if (position == Toggle.LEFT) {
            lCont.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            rCOnt.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            rCOnt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            lCont.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        if (listener != null)
            listener.onButtonToggled(position);
    }

    public enum Toggle{
        LEFT,
        RIGHT
    }

    public interface ButtonStateListener {
        void onButtonToggled(Toggle position);
    }
}
