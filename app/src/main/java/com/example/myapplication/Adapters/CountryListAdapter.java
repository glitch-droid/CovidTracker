package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.CountryModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends  RecyclerView.Adapter<CountryListAdapter.ViewHolder> implements Filterable {

    private  List<CountryModel> countryModelList;
    private  List<CountryModel> countryModelListFull;
    private  Context context;
    private OnClicked mOnClicked;

    public CountryListAdapter(List<CountryModel> countryModelList, Context context, OnClicked onClicked) {
        this.countryModelList = countryModelList;
        this.context = context;
        this.mOnClicked = onClicked;
        countryModelListFull = new ArrayList<>(countryModelList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item_layout,parent,false);
        return new ViewHolder(view, mOnClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(countryModelList.get(position).getFlag())
                .into(holder.countryFlag);

        holder.countryName.setText(countryModelList.get(position).getCountry());

    }


    @Override
    public int getItemCount() {
        return countryModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView countryFlag;
        TextView countryName;
        OnClicked onClicked;
        public ViewHolder(@NonNull View itemView, OnClicked onClicked) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.flag_imageView);
            countryName = itemView.findViewById(R.id.countryNameTextView);
            this.onClicked = onClicked;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClicked.onCountryClicked(getAdapterPosition());
        }
    }

    public interface OnClicked{
        void onCountryClicked(int position);
    }

    @Override
    public Filter getFilter() {
        return countryListFilter;
    }

    private Filter countryListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(countryModelListFull);
            }else{
                String pattern = constraint.toString().toLowerCase().trim();
                for(CountryModel itrModel : countryModelListFull){
                    if(itrModel.getCountry().toLowerCase().startsWith(pattern)){
                        filteredList.add(itrModel);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryModelList.clear();
            countryModelList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
