package com.fdvmlab.forfoodiesbyfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.forfoodiesbyfoodies.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        //init auth
        mAuth = FirebaseAuth.getInstance();

        // init views
        etEmailAddress = findViewById(R.id.etForgotPasswordEmailAddress);

        //add click listener to recover password button
        findViewById(R.id.btnForgotPasswordRecoverPassword).setOnClickListener(new ClickListener());

    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnForgotPasswordRecoverPassword) {
                String emailAddress = etEmailAddress.getText().toString().trim();

                // check if the entered input is valid
                if (!Util.isEmailValid(emailAddress)) {
                    Log.e("ForgotPass#ClkListener", emailAddress.isEmpty() ? "Email Required" : "Invalid Email");
                    return;
                }
                //send email
                sendResetPasswordEmail(emailAddress);
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
                    Toast.makeText(getApplicationContext(), (task.isSuccessful()) ? "Email Sent!" : "Recovery Failed!", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful())
                        startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }
    }
}