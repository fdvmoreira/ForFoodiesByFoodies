package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    protected TextView tvForgotPassword, tvDoNoHaveAnAccount;
    protected Button btnSignIn;
    //Firebase
    FirebaseAuth mAuth = null;
    //Click Listener
    ClickListener clickListener = null;
    //Views
    private EditText etEmailAddress, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide the action bar
        getSupportActionBar().hide();

        //init firebase auth
        mAuth = FirebaseAuth.getInstance();

        //init views
        tvForgotPassword = findViewById(R.id.tvActivityLoginForgotPassword);
        btnSignIn = findViewById(R.id.btnActivityLoginSignIn);
        tvDoNoHaveAnAccount = findViewById(R.id.tvActivityLoginDoNotHaveAnAccount);


    }

    /**
     *
     */
    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // this intent will get will get its data depending on which view was clicked
            Intent intent = null;

            //get the clicked view and act accordingly
            switch (v.getId()) {
                case R.id.tvActivityLoginForgotPassword:
//                    intent = new Intent(getApplicationContext(),ResetPassword.class);
                    Log.d("Login#ClickListener: ", " Forgot password clicked");
                    break;
                case R.id.btnActivityLoginSignIn:
                    Log.d("Login#ClickListener: ", "Sign In button clicked");
                    break;
                case R.id.tvActivityLoginDoNotHaveAnAccount:
                    Log.d("Login#ClickListener: ", " Do not have an account");
                    break;
                default:
                    intent = new Intent();
            }

            //go to next activity
            //startActivity(intent);
        }
    }
}