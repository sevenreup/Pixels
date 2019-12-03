package com.example.pixels.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pixels.R;
import com.example.pixels.Util.AuthListener;
import com.example.pixels.firebase.FirebaseSource;
import com.example.pixels.ui.auth.AuthFragment;
import com.example.pixels.vewmodels.AuthViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements AuthListener, FirebaseSource.AuthListener {

    @BindView(R.id.bottom_sheet)
    View btSheet;

    private FragmentManager fragmentManager;

    private static final int REQUEST_CODE = 5730;
    private List<AuthUI.IdpConfig> providers;

    private AuthViewModel authViewModel;
    private BottomSheetBehavior bottomSheetBehavior;
    private RegisterBT register = new RegisterBT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        ButterKnife.bind(register, btSheet);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        if (authViewModel.checkUser()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.auth_container, new AuthFragment()).commit();
        setupRegister();
    }
    private void setupRegister() {
        register.signIn.setOnClickListener(v->{
            String email = register.email.getText().toString();
            String name = register.name.getText().toString();
            String password = register.password.getText().toString();
            authViewModel.register(email, password, name, this);
        });
        register.signToggle.setOnClickListener(v->{
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            else
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        register.google.setOnClickListener(v->{});
    }
    @Override
    public void googleClick() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .setIsSmartLockEnabled(true)
        .build(), REQUEST_CODE);
    }

    @Override
    public void fingerprintCLick() {

    }

    @Override
    public void onSuccessAuth(FirebaseUser user) {
        if (user != null)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onSuccess(int ID, String info) {

    }

    @Override
    public void onFailure(int ID, String info) {

    }

    static class RegisterBT{
        @BindView(R.id.email)
        EditText email;
        @BindView(R.id.password)
        EditText password;
        @BindView(R.id.name)
        EditText name;
        @BindView(R.id.sign_in)
        Button signIn;
        @BindView(R.id.fingerprint)
        ImageView fingerPrint;
        @BindView(R.id.google)
        ImageView google;
        @BindView(R.id.sig_ico)
        Button signToggle;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
