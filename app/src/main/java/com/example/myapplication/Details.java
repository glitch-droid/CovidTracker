package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.CountryModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        CountryModel country = (CountryModel)getIntent().getSerializableExtra("Country");
        TextView name = findViewById(R.id.detailName);
        name.setText(country.getCountry());

        CircleImageView flag = findViewById(R.id.detailFlag);
        Glide.with(this)
                .asBitmap()
                .load(country.getFlag())
                .into(flag);
    }
}