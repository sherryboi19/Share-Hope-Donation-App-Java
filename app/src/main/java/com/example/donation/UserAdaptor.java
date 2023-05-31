package com.example.donation;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.MyViewHolder> {
    private Context context;
    private ArrayList<usermodel> usermodelList;
    String parent,type;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    public UserAdaptor(Context context, ArrayList<usermodel> usermodelList) {
        this.context = context;
        this.usermodelList = usermodelList;
    }
    public void senddata(String t,String p){
        parent=p;
        type=t;
    }
    public void add(usermodel um)
    {
        usermodelList.add(um);
        notifyDataSetChanged();
    }
    public void clear(){
        usermodelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        usermodel um= usermodelList.get(position);
        holder.name.setText(um.getName());
        holder.contact.setText(um.getCity());
        holder.date.setText(um.getDate());
        holder.cnic.setText(um.getCnic());
        holder.seemoretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview=LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.list_dialog,null);
                TextView email, Apartment, Address, State, ZipCode,dname,dcity,dcnic,dcontact;
                Button remove,confirm;
                remove=dialogview.findViewById(R.id.Removebtn);
                confirm=dialogview.findViewById(R.id.confirmbtn);
                parent=um.getParent();
                type=um.getType();
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child("Users").child(type).child(parent).child("Status").setValue("Reject");

                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            databaseReference.child("Users").child(type).child(parent).child("Status").setValue("Accept");
                    }
                });
                email=dialogview.findViewById(R.id.demail);
                Address=dialogview.findViewById(R.id.daddresstext);
                Apartment=dialogview.findViewById(R.id.dappartment);
                State=dialogview.findViewById(R.id.dstate);
                ZipCode=dialogview.findViewById(R.id.dzipcode);
                dcontact=dialogview.findViewById(R.id.dcontact);
                dcity=dialogview.findViewById(R.id.dcity);
                dcnic=dialogview.findViewById(R.id.dcnic);
                dname=dialogview.findViewById(R.id.dname);
                dname.setText(um.getName());
                dcity.setText(um.getCity());
                dcnic.setText(um.getCnic());
                dcontact.setText(um.getCity());
                email.setText(um.getEmail());
                Address.setText(um.getAddress());
                Apartment.setText(um.getApartment());
                State.setText(um.getState());
                ZipCode.setText(um.getZipCode());
                builder.setView(dialogview);
                builder.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return usermodelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,cnic,contact,date,seemoretxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.requesttype);
            cnic=itemView.findViewById(R.id.cnic);
            contact=itemView.findViewById(R.id.contact);
            date=itemView.findViewById(R.id.date);
            seemoretxt=itemView.findViewById(R.id.seemoretxt);
        }
    }
}
