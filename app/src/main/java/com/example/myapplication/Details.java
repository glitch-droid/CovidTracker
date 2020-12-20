package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.CountryModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    TextView cases, recovered, critical, deaths, tests, active;
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

        cases = findViewById(R.id.cases);
        critical = findViewById(R.id.critical);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        deaths = findViewById(R.id.deaths);
        tests = findViewById(R.id.tests);

        setValues(country);
    }
    private void setValues(CountryModel mCountry){
        cases.setText(mCountry.getCases());
        critical.setText(mCountry.getCritical());
        recovered.setText(mCountry.getRecovered());
        tests.setText(mCountry.getTests());
        active.setText(mCountry.getActive());
        deaths.setText(mCountry.getDeaths());
    }
}