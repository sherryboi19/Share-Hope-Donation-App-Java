package com.example.donation;

import android.content.Intent;
import android.graphics.Color;
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

public class List extends AppCompatActivity {
     UserAdaptor ua;
     ConstraintLayout donorselected,needyselected,dashboard,request,profile;
     TextView donor,needy;
     RecyclerView recyclerView;
     ArrayList<usermodel> arrayList;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list);
       recyclerView=findViewById(R.id.recycler);
       databaseReference=FirebaseDatabase.getInstance().getReference("Users");
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       arrayList=new ArrayList<>();
        Bundle extras=getIntent().getExtras();
        String email =extras.getString("Email");
        ua=new UserAdaptor(this,arrayList);
       recyclerView.setAdapter(ua);
       String type;
       type="Needy";
       donor=findViewById(R.id.donortext);
       needy=findViewById(R.id.needytext);
       donorselected=findViewById(R.id.donorselected);
       needyselected=findViewById(R.id.needyselected);
        request=findViewById(R.id.requestsbtn);
        profile=findViewById(R.id.profilebtn);
        dashboard=findViewById(R.id.dashboardbtn);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(List.this,ProfilePage.class);
                intent.putExtra("Type", "Admin");
                intent.putExtra("Contact", email);
                startActivity(intent);
                onBackPressed();

            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(List.this,Requests.class);
                intent.putExtra("Contact", email);
                startActivity(intent);
                onBackPressed();
                onBackPressed();
            }
        });

        needyselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ua.clear();

                donor.setTextColor(Color.parseColor("#30A84A"));
                needy.setTextColor(Color.parseColor("#FFFFFFFF"));
                donorselected.setBackground(null);
                needyselected.setBackground(getDrawable(R.drawable.greenbtn));
                String type = "Needy";

                databaseReference.child(type).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String parent=dataSnapshot.getRef().getKey();
                            usermodel user=dataSnapshot.getValue(usermodel.class);
                            user.setParent(parent);
                            user.setType(type);
                            arrayList.add(user);
                        }
                        ua.notifyDataSetChanged();
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
                needy.setTextColor(Color.parseColor("#30A84A"));
                donor.setTextColor(Color.parseColor("#FFFFFFFF"));
                donorselected.setBackground(getDrawable(R.drawable.greenbtn));
                needyselected.setBackground(null);
                String type = "Donor";
                databaseReference.child(type).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            usermodel user=dataSnapshot.getValue(usermodel.class);
                            String parent=dataSnapshot.getRef().getKey();
                            user.setParent(parent);
                            user.setType(type);
                            arrayList.add(user);
                        }
                        ua.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

       databaseReference.child(type).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                   usermodel user=dataSnapshot.getValue(usermodel.class);
                   String parent=dataSnapshot.getRef().getKey();
                   user.setParent(parent);
                   user.setType(type);
                   arrayList.add(user);
               }
               ua.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
}