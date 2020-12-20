package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.CountryModel;
import com.example.myapplication.R;

import java.util.List;

public class CountryListAdapter extends  RecyclerView.Adapter<CountryListAdapter.ViewHolder>{

    private  List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListFiltered;
    private  Context context;
    private RecyclerView.OnItemTouchListener mListener;
    private OnClicked mOnClicked;

    public CountryListAdapter(List<CountryModel> countryModelList, Context context, OnClicked onClicked) {
        this.countryModelList = countryModelList;
        this.context = context;
        this.mOnClicked = onClicked;
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

}
