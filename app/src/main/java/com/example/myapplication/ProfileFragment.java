package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class    ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Button button;
    TextView userName;
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
        userName = v.findViewById(R.id.textViewUserName);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        setName(userUid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mAuth.signOut();
            }
        });
        return v;
    }

    void setName(String userId){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegisterUser rUser = snapshot.child(userId).getValue(RegisterUser.class);
                assert rUser != null;
                userName.setText(rUser.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
