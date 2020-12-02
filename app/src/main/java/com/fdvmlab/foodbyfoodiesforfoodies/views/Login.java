package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    // Views
    protected EditText etEmailAddress, etPassword;
    protected Button btnSignIn;
    protected TextView tvForgotPassword, tvDoNoHaveAnAccount;
    //Firebase
    private FirebaseAuth mAuth = null;
    // Click Listener
    private ClickListener clickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // init firebase auth
        mAuth = FirebaseAuth.getInstance();

        // init views
        etEmailAddress = findViewById(R.id.etActivityLoginEmailAddress);
        etPassword = findViewById(R.id.etActivityLoginPassword);

        tvForgotPassword = findViewById(R.id.tvActivityLoginForgotPassword);
        btnSignIn = findViewById(R.id.btnActivityLoginSignIn);
        tvDoNoHaveAnAccount = findViewById(R.id.tvActivityLoginDoNotHaveAnAccount);


        // init and add listener to clickable views

        clickListener = new ClickListener();

        tvForgotPassword.setOnClickListener(clickListener);
        btnSignIn.setOnClickListener(clickListener);
        tvDoNoHaveAnAccount.setOnClickListener(clickListener);


    }

 
    /**
     * Implementation of click listener
     */
    private class ClickListener implements View.OnClickListener {
        public ClickListener() {

        }

        @Override
        public void onClick(View v) {

            // this intent will get will get its data depending on which view was clicked
//            Intent intent = null;

            // get the clicked view and act accordingly
            switch (v.getId()) {
                case R.id.tvActivityLoginForgotPassword:
//                    intent = new Intent(getApplicationContext(),ResetPassword.class);
                    Log.d("Login#ClickListener: ", " Forgot password clicked");
                    break;
                case R.id.btnActivityLoginSignIn:
                    Log.d("Login#ClickListener: ", "Sign In button clicked");
                    //validate inputs and sign in
                    if (validateInputFields())
                        //signin(etEmailAddress.getText().toString(), etPassword.getText().toString());
                        break;
                case R.id.tvActivityLoginDoNotHaveAnAccount:
                    Log.d("Login#ClickListener: ", " Do not have an account");
                    break;
                default:
            }
            // go to next activity
            // startActivity(intent);
        }

        /**
         * @return
         */
        private boolean validateInputFields() {

            // Error message
            String errorMessage;

            // Email address format check
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.getText().toString().trim()).matches()) {
                errorMessage = "Invalid format";
                if (etEmailAddress.getText().toString().trim().isEmpty()) {
                    errorMessage = "Required";
                }
                etEmailAddress.setError(errorMessage);
                return false;
            }

            // Password check
            if (etPassword.getText().toString().trim().length() < 6) {
                errorMessage = "Minimum length (6 characters)";
                etPassword.setError(errorMessage);
                return false;
            }
            return true;
        }
    }
}