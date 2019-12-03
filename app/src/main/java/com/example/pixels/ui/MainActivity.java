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
import com.example.pixels.ui.main.MainFragment;
import com.example.pixels.ui.main.ProfileFragment;
import com.example.pixels.ui.main.SearchFragment;
import com.example.pixels.vewmodels.SharedViewModel;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RootFragmentListener {

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
        getContentResolver();
        fragNavController =  new FragNavController(getSupportFragmentManager(), R.id.fragment_container);
        fragNavController.setRootFragmentListener(this);
        Log.e("SEVEN", fragNavController.getRootFragments() + "");
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

        sharedViewModel.loginInfo.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                }
            }
        });
    }

    @Override
    public int getNumberOfRootFragments() {
        return 3;
    }

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
}
