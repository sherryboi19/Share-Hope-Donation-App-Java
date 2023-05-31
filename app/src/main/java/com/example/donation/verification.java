package com.example.donation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class verification extends AppCompatActivity {
TextInputEditText address,city,state,zipCode,cnic,apartment;
Button done;
TextView step2text,addresstext,topic,description,message;
ConstraintLayout step2,step2lay,step3lay;
DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Bundle extras=getIntent().getExtras();
        state=findViewById(R.id.State);
        step2=findViewById(R.id.step2);
        city=findViewById(R.id.City);
        zipCode=findViewById(R.id.zip);
        cnic=findViewById(R.id.cnic);
        step2text=findViewById(R.id.step2text);
        address=findViewById(R.id.streetText);
        apartment=findViewById(R.id.apartment);
        message=findViewById(R.id.message);
        done=findViewById(R.id.donebtn);
        addresstext=findViewById(R.id.addresstext);
        step2lay=findViewById(R.id.step2lay);
        step3lay=findViewById(R.id.step3lay);
        description=findViewById(R.id.description);
        topic=findViewById(R.id.Topic);

        String type=extras.getString("Type");
        String contact=extras.getString("Contact");
        transfer(type,contact);

   /*     final String[] St = new String[1];//=databaseReference.child("Users").child(type).child(contact).child("State").getKey();
        databaseReference.child("Users").child(type).child(contact).child("Status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    St[0] ="state";
                }
                else{
                    St[0] ="";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if(Objects.equals(St[0], "")) {

        }
        else{
            transfer(type,contact);
        }
   */ }
    void verify(String Type,String Contact){
        ProgressDialog progressDialog = new ProgressDialog(this);
        String statetxt,addresstxt,citytxt,zipCodetxt,cnictxt,apartmenttxt;
        statetxt=state.getText().toString();
        addresstxt=address.getText().toString();
        citytxt=city.getText().toString();
        zipCodetxt=zipCode.getText().toString();
        cnictxt=cnic.getText().toString();

        apartmenttxt=apartment.getText().toString();
        if(statetxt.isEmpty())
        {
            state.setError("Enter state");
            state.requestFocus();
        }
        else if(citytxt.isEmpty()){
            city.setError("City is Required");
            city.requestFocus();
        }
        else if(apartmenttxt.isEmpty()){
            apartment.setError("Apartment is required.");
            apartment.requestFocus();
        }
        else if(zipCodetxt.isEmpty()){
            zipCode.setError("Zip code is required");
            zipCode.requestFocus();
        }
        else if(addresstxt.isEmpty()){
            address.setError("Address Required");
            address.requestFocus();
        }
        else if(cnictxt.isEmpty()){
            cnic.setError("Cnic is required");
            cnic.requestFocus();
        }
        else
        {
            progressDialog.setMessage("Please wait while sending request.");
            progressDialog.setTitle("Verification");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            databaseReference.child("Users").child(Type).child(Contact).child("State").setValue(statetxt);
            databaseReference.child("Users").child(Type).child(Contact).child("Status").setValue("Pending");
            databaseReference.child("Users").child(Type).child(Contact).child("City").setValue(citytxt);
            databaseReference.child("Users").child(Type).child(Contact).child("Apartment").setValue(apartmenttxt);
            databaseReference.child("Users").child(Type).child(Contact).child("ZipCode").setValue(zipCodetxt);
            databaseReference.child("Users").child(Type).child(Contact).child("Address").setValue(addresstxt);
            databaseReference.child("Users").child(Type).child(Contact).child("Cnic").setValue(cnictxt);
            progressDialog.dismiss();
            Toast.makeText(verification.this,"Verification request sent to verify.",Toast.LENGTH_SHORT).show();
            transfer(Type,Contact);

        }

    }
    void transfer(String type,String contact){
        databaseReference.child("Users").child(type).child(contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usermodel um=snapshot.getValue(usermodel.class);
                String status=um.getStatus();
                if(Objects.equals(status, "Accept")){
                    if(type.equals("Needy")) {
                        Intent intent = new Intent(verification.this, NeedyRequests.class);
                        intent.putExtra("Type", type);
                        intent.putExtra("Contact", contact);
                        startActivity(intent);
                        finish();
                    }
                    else if(type.equals("Donor")){
                        Intent intent = new Intent(verification.this, DonerRequest.class);
                        intent.putExtra("Type", type);
                        intent.putExtra("Contact", contact);
                        startActivity(intent);
                        finish();

                    }
                }
                else if(Objects.equals(status, "Reject")){
                    step2text.setVisibility(View.GONE);
                    step2.setBackground(getDrawable(R.drawable.ic_baseline_check_circle_24));
                    topic.setText("Verification Status");
                    description.setText("Sorry! You have been rejected.");
                    message.setText("Sorry! You have been rejected.");
                    step2lay.setVisibility(View.GONE);
                    addresstext.setVisibility(View.GONE);
                    step3lay.setVisibility(View.VISIBLE);

                }
                else if(Objects.equals(status, "Pending")){
                    step2text.setVisibility(View.GONE);
                    step2.setBackground(getDrawable(R.drawable.ic_baseline_check_circle_24));
                    topic.setText("Verification Status");
                    description.setText("Status will be updated once \n" + "information has verified");
                    step2lay.setVisibility(View.GONE);
                    addresstext.setVisibility(View.GONE);
                    step3lay.setVisibility(View.VISIBLE);

                }
                else {
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            verify(type, contact);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}