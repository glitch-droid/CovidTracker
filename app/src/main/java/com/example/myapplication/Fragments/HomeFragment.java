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
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapters.CountryListAdapter;
import com.example.myapplication.Models.BarGraphCountryModel;
import com.example.myapplication.Models.CountryModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private TextView tvCases, tvRecovered, tvActive, tvDeaths, tvCritical, tvTodayCases, tvTodayDeaths, tvCountries;
    private PieChart pieChart;
    private BarChart barChart1, barChart2;
    private List<BarGraphCountryModel> topCases;
    private Context mContext;

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        barChart1 = v.findViewById(R.id.topCasesChart);
        barChart2 = v.findViewById(R.id.topDeathsChart);
        topCases = new ArrayList<>();

        fetchData1();
        fetchData2();
        return v;
    }



    void fetchData1(){
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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    private void fetchData2() {
        String url = "https://disease.sh/v3/covid-19/countries";
        StringRequest request = new StringRequest(Request.Method.GET,url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String country = jsonObject.getString("country");
                            Float cases = Float.parseFloat(jsonObject.getString("cases"));
                            Float deaths = Float.parseFloat(jsonObject.getString("deaths"));
                            BarGraphCountryModel model = new BarGraphCountryModel(cases,deaths,country);
                            topCases.add(model);
                        }
                      Collections.sort(topCases, new Comparator<BarGraphCountryModel>() {
                          @Override
                          public int compare(BarGraphCountryModel o1, BarGraphCountryModel o2) {
                              return o2.getCasesBar().compareTo(o1.getCasesBar());
                          }
                      });
                        for (int i=0; i<5;i++){
                            barChart1.addBar(new BarModel(topCases.get(i).getCountryNameBar(),topCases.get(i).getCasesBar(),0xFF03DAC5));
                        }
                        barChart1.startAnimation();

                        Collections.sort(topCases, new Comparator<BarGraphCountryModel>() {
                            @Override
                            public int compare(BarGraphCountryModel o1, BarGraphCountryModel o2) {
                                return o2.getDeathsBar().compareTo(o1.getDeathsBar());
                            }
                        });
                        for (int i=0; i<5;i++){
                            barChart2.addBar(new BarModel(topCases.get(i).getCountryNameBar(),topCases.get(i).getDeathsBar(),0xFF6200EE));
                        }
                        barChart2.startAnimation();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                },error -> {
            Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
