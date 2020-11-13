package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button loginButton,signupButton;
    String TAG = "Covid";

    //Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser=null;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
                finish();
            }
        });

    }

    void loginUser(){
        final String emailId=email.getText().toString();
        String pass=password.getText().toString();

        if(!emailId.equals("")&&!pass.equals("")){
            mAuth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentUser=task.getResult().getUser();
                        Toast.makeText(getApplicationContext(),"Wait.... Logging you in",Toast.LENGTH_SHORT).show();
                        searchUser(currentUser.getUid());
                    }else{
                        Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please enter correct details",Toast.LENGTH_LONG).show();
        }
    }

    void searchUser(String userId){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegisterUser currentUser=snapshot.child(userId).getValue(RegisterUser.class);
                launchMainActivity(currentUser.getEmail(),currentUser.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void launchMainActivity(String email, String name){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("username",name);
        finish();
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null){
            Toast.makeText(getApplicationContext(),"Wait.... Logging you in",Toast.LENGTH_SHORT).show();
            searchUser(currentUser.getUid());
        }
    }
}