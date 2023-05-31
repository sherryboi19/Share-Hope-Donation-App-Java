package com.example.donation;

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
import java.util.Objects;

public class NotificationPage extends AppCompatActivity {
    private ConstraintLayout addbtn,dashbaord,Requestbtn,profilebtn,backbtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    ArrayList<addrequest> arrayList;
    HistoryAdaptor ha;
    TextView message;
    RecyclerView recyclerView;
    String Type,Contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        backbtn=findViewById(R.id.backbtn);
        Bundle extras=getIntent().getExtras();
        recyclerView=findViewById(R.id.notificationRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Type=extras.getString("Type");
        Contact=extras.getString("Contact");
        arrayList=new ArrayList<>();
        ha=new HistoryAdaptor(this,arrayList);
        recyclerView.setAdapter(ha);
        profilebtn=findViewById(R.id.profilebtn);
        Requestbtn=findViewById(R.id.requestsbtn);
        message=findViewById(R.id.text1);
        databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String parent=dataSnapshot.getRef().getKey();
                    addrequest req=dataSnapshot.getValue(addrequest.class);
                    if(Objects.equals(Objects.requireNonNull(req).getStatus(), "Verified"))
                    {req.setContact(Contact);
                    req.setParent(parent);
                    req.setType("Needy");
                    arrayList.add(req);
                    message.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                ha.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}