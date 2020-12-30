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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import com.example.myapplication.Models.RegisterUser;

public class Signup extends AppCompatActivity {

    EditText name, email, password, confirmPassword;
    Button registerButton;
    String TAG="Covid";
    TextView logintv;

    //Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail2);
        password  = findViewById(R.id.editTextPassword2);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
        logintv = findViewById(R.id.loginTV);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        registerButton  =findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    //Adding UserData to firebase database
    void makeUser(RegisterUser user, String userId){
        databaseReference.child(userId).setValue(user);
    }

    //Creating User in Firebase
    void registerUser(){
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();

        if(!Email.equals("")&&
                !Password.equals("")&&
                !ConfirmPassword.equals("")&&
                !Name.equals("")){
            if(Email.contains("@")){
                if(Password.equals(ConfirmPassword) && Password.length()>8){
                    final RegisterUser userObj=new RegisterUser(Name,Email);
                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                final FirebaseUser user=task.getResult().getUser();
                                Log.i("TEST", user.getUid());
                                makeUser(userObj, user.getUid());
                                Intent intent=new Intent(getApplicationContext() ,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"An unexpected error occured! Please try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    password.setError("Please enter a valid password of length greater than 8 and and confirm your password");
                }
            }else{
                email.setError("Please enter a valid Email");
            }
        }else{
            Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
        }
    }
}