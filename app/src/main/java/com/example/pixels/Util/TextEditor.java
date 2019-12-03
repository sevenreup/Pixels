package com.example.pixels.Util;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.AlignmentSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

public class TextEditor {
    private EditText editText = null;
    private int position = -1;

    public TextEditor() {
    }

    public boolean getEditText() {
        return editText != null;
    }

    public void setEditText(EditText editText, int position) {
        this.editText = editText;
        this.position = position;
    }

    public void changeStyle(Const.EDITOR STYLE) {
        if (editText.getSelectionStart() >= 0) {
            switch (STYLE) {
                case ITALIC:
                    italic();
                    break;
                case BOLD:
                    bold();
                    break;
                case UNDERLINE:
                    underline();
                    break;
                case STRIKE_THROUGH:
                    strikeThrough();
                    break;
            }
        }
    }
    public void setEditable(boolean isEditable) {
        if (isEditable) {
            editText.setEnabled(false);
            editText.setInputType(InputType.TYPE_NULL);
            editText.setFocusable(false);
        }
    }
    public void changeAlign(Const.EDITOR DIR) {
        if (DIR == Const.EDITOR.ALGN_CENTER) {
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else if (DIR == Const.EDITOR.ALGN_LEFT) {
            editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else if (DIR == Const.EDITOR.ALGN_RIGHT) {
            editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
    }

    private void italic() {
        editText.getText().setSpan(new StyleSpan(Typeface.ITALIC),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void bold() {
        editText.getText().setSpan(new StyleSpan(Typeface.BOLD),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void underline() {
        editText.getText().setSpan(new UnderlineSpan(),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void strikeThrough() {
        editText.getText().setSpan(new StrikethroughSpan(),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public String getText() throws JSONException {
        return SpannableConverter.spannable2Json((SpannableStringBuilder) editText.getText());
    }

    public void clearFocus() {
        editText.clearFocus();
    }
}
