package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    TextView name,cnic,email,contact,state,city,zipcode,apartment,streetAddress;
    Button Editbtn;
    ConstraintLayout backbtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    private String Type,Contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Bundle extras=getIntent().getExtras();
        Type=extras.getString("Type");
        Contact=extras.getString("Contact");
        backbtn=findViewById(R.id.backbtn);
        name=findViewById(R.id.pname);
        cnic=findViewById(R.id.pcnic);
        email=findViewById(R.id.pemail);
        contact=findViewById(R.id.pcontact);
        state=findViewById(R.id.pstate);
        city=findViewById(R.id.pcity);
        zipcode=findViewById(R.id.pzipcode);
        apartment=findViewById(R.id.pappartment);
        streetAddress=findViewById(R.id.pstreetadress);
        Editbtn=findViewById(R.id.pedit);
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
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,EditProfilePage.class);
                intent.putExtra("Type",Type);
                intent.putExtra("Contact",Contact);

                startActivity(intent);
            }
        });

    }
}