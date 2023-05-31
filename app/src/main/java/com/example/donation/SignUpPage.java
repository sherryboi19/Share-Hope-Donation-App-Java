package com.example.donation;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignUpPage extends AppCompatActivity {
    TextInputEditText name,phone,email,password,confirmpassword;
    RadioButton select,needyradio,donorradio;
    RadioGroup type;
    Button signup;
    Spinner phonecode;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");
    FirebaseUser firebaseUser;

    String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        TextView signin=findViewById(R.id.footersignin);
        Spinner spinnercodes=findViewById(R.id.phonecode);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.phonecodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnercodes.setAdapter(adapter);
        firebaseAuth= getInstance();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this,loginpage.class));
            }
        });
        name=findViewById(R.id.State);
        email=findViewById(R.id.getemail);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.getpasswordn);
        confirmpassword=findViewById(R.id.getconfirmpassword);
        type=findViewById(R.id.type);
        phonecode=findViewById(R.id.phonecode);
        needyradio=findViewById(R.id.needyradiobutton);
        donorradio=findViewById(R.id.DonorradioButton);
        signup=findViewById(R.id.donebtn);
        final String[] accounttype = {"Needy"};
        donorradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needyradio.setTextColor(Color.parseColor("#BCBCBC"));
                donorradio.setTextColor(Color.parseColor("#30A84A"));
                accounttype[0] ="Donor";
            }
        });
        needyradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donorradio.setTextColor(Color.parseColor("#BCBCBC"));
                needyradio.setTextColor(Color.parseColor("#30A84A"));
                accounttype[0] ="Needy";
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Athentication(accounttype[0]);
            }
        });
    }
    void Athentication(String acount){
        String nametxt,phonetxt,passwordtxt,confirmpasswordtxt,phonecodetxt,emailtxt,selected;
        ProgressDialog progressDialog = new ProgressDialog(this);
        emailtxt=email.getText().toString();
        nametxt=name.getText().toString();
        phonetxt=phone.getText().toString();

        passwordtxt=password.getText().toString();
        confirmpasswordtxt=confirmpassword.getText().toString();
        phonecodetxt=phonecode.getSelectedItem().toString();
        String contact=phonecodetxt+phonetxt;
        if(nametxt.isEmpty()){

            name.setError("Enter Full Name");
            name.requestFocus();
        }
        else if(phonetxt.length() != 10){
            phone.setError("Incorrect phone number");
            phone.requestFocus();
        }
        else if(phonetxt.isEmpty()){
            phone.setError("Enter phone number");
            phone.requestFocus();
        }
        else if(!emailtxt.matches(pattern)){
            email.setError("Enter correct email");
            email.requestFocus();
        }
        else if(passwordtxt.isEmpty()){
            password.setError("Please Enter password");
            password.requestFocus();
        }
        else if(passwordtxt.length()<6){
            password.setError("Atleast 6 characters");
            password.requestFocus();
        }
        else if(!confirmpasswordtxt.equals(passwordtxt)){
            confirmpassword.setError("Password not matched");
            confirmpassword.requestFocus();
        }
        else {

          /*  firebaseUser=firebaseAuth.getCurrentUser();
            firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignUpPage.this,"sucess.",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpPage.this,"Fail.",Toast.LENGTH_SHORT).show();
                }
            });
          */ /* Random random=new Random();
            int code=random.nextInt(8999)+1000;
            Toast.makeText(SignUpPage.this,random.nextInt(8999)+1000,Toast.LENGTH_SHORT).show();
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, emailtxt);
            email.putExtra(Intent.EXTRA_SUBJECT, "Email verification");
            email.putExtra(Intent.EXTRA_TEXT, "Your otp code: "+code);

            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));

           */
            String authe="Auth";
           final String em[]=new String[1];
            String split[] = emailtxt.split("@", 10);
            int j=0;
            for (String s: split)
            {
                if (j==0){
                    j=1;
                em[0]=s;
                }
            }


            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(acount).hasChild(contact)){
                        Toast.makeText(SignUpPage.this,"Already Registered.",Toast.LENGTH_SHORT).show();
                    }
                    else{

                     //    User user=new User(nametxt,contact,emailtxt,passwordtxt,acount);
                        firebaseAuth.createUserWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.setMessage("Please wait while registration");
                                    progressDialog.setTitle("Registration");
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    Calendar calendar = Calendar.getInstance();
                                    int year = calendar.get(Calendar.YEAR);
                                    int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
                                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    databaseReference.child("Users").child(acount).child(contact).child("Name").setValue(nametxt);
                                    databaseReference.child("Users").child(acount).child(contact).child("Password").setValue(passwordtxt);
                                    databaseReference.child("Users").child(authe).child(em[0]).child("Contact").setValue(contact);
                                    databaseReference.child("Users").child(acount).child(contact).child("Email").setValue(emailtxt);
                                    databaseReference.child("Users").child(acount).child(contact).child("Date").setValue(year+"-"+month+"-"+day);
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUpPage.this,"Successfully Registered.",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpPage.this, verification.class);
                                    intent.putExtra("Type", acount);
                                    intent.putExtra("Contact", contact);
                                    startActivity(intent);

                                  /*  FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(acount).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpPage.this,"Successfully Registered.",Toast.LENGTH_SHORT).show();
                                             //   finish();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
*/
                                }
                                else{
                                    Toast.makeText(SignUpPage.this,"Already Registered.",Toast.LENGTH_SHORT).show();
                                }
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


}