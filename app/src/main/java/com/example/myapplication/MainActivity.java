package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String userName;
    FirebaseAuth mAuth;
    BottomNavigationView navigationView;
    Fragment container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String str = intent.getStringExtra("username");

        mAuth = FirebaseAuth.getInstance();
        userName = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setElevation(10);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home_icon:
                        container = new HomeFragment();
                        break;
                    case R.id.profile_icon:
                        container = new ProfileFragment(userName);
                        break;
                    case R.id.world_icon:
                        container  = new AffectedCountriesFragment();
                        break;
                    default:
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,container).commit();
                return true;
            }
        });

    }
}