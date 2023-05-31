package com.example.donation;

public class User {
    String Name;
    String Phone;
    String Email;
    String Password;
    String AccountType;
    User (String Name,String Phone,String Email,String Password,String AccountType){
        this.Name=Name;
        this.Phone=Phone;
        this.Email=Email;
        this.Password=Password;
        this.AccountType=AccountType;
    }
    User (String Name,String Phone,String Email,String Password){
        this.Name=Name;
        this.Phone=Phone;
        this.Email=Email;
        this.Password=Password;
        this.AccountType=AccountType;
    }
}
