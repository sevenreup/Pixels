package com.example.pixels.Util;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpannableConverter {

    public static String spannable2Json (SpannableStringBuilder string) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("text", string.toString());
        JSONArray jsonArray = new JSONArray();
        StyleSpan[] styleSpans = string.getSpans(0, string.length(), StyleSpan.class);
        for (StyleSpan styleSpan : styleSpans) {
            int style = styleSpan.getStyle();
            int start = string.getSpanStart(styleSpan);
            int end = string.getSpanEnd(styleSpan);
            JSONObject object = new JSONObject();
            object.put("style", style);
            object.put("start", start);
            object.put("end", end);
            jsonArray.put(object);
        }
        json.put("spans", jsonArray);
        return json.toString();
    }
}
