package com.example.donation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;

public class NeedyRequests extends AppCompatActivity {
   private ConstraintLayout addbtn,dashbaord,Notification,profilebtn,backbtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    ArrayList<addrequest> arrayList;
    RequestsAdaptor ra;
    TextView message;
    RecyclerView recyclerView;
    String Type,Contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy_requests);
        addbtn=findViewById(R.id.addbtn);
        backbtn=findViewById(R.id.backbtn);
        Bundle extras=getIntent().getExtras();
        recyclerView=findViewById(R.id.needypRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Type=extras.getString("Type");
        final int[] v = {0};
        Contact=extras.getString("Contact");
        arrayList=new ArrayList<>();
        ra=new RequestsAdaptor(this,arrayList);
        recyclerView.setAdapter(ra);
        message=findViewById(R.id.text1);
        profilebtn=findViewById(R.id.profilebtn);
        Notification=findViewById(R.id.notificationbtn);

        databaseReference.child("Users").child("Requests").child("Needy").child(Contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String parent=dataSnapshot.getRef().getKey();
                    addrequest req=dataSnapshot.getValue(addrequest.class);
                    req.setContact(Contact);
                    req.setParent(parent);
                    req.setType(Type);
                    arrayList.add(req);
                    message.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                ra.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button save,cancel;
                EditText cnic,address,accountno,description;
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.addrequestdialog,null);
                save=dialogview.findViewById(R.id.rsavebtn);
                cancel=dialogview.findViewById(R.id.rcancelbtn);
                cnic=dialogview.findViewById(R.id.rcnic);
                address=dialogview.findViewById(R.id.raddress);
                accountno=dialogview.findViewById(R.id.raccount);
                description=dialogview.findViewById(R.id.rdescription);
                Spinner requesttype=dialogview.findViewById(R.id.scholarshiptype);
                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(view.getRootView().getContext(), R.array.ScholarshipType, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                requesttype.setAdapter(adapter);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        addrequest addreq=new addrequest();
                        addreq.setCnic(String.valueOf(cnic.getText()));
                        addreq.setDate(year+"-"+month+"-"+day);
                        addreq.setAddress(String.valueOf(address.getText()));
                        addreq.setAccountNo(accountno.getText().toString());
                        addreq.setDescription(description.getText().toString());
                        addreq.setRequestType(requesttype.getSelectedItem().toString());
                        addreq.setContact(Contact);
                        databaseReference.child("Users").child("Requests").child(Type).child(Contact).push().setValue(addreq);
                        ra.clear();
                    }
                });




                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.setCancelable(true);
                    }
                });
                builder.setView(dialogview);
                builder.show();


            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NeedyRequests.this,ProfilePage.class);
                intent.putExtra("Type",Type);
                intent.putExtra("Contact",Contact);

                startActivity(intent);
            }
        });
        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NeedyRequests.this, NotificationPage.class);
                intent.putExtra("Type",Type);
                intent.putExtra("Contact",Contact);

                startActivity(intent);

            }
        });
    }

    public void relaod() {

        Intent intent=new Intent(NeedyRequests.this,NeedyRequests.class);
        intent.putExtra("Type",Type);
        intent.putExtra("Contact",Contact);

        startActivity(intent);
    }
}