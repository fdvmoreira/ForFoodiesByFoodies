package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.MainActivity;
import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    // Views
    protected EditText etEmailAddress, etPassword;
    protected Button btnSignIn;
    protected TextView tvForgotPassword, tvDoNoHaveAnAccount;
    //Firebase
    private FirebaseAuth mAuth = null;
    private FirebaseUser mUser = null;
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
     * Sign In with with the provided email and password
     *
     * @param email
     * @param password
     */
    private void signin(String email, String password) {
        //Log any current user out
        //mAuth.signOut();

        //signin
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.d("Login#SignIn#SUCCESS", authResult.getUser().getEmail() + " Logged In Successfully.");
                                mUser = authResult.getUser();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Login#SignIn#FAIL", e.getMessage() + "");
                            }
                        });
                    }
                });
    }

    /**
     * Implementation of click listener
     */
    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // this intent will get will get its data depending on which view was clicked
            Intent intent = null;

            // get the clicked view and act accordingly
            switch (v.getId()) {
                case R.id.tvActivityLoginForgotPassword:
                    Log.d("Login#ClickListener: ", " Forgot password clicked");
                    intent = new Intent(getApplicationContext(), ForgotPassword.class);
                    break;
                case R.id.btnActivityLoginSignIn:
                    Log.d("Login#ClickListener: ", "Sign In button clicked");
                    //validate inputs and sign in
                    if (validateInputFields()) {

                        signin(etEmailAddress.getText().toString(), etPassword.getText().toString());
                        //TODO open main activity
                        if (mUser != null) {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("uuid", mUser.getUid());
                        }
                    }
                    break;
                case R.id.tvActivityLoginDoNotHaveAnAccount:
                    Log.d("Login#ClickListener: ", " Do not have an account");
                    intent = new Intent(getApplicationContext(), SignUp.class);
                    break;
                default:
            }
            // go to next activity
            if (intent != null)
                startActivity(intent);
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