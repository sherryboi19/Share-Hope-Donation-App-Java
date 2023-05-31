package com.example.donation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class dashboard extends AppCompatActivity {
    ConstraintLayout list;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    DatePickerDialog datePickerDialog,datePickerDialog2;
    ConstraintLayout request,profile,user;
Button frombtn,tobtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initDatePicker();
        DatePicker();
        Bundle extras=getIntent().getExtras();
        String email =extras.getString("Email");
        frombtn=findViewById(R.id.from);
        tobtn=findViewById(R.id.from2);
        list=findViewById(R.id.listbtn);
        request=findViewById(R.id.requestsbtn);
        profile=findViewById(R.id.profilebtn);
        databaseReference.child("Users").child("Requests").child("Needy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String Contact=dataSnapshot.getRef().getKey();
                    databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                String parent=dataSnapshot.getRef().getKey();
                                addrequest req=dataSnapshot.getValue(addrequest.class);
                                if(req.getStatus()=="Verified"){

                                }
                                else
                                {

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(dashboard.this,ProfilePage.class);
                intent.putExtra("Type", "Admin");
                intent.putExtra("Contact", email);

                startActivity(intent);

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(dashboard.this,Requests.class);
                intent.putExtra("Email", email);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(dashboard.this,List.class);
                intent.putExtra("Email", email);
                startActivity(intent);
            }
        });
        frombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        String date=initDatePicker();
                datePickerDialog.show();

            }
        });
        tobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       //         String date=initDatePicker();
                datePickerDialog2.show();

            }
        });
    //    initDatePicker();

    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                 String date =month+"\\" + day+"\\"+year;
                frombtn.setText(date);
            }

        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int styles= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, styles, dateSetListener, year, month,day);

    }
    private void DatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
               String date =month+"\\" + day+"\\"+year;
               tobtn.setText(date);
            }

        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int styles= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog2 = new DatePickerDialog(this, styles, dateSetListener, year, month,day);

    }


}
