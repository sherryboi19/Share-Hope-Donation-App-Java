package com.example.donation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

public class Requests extends AppCompatActivity {
    HistoryAdaptor ua;
    nRequestsAdator nra;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    ConstraintLayout donorselected,needyselected,list,profile,dashboard;
    TextView donor,needy;
    RecyclerView recyclerView;
    ArrayList<addrequest> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        recyclerView=findViewById(R.id.requestsrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        nra=new nRequestsAdator(this,arrayList);
        ua=new HistoryAdaptor(this,arrayList);
        recyclerView.setAdapter(ua);
        Bundle extras=getIntent().getExtras();
        String email =extras.getString("Email");
        String type,Type;
        Type="Admin";
        type="Needy";
        donor=findViewById(R.id.donortext);
        needy=findViewById(R.id.needytext);
        donorselected=findViewById(R.id.donorselected);
        needyselected=findViewById(R.id.needyselected);
        dashboard=findViewById(R.id.dashboardbtn);
        profile=findViewById(R.id.profilebtn);
        list=findViewById(R.id.listbtn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Requests.this,ProfilePage.class);
                intent.putExtra("Type", "Admin");
                intent.putExtra("Contact", email);

                startActivity(intent);
                onBackPressed();


            }
        });
     dashboard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             onBackPressed();
         }
     });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Requests.this,List.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                onBackPressed();
            }
        });


        needyselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ua.clear();
                nra.clear();
                recyclerView.setAdapter(ua);

                donor.setTextColor(Color.parseColor("#30A84A"));
                needy.setTextColor(Color.parseColor("#FFFFFFFF"));
                donorselected.setBackground(null);
                needyselected.setBackground(getDrawable(R.drawable.greenbtn));
                String type = "Needy";
                databaseReference.child("Users").child("Requests").child("Needy").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String Contact=dataSnapshot.getRef().getKey();
                            databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                        String parent = dataSnapshot.getRef().getKey();
                                        addrequest req = dataSnapshot.getValue(addrequest.class);
                                        req.setType("Admin");
                                        req.setContact(Contact);
                                        req.setParent(parent);
                                        arrayList.add(req);
                                    }

                                    ua.notifyDataSetChanged();
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

            }
        });
        donorselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ua.clear();
                nra.clear();
                recyclerView.setAdapter(nra);
                needy.setTextColor(Color.parseColor("#30A84A"));
                donor.setTextColor(Color.parseColor("#FFFFFFFF"));
                donorselected.setBackground(getDrawable(R.drawable.greenbtn));
                needyselected.setBackground(null);
                String type = "Donor";
                databaseReference.child("Users").child("Requests").child("Needy").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String Contact=dataSnapshot.getRef().getKey();
                            Log.d("msg","enter");
                            databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        Log.d("msg","rovole");
                                        String parent=dataSnapshot.getRef().getKey();
                                        addrequest req=dataSnapshot.getValue(addrequest.class);
                                        if (Objects.equals(req.getStatus(), "Verified")) {
                                            Log.d("msg","verified");
                                            req.setType("Admin");
                                            req.setContact(Contact);
                                            req.setParent(parent);
                                            Log.d("msg","run");
                                            arrayList.add(req);
                                            Log.d("msg","complete");

                                        }
                                    }
                                    nra.notifyDataSetChanged();
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

            }
        });
        ua.clear();
        databaseReference.child("Users").child("Requests").child("Needy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String Contact=dataSnapshot.getRef().getKey();
                    databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                String parent = dataSnapshot.getRef().getKey();
                                addrequest req = dataSnapshot.getValue(addrequest.class);
                                req.setType("Admin");
                                req.setContact(Contact);
                                req.setParent(parent);
                                arrayList.add(req);
                            }

                            ua.notifyDataSetChanged();
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

    }
}