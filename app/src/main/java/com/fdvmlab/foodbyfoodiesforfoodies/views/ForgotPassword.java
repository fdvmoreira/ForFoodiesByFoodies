package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    // firebase Auth
    FirebaseAuth mAuth = null;

    //views ref
    private Button btnRecoverPassword;
    private EditText etEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // init views
        etEmailAddress = findViewById(R.id.etForgotPasswordEmailAddress);
        btnRecoverPassword = findViewById(R.id.btnForgotPasswordRecoverPassword);

        //btnRecoverPassword.setOnClickListener(new ClickListener());
        
    }
}