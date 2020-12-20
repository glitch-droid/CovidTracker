package com.example.myapplication.Fragments;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrawalsuneet.loaderspack.loaders.PulseLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapters.CountryListAdapter;
import com.example.myapplication.Details;
import com.example.myapplication.Models.CountryModel;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AffectedCountriesFragment extends Fragment implements CountryListAdapter.OnClicked{
    public static List<CountryModel> countryList = new ArrayList<>();
    List<CountryModel> countryListFiltered;
    RecyclerView recyclerView;
    EditText nameTextView;
    CountryListAdapter myAdapter;
    public AffectedCountriesFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.afffected_countries_layout,container,false);
        nameTextView = v.findViewById(R.id.countryEditText);
        recyclerView = v.findViewById(R.id.countriesListRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchData();

        return v;
    }

    private void fetchData(){
        String url = "https://disease.sh/v3/covid-19/countries";
        StringRequest request = new StringRequest(Request.Method.GET,url,
                response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String country = jsonObject.getString("country");
                    String cases = jsonObject.getString("cases");
                    String todayCases = jsonObject.getString("todayCases");
                    String recovered = jsonObject.getString("recovered");
                    String active = jsonObject.getString("active");
                    String critical = jsonObject.getString("critical");
                    String deaths = jsonObject.getString("deaths");
                    String todayDeaths = jsonObject.getString("todayDeaths");
                    String tests = jsonObject.getString("tests");

                    JSONObject flagJsonObject = jsonObject.getJSONObject("countryInfo");
                    String flag = flagJsonObject.getString("flag");

                    countryList.add(new CountryModel(flag,country,cases,todayCases,recovered,active,critical,deaths,todayDeaths,tests));
                }
                myAdapter = new CountryListAdapter(countryList,getContext(),this);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            }
            catch (Exception e){
                e.printStackTrace();
            }
                },error -> {
            Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);

    }

    @Override
    public void onCountryClicked(int position) {
        Intent intent = new Intent(getContext(),Details.class);
        CountryModel cModel = countryList.get(position);
        intent.putExtra("Country",cModel);
        startActivity(intent);

    }
}
