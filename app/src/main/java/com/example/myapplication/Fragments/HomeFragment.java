package com.example.myapplication.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class HomeFragment extends Fragment {

    TextView tvCases, tvRecovered, tvActive, tvDeaths, tvCritical, tvTodayCases, tvTodayDeaths, tvCountries;
    PieChart pieChart;

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Home");
        View v = inflater.inflate(R.layout.home_fragment_layout,container,false);

        tvCases = v.findViewById(R.id.casesNumber);
        tvRecovered = v.findViewById(R.id.recoveredNumber);
        tvActive = v.findViewById(R.id.activeNumber);
        tvDeaths = v.findViewById(R.id.totalDeathsNumber);
        tvTodayDeaths = v.findViewById(R.id.todayDeathsNumber);
        tvTodayCases = v.findViewById(R.id.todayCasesNumber);
        tvCountries = v.findViewById(R.id.countriesNumber);
        tvCritical = v.findViewById(R.id.criticalNumber);

        pieChart = v.findViewById(R.id.pieChart);

        fetchData();
        return v;
    }

    void fetchData(){
        String url = "https://disease.sh/v3/covid-19/all";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                            JSONObject jsonObject = new JSONObject(response);
                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvDeaths.setText(jsonObject.getString("deaths"));
                            tvCritical.setText(jsonObject.getString("critical"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvCountries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#ffce08")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#8BC34A")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#0873ff")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvDeaths.getText().toString()), Color.parseColor("#fc0303")));
                            pieChart.startAnimation();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(request);
    }

}
