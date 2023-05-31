package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordPage extends AppCompatActivity {
Spinner spinner;
LinearLayout emailLayout,numberLayout;
TextView byemail,bynumber,textView;
TextInputEditText number;
Button send;
    String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passord_page);
        spinner=findViewById(R.id.phonecode);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.phonecodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        number=findViewById(R.id.phone);

        emailLayout=findViewById(R.id.EmailLayout);
        numberLayout=findViewById(R.id.NumberLayout);
        byemail=findViewById(R.id.byemailbtn);
        bynumber=findViewById(R.id.byenumberbtn);
        textView=findViewById(R.id.description);
        final boolean[] type = {true};
        number=findViewById(R.id.getemail);
        final String[] description = {"Don't worry! Just enter your email and we’ll send you a reset password link"};
        byemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailLayout.setVisibility(View.VISIBLE);
                numberLayout.setVisibility(View.GONE);
                description[0] ="Don't worry! Just enter your email and we’ll send you a reset password link";
                textView.setText(description[0]);
                number=findViewById(R.id.getemail);
                type[0] =true;
            }
        });
        bynumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailLayout.setVisibility(View.GONE);
                numberLayout.setVisibility(View.VISIBLE);
                description[0] ="Don't worry! Just enter your number and we’ll send you otp";
                textView.setText(description[0]);
                number=findViewById(R.id.phone);
                type[0] =false;
            }
        });
        send=findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                ProgressBar progressBar;
                progressBar=findViewById(R.id.progressBar);
                if(type[0]) {

                    String Emailtxt=number.getText().toString();
                    if(!Emailtxt.matches(pattern)){
                        number.setError("Enter correct email");// number contain email
                        number.requestFocus();
                    }
                    else{
                        send.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseAuth.getInstance().sendPasswordResetEmail(Emailtxt)

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ForgetPasswordPage.this, "Successfully sent!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ForgetPasswordPage.this, otpPage.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(ForgetPasswordPage.this, "Not sent!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                        progressBar.setVisibility(View.GONE);
                        send.setVisibility(View.VISIBLE);
                    }


                }
                else{

                    String contact = spinner.getSelectedItem().toString() + number.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(contact, 60, TimeUnit.SECONDS, ForgetPasswordPage.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            Toast.makeText(ForgetPasswordPage.this, "Verfied", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(ForgetPasswordPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            Intent intent = new Intent(ForgetPasswordPage.this, loginpage.class);
                            intent.putExtra("Contact", contact);
                            intent.putExtra("id", s);
                            startActivity(intent);

                        }
                    });
                }
                }
            });
    }
}