package com.example.donation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonerRequest extends AppCompatActivity {
    private ConstraintLayout addbtn,calculatorbtn,historybtn,profilebtn,backbtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    ArrayList<addrequest> arrayList;
    drequests ra;
    RecyclerView recyclerView;
    String Type,contact;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_request);
        backbtn=findViewById(R.id.backbtn);
        Bundle extras=getIntent().getExtras();
        recyclerView=findViewById(R.id.needyRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Type=extras.getString("Type");
        contact=extras.getString("Contact");
        TextView topic=findViewById(R.id.Topic);
        arrayList=new ArrayList<>();
        ra=new drequests(this,arrayList);
        recyclerView.setAdapter(ra);
        calculatorbtn=findViewById(R.id.Calculatorbtn);
        profilebtn=findViewById(R.id.profilebtn);
        historybtn=findViewById(R.id.historybtn);
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
                                 req.setDonor(contact);
                                req.setContact(Contact);
                                req.setParent(parent);
                                req.setType(Type);
                                arrayList.add(req);
                            }
                            ra.notifyDataSetChanged();
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
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Intent intent = new Intent(DonerRequest.this,DonationHistory.class);
                intent.putExtra("Type", Type);
                intent.putExtra("Contact", contact);
                startActivity(intent);

            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonerRequest.this,ProfilePage.class);
                intent.putExtra("Type", Type);
                intent.putExtra("Contact", contact);
                startActivity(intent);

            }
        });

    }


}