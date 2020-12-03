package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

        btnRecoverPassword.setOnClickListener(new ClickListener());

    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnForgotPasswordRecoverPassword:
                    // TODO: get text from input field, validate, send reset password email

                    if (!isEmailValid(etEmailAddress.getText().toString())) {
                        Log.e("ForgotPass#ClkListener", "Invalid Email");
                    }

                    //sendResetPasswordEmail();
                    
                    break;
                default:
            }
        }

        private boolean isEmailValid(String toString) {
            if (!Patterns.EMAIL_ADDRESS.matcher(toString).matches())
                return false;
            return true;
        }
    }
}