package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfilePage extends AppCompatActivity {
    EditText name,cnic,email,contact,state,city,zipcode,apartment,streetAddress;
    Button Savebtn,cancelbtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    private String Type,Contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        Bundle extras=getIntent().getExtras();
        Type=extras.getString("Type");
        Contact=extras.getString("Contact");

        name=findViewById(R.id.pnamee);
        cnic=findViewById(R.id.pcnice);
        email=findViewById(R.id.pemaile);
        contact=findViewById(R.id.pcontacte);
        state=findViewById(R.id.pstatee);
        city=findViewById(R.id.pcitye);
        zipcode=findViewById(R.id.pzipcodee);
        apartment=findViewById(R.id.pappartmente);
        streetAddress=findViewById(R.id.pstreetadresse);
        cancelbtn=findViewById(R.id.ncancel);
        Savebtn=findViewById(R.id.nSave);
        databaseReference.child("Users").child(Type).child(Contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usermodel um=snapshot.getValue(usermodel.class);
                name.setText(um.getName());
                cnic.setText(um.getCnic());
                email.setText(um.getEmail());
                contact.setText(Contact);
                state.setText(um.getState());
                city.setText(um.getCity());
                zipcode.setText(um.getZipCode());
                apartment.setText(um.getApartment());
                streetAddress.setText(um.getAddress());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){

                    name.setError("Enter Full Name");
                    name.requestFocus();
                }
                else if(contact.getText().length() != 13){
                    contact.setError("Incorrect phone number");
                    contact.requestFocus();
                }
                else if(contact.getText().toString().isEmpty()){
                    contact.setError("Enter phone number");
                    contact.requestFocus();
                }
                else if(!email.getText().toString().matches(pattern)){
                    email.setError("Enter correct email");
                    email.requestFocus();
                }
                else if(state.getText().toString().isEmpty()){
                    state.setError("Please Enter State");
                    state.requestFocus();
                }
                else if(city.getText().toString().equals("")){
                    city.setError("Enter City");
                    city.requestFocus();
                }
                else if(streetAddress.getText().toString().equals("")){
                    streetAddress.setError("Enter address");
                    streetAddress.requestFocus();
                }
                else if(apartment.getText().toString().equals("")){
                    apartment.setError("Enter your apartment");
                    apartment.requestFocus();
                }
                else if(zipcode.getText().toString().equals("")){
                    zipcode.setError("Enter ZipCode");
                    zipcode.requestFocus();
                }
                else {
                     String em, emailtxt;
                     em="";
                    emailtxt = email.getText().toString();
                    String split[] = emailtxt.split("@", 10);
                    int j = 0;
                    for (String s : split) {
                        if (j == 0) {
                            j = 1;
                            em = s;
                        }
                    }

                    databaseReference.child("Users").child(Type).child(Contact).child("Name").setValue(name.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("Cnic").setValue(cnic.getText().toString());
                    databaseReference.child("Users").child("Auth").child(em).child("Contact").setValue(contact.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("Email").setValue(emailtxt);
                    databaseReference.child("Users").child(Type).child(Contact).child("State").setValue(state.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("City").setValue(city.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("Apartment").setValue(apartment.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("ZipCode").setValue(zipcode.getText().toString());
                    databaseReference.child("Users").child(Type).child(Contact).child("Address").setValue(streetAddress.getText().toString());
                    Intent intent=new Intent(EditProfilePage.this,ProfilePage.class);
                    intent.putExtra("Type",Type);
                    intent.putExtra("Contact",Contact);

                    startActivity(intent);
                    finish();
                }
            }
        });
        ConstraintLayout backbtn=findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}