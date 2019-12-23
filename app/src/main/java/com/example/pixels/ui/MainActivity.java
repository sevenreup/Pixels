package com.example.pixels.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pixels.R;
import com.example.pixels.Util.PostDeserializer;
import com.example.pixels.Util.RuntimeTypeAdapterFactory;
import com.example.pixels.models.NavDir;
import com.example.pixels.models.Post;
import com.example.pixels.models.PostContent;
import com.example.pixels.ui.frags.PostViewFragment;
import com.example.pixels.ui.main.MainFragment;
import com.example.pixels.ui.main.ProfileFragment;
import com.example.pixels.ui.main.SearchFragment;
import com.example.pixels.vewmodels.SharedViewModel;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RootFragmentListener, FragNavController.TransactionListener {

    @BindView(R.id.navigationview)
    BubbleNavigationLinearView navigationView;

    private FragNavController fragNavController;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        if (!sharedViewModel.checkUser()) {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        }
        setUpload();
        setupNav(savedInstanceState);
        sharedViewModel.loginInfo.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                }
            }
        });
    }

    private void setupNav(Bundle savedInstanceState) {
        fragNavController =  new FragNavController(getSupportFragmentManager(), R.id.fragment_container);
        fragNavController.setRootFragmentListener(this);


        UniqueTabHistoryStrategy history = new UniqueTabHistoryStrategy(new FragNavSwitchController() {
            @Override
            public void switchTab(int i, @Nullable FragNavTransactionOptions fragNavTransactionOptions) {
                navigationView.setCurrentActiveItem(i);
                Toast.makeText(MainActivity.this, i + " ", Toast.LENGTH_LONG).show();
            }
        });
        fragNavController.setNavigationStrategy(history);
        fragNavController.setFragmentHideStrategy(FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH);
        navigationView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                Log.e("SEVEN", position + "");
                switch (view.getId()) {
                    case R.id.home: case R.id.brands:
                        fragNavController.switchTab(position);
                        break;
                    case R.id.notifications:
                        fragNavController.switchTab(position-1);
                        break;
                    case R.id.upload:
                        startActivity(new Intent(MainActivity.this, UploadActivity.class));
                    default:
                        Toast.makeText(MainActivity.this, "failure :" + position, Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });
        fragNavController.initialize(0, savedInstanceState);
        sharedViewModel.navigation.observe(this, new Observer<NavDir>() {
            @Override
            public void onChanged(NavDir navDir) {
                switch (navDir.getDestination()) {
                    case POST:
                        fragNavController.pushFragment(new PostViewFragment(((Post)navDir.getData())));
                        break;
                }
            }
        });
    }
    private void setUpload() {
        String upload = getIntent().getStringExtra("upload");

        if (upload != null) {
            Log.e("TAG", upload);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Post.class, new PostDeserializer());
            Gson gson = builder.create();
            Post uploadPost = gson.fromJson(upload, Post.class);
            Log.e("nii", uploadPost.toString());
            sharedViewModel.uploadPost.setValue(uploadPost);
        }
    }

    @Override
    public int getNumberOfRootFragments() {
        return 3;
    }

    @NotNull
    @Override
    public Fragment getRootFragment(int i) {
        Log.e("SEVEN", i + "");
        switch (i) {
            case 0:
                return MainFragment.newInstance(0);
            case 1:
                return SearchFragment.newInstance(0);
            case 2:
                return ProfileFragment.newInstance(0);
                default:
                    throw new IllegalStateException("Not supposed to get here");
        }
    }


    @Override
    public void onBackPressed() {
        if (!fragNavController.popFragment())
            super.onBackPressed();
    }

    @Override
    public void onFragmentTransaction(@Nullable Fragment fragment, @NotNull FragNavController.TransactionType transactionType) {
    }

    @Override
    public void onTabTransaction(@Nullable Fragment fragment, int i) {

    }
}
