package com.example.donation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class loginpage extends AppCompatActivity {

    ConstraintLayout needy, donor, forgetPassword;
    TextInputEditText getemail, getpassword;
    LinearLayout needylay, donoralay;
    Button signinbtn, donorsigninbtn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://donation-1ff65-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String type = "Needy";
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_loginpage);
        needy = findViewById(R.id.needyselected);
        needylay = findViewById(R.id.needylayout);
        donor = findViewById(R.id.donorselected);
        donoralay = findViewById(R.id.donorlayout);
        forgetPassword = findViewById(R.id.forgetpasswordbtn);
        TextView needytext = findViewById(R.id.needytext);
        TextView donortext = findViewById(R.id.donortext);
        TextView signup = findViewById(R.id.footersignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, SignUpPage.class));
            }
        });
        needy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donortext.setTextColor(Color.parseColor("#B0E8BC"));
                needytext.setTextColor(Color.parseColor("#30A84A"));
                donoralay.setVisibility(View.GONE);
                needylay.setVisibility(View.VISIBLE);
                donor.setBackground(null);
                needy.setBackground(getDrawable(R.drawable.selectedbox));
                String type = "Needy";

            }
        });
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needytext.setTextColor(Color.parseColor("#B0E8BC"));
                donortext.setTextColor(Color.parseColor("#30A84A"));
                donor.setBackground(getDrawable(R.drawable.selectedbox));
                needy.setBackground(null);
                needylay.setVisibility(View.GONE);
                donoralay.setVisibility(View.VISIBLE);
                String type = "Donor";
            }
        });
        signinbtn = findViewById(R.id.Signinbtn);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getemail = findViewById(R.id.getemail);
                getpassword = findViewById(R.id.getpasswordn);
                login("Needy");
            }
        });

        donorsigninbtn = findViewById(R.id.donorsigninbtn);
        donorsigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getemail = findViewById(R.id.donoremail);
                getpassword = findViewById(R.id.getdpassword);
                login("Donor");
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, ForgetPasswordPage.class));
            }
        });
    }

    void login(String acount) {
        String Emailtxt, passwordtxt;
        Emailtxt = getemail.getText().toString();
        passwordtxt = getpassword.getText().toString();
        if (!Emailtxt.matches(pattern)) {
            getemail.setError("Enter correct email");
            getemail.requestFocus();
        } else if (passwordtxt.isEmpty()) {
            getpassword.setError("Password required!");
            getpassword.requestFocus();
        } else {
            final String[] contact = new String[1];
            boolean found[]=new boolean[1];
            found[0]=false;
            Random random = new Random();
            final String em[]=new String[1];
            String split[] = Emailtxt.split("@", 10);
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
                    if(snapshot.child("Admin").hasChild(em[0]))
                    {
                        String pas = (String) snapshot.child("Admin").child(em[0]).child("Password").getValue();
                        if(passwordtxt.equals(pas))
                        {
                            Intent intent=new Intent(loginpage.this,dashboard.class);
                            intent.putExtra("Email", em[0]);
                            startActivity(intent);

                            Toast.makeText(loginpage.this, "Successfully Signed In a", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(loginpage.this, "Email or password is incorrect.", Toast.LENGTH_SHORT).show();
                        }

                    }
                     else {
                        if (snapshot.child("Auth").hasChild(em[0])) {
                            contact[0] = (String) snapshot.child("Auth").child(em[0]).child("Contact").getValue();
                            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child(acount).hasChild(contact[0])) {

                                        firebaseAuth.signInWithEmailAndPassword(Emailtxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(loginpage.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(loginpage.this, verification.class);
                                                    intent.putExtra("Type", acount);
                                                    intent.putExtra("Contact", contact[0]);
                                                    //   intent.putExtra()

                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(loginpage.this, "Not Registered.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(loginpage.this, "Not Registered.", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(loginpage.this, "Not found.", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            Toast.makeText(loginpage.this, "wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(loginpage.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}