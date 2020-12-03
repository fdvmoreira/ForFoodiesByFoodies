package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    // firebase Auth
    FirebaseAuth mAuth = null;

    //views ref
    private EditText etEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // init views
        etEmailAddress = findViewById(R.id.etForgotPasswordEmailAddress);

        //add click listener to recover password button
        findViewById(R.id.btnForgotPasswordRecoverPassword).setOnClickListener(new ClickListener());

    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnForgotPasswordRecoverPassword:
                    // TODO: get text from input field, validate, send reset password email
                    String emailAddress = etEmailAddress.getText().toString().trim();

                    // check if the entered input is valid
                    if (!isEmailValid(emailAddress)) {
                        Log.e("ForgotPass#ClkListener", emailAddress.isEmpty() ? "Email Required" : "Invalid Email");
                        break;
                    }
                    //sendResetPasswordEmail();
                default:
            }
        }

        /**
         * Validate email address
         *
         * @param emailAddress
         * @return true if is valid or false otherwise
         */
        private boolean isEmailValid(String emailAddress) {
            return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        }
    }
}