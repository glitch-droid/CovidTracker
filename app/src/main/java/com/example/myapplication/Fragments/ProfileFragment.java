package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Login;
import com.example.myapplication.R;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import com.example.myapplication.Models.RegisterUser;

public class    ProfileFragment extends Fragment {

    private static final String TAG = "I" ;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private Context mContext;
    private MaterialTextView profName;
    private MaterialTextView profEmail;
    private RegisterUser user;
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
        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.profile_fragment_layout,
                container, false);
        button = v.findViewById(R.id.buttonOut);
        profEmail = v.findViewById(R.id.prof_email);
        profName = v.findViewById(R.id.prof_name);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(mContext, Login.class));
                getActivity().finish();
            }
        });
        if(mAuth.getCurrentUser() != null){
            userUid = mAuth.getCurrentUser().getUid();
            System.out.print(userUid);
        }

        setDetails();
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setDetails(){
        databaseReference.child("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(RegisterUser.class);
                if(user==null){
                    Toast.makeText(mContext,"No User data found",Toast.LENGTH_SHORT).show();
                }else{
                    System.out.print(user.getName() + "____" + user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG,"Failed to read user",error.toException());
            }
        });
    }

}
