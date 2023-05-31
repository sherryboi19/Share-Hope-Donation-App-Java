package com.example.donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout signin,signup;
    Drawable BG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin=findViewById(R.id.Signin);
        signin.setBackground(getDrawable(R.drawable.whitecircle));
        TextView signintext=findViewById(R.id.signintext);
        signintext.setTextColor(Color.parseColor("#30A84A"));
        TextView signuptext=findViewById(R.id.signuptext);
        signuptext.setTextColor(Color.parseColor("#30A84A"));
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin.setBackground(getDrawable(R.drawable.green_circle));
                signintext.setTextColor(Color.parseColor("#FFFFFF"));
                startActivity(new Intent(MainActivity.this,loginpage.class));
            }
        });
        signup=findViewById(R.id.Signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* signup.setBackground(getDrawable(R.drawable.green_circle));
                signuptext.setTextColor(Color.parseColor("#FFFFFF"));
               */ startActivity(new Intent(MainActivity.this,SignUpPage.class));

            }
        });
    }

}