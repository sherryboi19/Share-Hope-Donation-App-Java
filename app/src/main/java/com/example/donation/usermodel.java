package com.example.donation;

public class usermodel {
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Name;
    private String City;
    private String Cnic;
    private String Email;
    private String Password;
    private String Apartment;
    private String Address;
    private String State;
    private String Date;
    private String DonorVerfication;
    private String NeedyVerfication;


    public String getDonorVerfication() {
        return DonorVerfication;
    }

    public void setDonorVerfication(String donorVerfication) {
        DonorVerfication = donorVerfication;
    }

    public String getNeedyVerfication() {
        return NeedyVerfication;
    }

    public void setNeedyVerfication(String needyVerfication) {
        NeedyVerfication = needyVerfication;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private String ZipCode;
    private String parent;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getApartment() {
        return Apartment;
    }

    public void setApartment(String apartment) {
        Apartment = apartment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    void usermodel(){

}
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getCnic() {
        return Cnic;
    }

    public void setCnic(String cnic) {
        this.Cnic = cnic;
    }
}
