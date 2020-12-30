package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.Adapters.CountryListAdapter;
import com.example.myapplication.Models.CountryModel;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    TextView cases, recovered, critical, deaths, tests, active;
    private StackedBarChart stackedBarChart;
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
        stackedBarChart = findViewById(R.id.barGraph);

        setValues(country);
        makeBarGraph(country);
    }

    private void makeBarGraph(CountryModel country) {
        String url = "https://disease.sh/v3/covid-19/all";
        final float[] a = new float[1];
        final float[] b = new float[1];
        final float[] c = new float[1];
        final float[] d = new float[1];
        final float[] e = new float[1];
        final float[] f = new float[1];

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        a[0] = Float.parseFloat(jsonObject.getString("cases"));
                        b[0] = Float.parseFloat(jsonObject.getString("deaths"));
                        c[0] = Float.parseFloat(jsonObject.getString("recovered"));
                        d[0] = Float.parseFloat(country.getCases());
                        e[0] = Float.parseFloat(country.getDeaths());
                        f[0] = Float.parseFloat(country.getRecovered());

                        StackedBarModel s1 = new StackedBarModel("Cases");

                        s1.addBar(new BarModel(a[0], 0xFF63CBB0));
                        s1.addBar(new BarModel(d[0], 0xFF56B7F1));

                        StackedBarModel s2 = new StackedBarModel("Deaths");
                        s2.addBar(new BarModel(b[0], 0xFF63CBB0));
                        s2.addBar(new BarModel(e[0], 0xFF56B7F1));

                        StackedBarModel s3 = new StackedBarModel("Recovered");

                        s3.addBar(new BarModel(c[0], 0xFF63CBB0));
                        s3.addBar(new BarModel(f[0], 0xFF56B7F1));

                        stackedBarChart.addBar(s1);
                        stackedBarChart.addBar(s2);
                        stackedBarChart.addBar(s3);
                        stackedBarChart.startAnimation();

                    } catch (JSONException el) {
                        el.printStackTrace();
                    }

                }, error -> Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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