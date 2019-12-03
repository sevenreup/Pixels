package com.example.pixels.models.upload;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.example.pixels.R;
import com.example.pixels.models.BaseEpoxyHolder;

import butterknife.BindView;

@EpoxyModelClass(layout = R.layout.model_upload_text)
public class TextModel extends EpoxyModelWithHolder<TextModel.TextModelHolder> {

    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash) View.OnFocusChangeListener listener;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash) View.OnLongClickListener longClickListener;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash) View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull TextModelHolder holder) {
        super.bind(holder);
        holder.editText.setOnFocusChangeListener(listener);
        holder.controlButton.setOnLongClickListener(longClickListener);
        holder.controlButton.setOnClickListener(clickListener);
    }

    @Override
    protected TextModelHolder createNewHolder() {
        return new TextModelHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_upload_text;
    }

    public static class TextModelHolder extends BaseEpoxyHolder {
        @BindView(R.id.add_text)
        EditText editText;
        @BindView(R.id.widget_control)
        Button controlButton;
    }
}
