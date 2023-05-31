package com.example.donation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class RequestsAdaptor extends RecyclerView.Adapter<RequestsAdaptor.MyView> {
    NeedyRequests nr;
    private Context context;
    private ArrayList<addrequest> addrequestslist;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    public RequestsAdaptor(android.content.Context context, ArrayList<addrequest> addrequestslist) {
        this.context =  context;
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
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nrequests,parent,false);
        return new MyView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        addrequest addrq=addrequestslist.get(position);
        holder.type.setText(addrq.getRequestType());
        holder.cnic.setText(addrq.getCnic());
        holder.address.setText(addrq.getAddress());
        holder.date.setText(addrq.getCnic());
        holder.phone.setText(addrq.getContact());
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button save,cancel;
                TextView title;
                EditText cnic,address,accountno,description;
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogview= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.addrequestdialog,null);
                save=dialogview.findViewById(R.id.rsavebtn);
                cancel=dialogview.findViewById(R.id.rcancelbtn);
                title=dialogview.findViewById(R.id.textView18);
                cnic=dialogview.findViewById(R.id.rcnic);
                address=dialogview.findViewById(R.id.raddress);
                accountno=dialogview.findViewById(R.id.raccount);
                description=dialogview.findViewById(R.id.rdescription);
                Spinner requesttype=dialogview.findViewById(R.id.scholarshiptype);
                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(view.getRootView().getContext(), R.array.ScholarshipType, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                requesttype.setAdapter(adapter);
                cnic.setText(addrq.getCnic());
                requesttype.setSelection(getIndex(requesttype,addrq.getRequestType()));
                address.setText(addrq.getAddress());
                accountno.setText(addrq.getAccountNo());
                description.setText(addrq.getDescription());
                title.setText("Update Request");
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("cnic").setValue(cnic.getText().toString());
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("Date").setValue(year+"-"+month+"-"+day);
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("address").setValue(address.getText().toString());
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("AccountNo").setValue(accountno.getText().toString());
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("description").setValue(description.getText().toString());
                        databaseReference.child("Users").child("Requests").child("Needy").child(addrq.getContact()).child(addrq.getParent()).child("requestType").setValue(requesttype.getSelectedItem().toString());
                        clear();

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
    public class MyView extends RecyclerView.ViewHolder {
        TextView type,date,cnic,phone,address;
        ConstraintLayout editbtn;
        public MyView(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.requesttype);
            date=itemView.findViewById(R.id.date);
            cnic=itemView.findViewById(R.id.cnic);
            phone=itemView.findViewById(R.id.contact);
            address=itemView.findViewById(R.id.address);
            editbtn=itemView.findViewById(R.id.Editbtn);
        }
    }


}
