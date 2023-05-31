package com.example.donation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonatePage extends AppCompatActivity {
    TextView Contact;
    EditText Description,tid,amount;
    Button verify;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_page);
        Bundle extras=getIntent().getExtras();
        String parent=extras.getString("parent");
        String contact=extras.getString("Contact");
        String phone=extras.getString("phone");
        Contact=findViewById(R.id.paise);
        amount=findViewById(R.id.address);
        Description=findViewById(R.id.ddescription);
        tid=findViewById(R.id.tid);

        verify=findViewById(R.id.button);
        Contact.setText(phone);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("Users").child("Requests").child("Needy").child(phone).child(parent).child("Status").setValue("Verified");
                databaseReference.child("Users").child("Requests").child("Needy").child(phone).child(parent).child("Amount").setValue(amount.getText().toString());
                databaseReference.child("Users").child("Requests").child("Needy").child(phone).child(parent).child("Ddescription").setValue(Description.getText().toString());
                databaseReference.child("Users").child("Requests").child("Needy").child(phone).child(parent).child("Tid").setValue(tid.getText().toString());
                databaseReference.child("Users").child("Requests").child("Needy").child(phone).child(parent).child("Donor").setValue(contact);
                finish();
            }
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}