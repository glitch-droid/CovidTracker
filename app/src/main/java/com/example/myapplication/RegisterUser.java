package com.example.myapplication;

public class RegisterUser {
    String name;
    String email;

    public RegisterUser() {

    }

    public RegisterUser(String name, String email){
        this.name = name;
        this.email = email;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
}
