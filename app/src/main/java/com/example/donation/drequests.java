package com.example.donation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class drequests extends RecyclerView.Adapter<drequests.views> {
    private Context context;
    private ArrayList<addrequest> addrequestslist;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    public drequests(Context context, ArrayList<addrequest> addrequestslist) {
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
    public class views extends RecyclerView.ViewHolder {
        TextView type,date,cnic,phone,address,description;
        ConstraintLayout editbtn;
        public views(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.requesttype);
            date=itemView.findViewById(R.id.date);
            cnic=itemView.findViewById(R.id.cnic);
            phone=itemView.findViewById(R.id.contact);
            address=itemView.findViewById(R.id.address);
            description=itemView.findViewById(R.id.description);
            editbtn=itemView.findViewById(R.id.Editbtn);

        }
    }
    @NonNull
    @Override
    public drequests.views onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.drequests,parent,false);
        return new drequests.views(view);

    }

    @Override
    public void onBindViewHolder(@NonNull drequests.views holder, int position) {
        DonerRequest donerRequest=new DonerRequest();
        addrequest addrq=addrequestslist.get(position);
        holder.type.setText(addrq.getRequestType());
        holder.cnic.setText(addrq.getCnic());
        holder.address.setText(addrq.getAddress());
        holder.date.setText(addrq.getDate());
        holder.phone.setText(addrq.getContact());
        holder.description.setText(addrq.getDescription());
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button donatebtn, cancel;
                EditText cnic, address, accountno, description, requesttype, name;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.donate_dialog, null);
                donatebtn = dialogview.findViewById(R.id.rsavebtn);
                cancel = dialogview.findViewById(R.id.rcancelbtn);
                cnic = dialogview.findViewById(R.id.rcnic);
                address = dialogview.findViewById(R.id.raddress);
                accountno = dialogview.findViewById(R.id.raccount);
                description = dialogview.findViewById(R.id.rdescription);
                requesttype = dialogview.findViewById(R.id.requestype);
                name = dialogview.findViewById(R.id.cnic);
                requesttype.setText(addrq.getRequestType());
                cnic.setText(addrq.getCnic());
                address.setText(addrq.getAddress());
                accountno.setText(addrq.getAccountNo());
                description.setText(addrq.getDescription());
                databaseReference.child("Users").child("Needy").child(addrq.getContact()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        usermodel um=snapshot.getValue(usermodel.class);
                        name.setText(um.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("parent",error.getDetails());
                        Log.d("parent",error.getMessage());

                    }
                });

                donatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,DonatePage.class);
                        intent.putExtra("parent", addrq.getParent());
                        intent.putExtra("phone", addrq.getContact());
                        intent.putExtra("Contact", addrq.getDonor());

                        context.startActivity(intent);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                dialogInterface.cancel();
                            }
                        });
                    }
                });
                builder.setView(dialogview);
                builder.show();

            }
        });

            }

            @Override
            public int getItemCount() {
                return addrequestslist.size();
            }


}