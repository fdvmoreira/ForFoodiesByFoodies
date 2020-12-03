package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignUp extends AppCompatActivity {

    // Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseStorage mStorage;
    private StorageReference mStorageProfilePicturesRef;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseUsersRef;

    //views
    private ImageView ivProfilePicture;
    private EditText etFullName, etEmailAddress, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Hide top action bar
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Initialize firebase components
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mStorage = FirebaseStorage.getInstance();
        mStorageProfilePicturesRef = mStorage.getReference("profile_pictures");

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseUsersRef = mDatabase.getReference("users");

        // init views
        ivProfilePicture = findViewById(R.id.ivActivitySigUpProfilePicture);

        etFullName = findViewById(R.id.etActivitySignUpFullName);
        etEmailAddress = findViewById(R.id.etActivitySignUpEmailAddress);
        etPassword = findViewById(R.id.etActivitySignUpNewPassword);
        etConfirmPassword = findViewById(R.id.etActivitySignUpConfirmPassword);

        //add listeners to views
        findViewById(R.id.btnActivitySignUpSignUp).setOnClickListener(new ClickListener());

        findViewById(R.id.tvActivitySignUpAlreadyHaveAnAccount).setOnClickListener(new ClickListener());


    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}