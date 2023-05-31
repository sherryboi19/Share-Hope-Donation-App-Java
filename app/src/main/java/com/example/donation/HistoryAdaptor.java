package com.example.donation;

import android.app.AlertDialog;
import android.content.Context;
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

public class HistoryAdaptor extends RecyclerView.Adapter<HistoryAdaptor.holder> {
    private Context context;
    private ArrayList<addrequest> addrequestslist;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    public HistoryAdaptor(Context context, ArrayList<addrequest> addrequestslist) {
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
    public class holder extends RecyclerView.ViewHolder {
        TextView type,date,tid,phone,amount,description,status;
        ConstraintLayout editbtn;
        public holder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            type=itemView.findViewById(R.id.requesttype);
            date=itemView.findViewById(R.id.date);
            tid=itemView.findViewById(R.id.tid);
            phone=itemView.findViewById(R.id.contact);
            amount=itemView.findViewById(R.id.amount);
            description=itemView.findViewById(R.id.description);
            editbtn=itemView.findViewById(R.id.Editbtn);

        }
    }
    @NonNull
    @Override
    public HistoryAdaptor.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dhistory,parent,false);
        return new HistoryAdaptor.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdaptor.holder hold, int position) {
        addrequest addrq=addrequestslist.get(position);
        hold.type.setText(addrq.getRequestType());
        hold.status.setText(addrq.getStatus());
        hold.tid.setText(addrq.getTid());
        hold.amount.setText(addrq.getAmount());
        hold.date.setText(addrq.getDate());
        hold.phone.setText(addrq.getContact());
        hold.description.setText(addrq.getDescription());
        if(addrq.getType().equals("Donor")) {
            hold.editbtn.setOnClickListener(new View.OnClickListener() {
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
                    Log.d("what",addrq.getContact());
                    Log.d("donor",addrq.getDonor());
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
                    donatebtn.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    builder.setView(dialogview);
                    builder.show();

                }
            });

        }
        else if(addrq.getType().equals("Admin")) {
            hold.editbtn.setOnClickListener(new View.OnClickListener() {
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
                    Log.d("what",addrq.getContact());
                    Log.d("donor",addrq.getDonor());
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
                    donatebtn.setText("Allow");
                    donatebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("NeedyVerification").setValue("Allow");

                        }
                    });
                    cancel.setVisibility(View.GONE);
                    builder.setView(dialogview);
                    builder.show();

                }
            });

        }

        else{
            hold.editbtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return addrequestslist.size();
    }


}
