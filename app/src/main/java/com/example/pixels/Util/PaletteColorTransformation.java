package com.example.pixels.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.example.pixels.R;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import static android.os.Build.ID;

public class PaletteColorTransformation extends BitmapTransformation {
     private static String ID = "com.example.pixels.Util";
    private static final byte[] ID_BYTES = ID.getBytes(Charset.forName("UTF-8"));
    public PaletteColorTransformation() {
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);
        Palette p =  Palette.from(bitmap).generate();
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(p.getVibrantColor(Color.GREEN), PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PaletteColorTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
