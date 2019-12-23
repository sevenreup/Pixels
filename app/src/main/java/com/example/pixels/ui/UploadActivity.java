package com.example.pixels.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pixels.R;
import com.example.pixels.Util.Const;
import com.example.pixels.adapter.CreatePostViewPagerAdapter;
import com.example.pixels.models.Post;
import com.example.pixels.models.WritingType;
import com.example.pixels.vewmodels.UploadViewModel;
import com.example.pixels.view.SegmentedButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends AppCompatActivity {
    @BindView(R.id.post_viewpager)
    ViewPager2 viewPager;
    UploadViewModel uploadViewModel;
    private CreatePostViewPagerAdapter viewPagerAdapter;
    private BottomSheetDialog choiceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upload);
        uploadViewModel = ViewModelProviders.of(this).get(UploadViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.test_image);
        toolbar.setNavigationOnClickListener(v -> Toast.makeText(UploadActivity.this, "close", Toast.LENGTH_SHORT).show());
        ButterKnife.bind(this);
        setUpDialog();
    }

    private void setUpDialog() {
        choiceDialog = new BottomSheetDialog(this);
        choiceDialog.setOnCancelListener(dialog -> UploadActivity.this.onBackPressed());
        View view = getLayoutInflater().inflate(R.layout.bottom_upload_choice, null);
        SegmentedButton postType = view.findViewById(R.id.post_type);
        postType.setOnStateListener(position -> {
            if (position == SegmentedButton.Toggle.LEFT) {
                setUpWriting();
                choiceDialog.dismiss();
            } else {
                setUpArt();
                choiceDialog.dismiss();
            }
        });
        choiceDialog.setCanceledOnTouchOutside(false);
        choiceDialog.setContentView(view);
        choiceDialog.show();
    }

    private void setUpArt() {
        uploadViewModel.setUpUploads(Const.ART);
        viewPagerAdapter = new CreatePostViewPagerAdapter(this, Const.ART);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setUpWriting() {
        uploadViewModel.setUpUploads(Const.POEM);
        viewPagerAdapter = new CreatePostViewPagerAdapter(this, Const.POEM);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_first_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cont) {

            if (checkValidity()) {
//                Gson gson = new GsonBuilder().create();
//                String sfy = gson.toJson(uploadViewModel.postInEdit.getValue());
//                Log.e("sfy", sfy);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("upload", uploadViewModel.postInEdit.getValue());
//                intent.putExtra("upload", sfy);
                startActivity(intent);
            }
        }
        return true;
    }

    private boolean checkValidity() {
        uploadViewModel.consolidate.setValue(true);
        Post post = uploadViewModel.postInEdit.getValue();
        Log.e("stuu", post.toString());
        if (post.getTitle() == null) {
            Log.e("activity", "finished observing");
            Toast.makeText(this, "no title", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (post.getType() == Const.POEM) {
                WritingType writingType = (WritingType) post.getContent();
                if (writingType.getImage().getContent() == null) {
                    Toast.makeText(this, "add image", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    if (writingType.getContent().isEmpty()) {
                        Toast.makeText(this, "please add something", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            } else {

            }
        }
        return true;
    }
}
