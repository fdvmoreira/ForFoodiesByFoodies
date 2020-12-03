package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.foodbyfoodiesforfoodies.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    // firebase Auth
    FirebaseAuth mAuth = null;

    //views ref
    private EditText etEmailAddress;
    private Button btnRecoverPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //init auth
        mAuth = FirebaseAuth.getInstance();

        // init views
        etEmailAddress = findViewById(R.id.etForgotPasswordEmailAddress);

        //add click listener to recover password button
        btnRecoverPassword = findViewById(R.id.btnForgotPasswordRecoverPassword);
        btnRecoverPassword.setOnClickListener(new ClickListener());

    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnForgotPasswordRecoverPassword:
                    String emailAddress = etEmailAddress.getText().toString().trim();

                    // check if the entered input is valid
                    if (!Util.isEmailValid(emailAddress)) {
                        Log.e("ForgotPass#ClkListener", emailAddress.isEmpty() ? "Email Required" : "Invalid Email");
                        break;
                    }
                    //send email
                    sendResetPasswordEmail(emailAddress);
                default:
            }
        }

        /**
         * Recovery password
         */
        private void sendResetPasswordEmail(String emailAddress) {
            mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("RECOVERY", (task.isSuccessful()) ? "Recover SUCCESS" : " Recover FAIL");
                    //Snackbar.make(btnRecoverPassword,(task.isSuccessful())?"Email Sent!":"Recovery Failed!",Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), (task.isSuccessful()) ? "Email Sent!" : "Recovery Failed!", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful())
                        startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }
    }
}