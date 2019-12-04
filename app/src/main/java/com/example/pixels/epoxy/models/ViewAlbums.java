package com.example.pixels.epoxy.models;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.example.pixels.R;

import butterknife.BindView;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.model_view_albums)
public class ViewAlbums  extends EpoxyModelWithHolder<ViewAlbums.VAHolder> {
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener listener = null;

    @Override
    public void bind(@NonNull VAHolder holder) {
        super.bind(holder);
        holder.layout.setOnClickListener(listener);
    }

    @Override
    protected VAHolder createNewHolder() {
        return new VAHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.model_view_albums;
    }

    public static class VAHolder extends BaseEpoxyHolder {
        @BindView(R.id.view_more)
        LinearLayout layout;
    }
}
