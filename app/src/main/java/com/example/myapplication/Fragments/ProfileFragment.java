package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Registration.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import com.example.myapplication.Models.RegisterUser;

public class    ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Button button;
    String userUid;

    public ProfileFragment(){

    }

    public ProfileFragment(String userUid){
        this.userUid = userUid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Profile");
        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.profile_fragment_layout,
                container, false);
        button = v.findViewById(R.id.buttonOut);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mAuth.signOut();
                    startActivity(new Intent(getContext(), Login.class));
                    getActivity().finish();
            }
        });
        return v;
    }

}
