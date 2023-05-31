package com.example.donation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class nRequestsAdator extends RecyclerView.Adapter<nRequestsAdator.nViews> {
    private Context context;

    private ArrayList<addrequest> addrequestslist;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    public nRequestsAdator(Context context, ArrayList<addrequest> addrequestslist) {
        this.context = context;
        this.addrequestslist = addrequestslist;
    }
    public void add(addrequest ar)
    {
        addrequestslist.add(ar);
        notifyDataSetChanged();
    }
    public void clear(){
        addrequestslist.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public nRequestsAdator.nViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminrequests,parent,false);
        return new nRequestsAdator.nViews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull nRequestsAdator.nViews holder, int position) {
        addrequest addrq=addrequestslist.get(position);
        holder.type.setText(addrq.getRequestType());
        Log.d("msg","enterh");

        holder.date.setText(addrq.getDate());
        holder.cnic.setText(addrq.getCnic());
        holder.phone.setText(addrq.getContact());
        holder.account.setText(addrq.getAccountNo());
        holder.address.setText(addrq.getAddress());
        holder.description.setText(addrq.getDescription());
        Log.d("msg",addrq.getDescription());

        databaseReference.child("Users").child("Donor").child(addrq.getDonor()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usermodel um=snapshot.getValue(usermodel.class);
                holder.name.setText(um.getName());
                Log.d("msg",um.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("parent",error.getDetails());
                Log.d("parent",error.getMessage());

            }
        });

        holder.tid.setText(addrq.getTid());
        holder.amount.setText(addrq.getAmount());
        holder.description.setText(addrq.getDescription());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("DonorVerification").setValue("Allow");

            }
        });

    }

    @Override
    public int getItemCount() {
        return addrequestslist.size();
    }

    public class nViews extends RecyclerView.ViewHolder {
        TextView type,date,tid,phone,amount,description,description1,address,cnic,account,name;
        ConstraintLayout editbtn;

        public nViews(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datea);
            cnic=itemView.findViewById(R.id.cnica);
            phone=itemView.findViewById(R.id.contacta);
            account=itemView.findViewById(R.id.account);
            address=itemView.findViewById(R.id.addressa);
            description=itemView.findViewById(R.id.descriptiona);

            name=itemView.findViewById(R.id.namea);
            amount=itemView.findViewById(R.id.amounta);
            tid=itemView.findViewById(R.id.tidad);
          description1=itemView.findViewById(R.id.descriptionad);

            type=itemView.findViewById(R.id.requsttype);
            editbtn=itemView.findViewById(R.id.Editbtn);

        }
    }
}
