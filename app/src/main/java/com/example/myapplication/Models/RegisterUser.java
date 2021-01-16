package com.example.myapplication.Models;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
