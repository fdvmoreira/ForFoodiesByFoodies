package com.fdvmlab.foodbyfoodiesforfoodies.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.foodbyfoodiesforfoodies.Util;
import com.fdvmlab.foodbyfoodiesforfoodies.models.User;
import com.fdvmlab.foodbyfoodiesforfoodies.models.UserRole;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SignUp extends AppCompatActivity {

    // Pick an image request code
    final int REQUEST_CODE = 200;

    private User mAdmin = null;

    // Firebase Auth
    private FirebaseAuth mAuth;
    private StorageReference mProfilePicturesStorageRef;
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

        mProfilePicturesStorageRef = FirebaseStorage.getInstance().getReference("profile_pictures");

        mDatabaseUsersRef = FirebaseDatabase.getInstance().getReference("users");

        // init views
        ivProfilePicture = findViewById(R.id.ivActivitySigUpProfilePicture);
        ivProfilePicture.setDrawingCacheEnabled(true);
        ivProfilePicture.buildDrawingCache();

        etFullName = findViewById(R.id.etActivitySignUpFullName);
        etEmailAddress = findViewById(R.id.etActivitySignUpEmailAddress);
        etPassword = findViewById(R.id.etActivitySignUpNewPassword);
        etConfirmPassword = findViewById(R.id.etActivitySignUpConfirmPassword);

        //add focus listener
        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etPassword.getText().toString().length() < 6) {
                        etPassword.setError("Password must be longer than 6 characters");
                    }
                }
            }
        });

        // add listeners to views
        ivProfilePicture.setOnClickListener(new ClickListener());
        findViewById(R.id.btnActivitySignUpSignUp).setOnClickListener(new ClickListener());
        findViewById(R.id.tvActivitySignUpAlreadyHaveAnAccount).setOnClickListener(new ClickListener());


    }

    @Override
    protected void onStart() {
        super.onStart();
        // get the admin user
        if (getIntent().getExtras().get("ADMIN") == null) {
            //create blank object
            mAdmin = new User();
        }
        mAdmin = (User) getIntent().getExtras().get("ADMIN");
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getData() != null) {
                        ivProfilePicture.setImageURI(data.getData());
                        Snackbar.make(ivProfilePicture, "Image load Successful", Snackbar.LENGTH_LONG).show();
                    }
                }
            } else {
                Snackbar.make(ivProfilePicture, "No Image Selected", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void createNewUser(final User user) {
        // Create new Auth
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTH", task.isSuccessful() ? "Success" : "Failed");

                        //If the registration was successful continue to upload image
                        if (task.isSuccessful()) {

                            // Get the newly created user ID
                            user.setUserId(task.getResult().getUser().getUid());

                            // Get Image from image view convert it into bytes and save them into storage
                            Bitmap bitmapImage = ((BitmapDrawable) ivProfilePicture.getDrawable()).getBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);


                            // Upload the profile photo
                            final StorageReference photoRef = mProfilePicturesStorageRef.child(user.getUserId() + ".jpg");
                            photoRef.putBytes(byteArrayOutputStream.toByteArray()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        //upload has failed
                                        try {
                                            throw task.getException();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                    return photoRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    // upload complete
                                    if (task.isSuccessful()) {

                                        //get the uploaded image's URL
                                        try {
                                            Uri downloadUrl = task.getResult();
                                            if (downloadUrl != null) {
                                                user.setProfilePhotoUrl(downloadUrl.toString());
                                                Log.d("DownloadUrl", downloadUrl.toString());
                                            }

                                        } catch (Exception e) {
                                            Log.e("DownloadUrl", " Could NOT get download URL" + e.getMessage() + "");
                                        }

                                        // Save User to database
                                        mDatabaseUsersRef.child(user.getUserId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // save complete
                                                if (task.isSuccessful())
                                                    Log.d("DATABASE", "Created User ID :" + user.getUserId());
                                                //Toast.makeText(getApplicationContext(), " User " + user.getUserId(), Toast.LENGTH_SHORT).show();
                                            }

                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // save to database success
                                                Log.d("DATABASE", "User was saved!");
                                                Toast.makeText(getApplicationContext(), "User created successfully!", Toast.LENGTH_LONG).show();

                                                // If the App user is not admin it is taken to sign in activity
                                                if (mAdmin == null) {
                                                    mAuth.signOut();
                                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                                    return;
                                                }

                                                //sign in the admin again
                                                mAuth.signInWithEmailAndPassword(mAdmin.getEmail(), mAdmin.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        //auth complete

                                                        if (!task.isSuccessful()) {
                                                            // sign back in has failed
                                                            try {
                                                                Log.e("AUTH", "Unable to sign in: " + task.getException().getMessage());
                                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            return;
                                                        }
                                                        // admin sign in successfully
                                                        Toast.makeText(getApplicationContext(), "Logged in as " + mAdmin.getName(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // save to database failed
                                                Log.e("DATABASE", e.getMessage() + "");
                                                Toast.makeText(getApplicationContext(), "User was NOT saved!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // upload successful
                                    Log.d("STORAGE", "Image upload was successful! ");
                                    //Toast.makeText(getApplicationContext(), "Profile picture uploaded successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // upload failure
                                    Log.e("STORAGE", "Image upload has FAILED! " + e.getMessage());
                                    Toast.makeText(getApplicationContext(), "Image upload has FAILED! ", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            if (task.getException() != null) {
                                try {
                                    // New Auth was not created
                                    Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    task.getException().printStackTrace();
                                } catch (Exception e) {
                                    Log.e("UPLOAD: ", e.getMessage() + "");
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Click Listener to handle clicks
     */
    private class ClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivActivitySigUpProfilePicture:
                    //pick image from sdcard

                    startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), REQUEST_CODE);
                    break;

                case R.id.btnActivitySignUpSignUp:
                    //1 validate inputs -> 2 create new user -> 3 save profile photo -> 4 add user to database
                    boolean isFormValid = true;

                    //check name
                    if (Util.isTextInvalid(etFullName.getText().toString().trim())) {
                        etFullName.setError("Invalid Name");
                        isFormValid = false;
                        break;
                    }

                    //check email address
                    if (!Util.isEmailValid(etEmailAddress.getText().toString())) {
                        etEmailAddress.setError("Email Is Invalid");
                        isFormValid = false;
                        break;
                    }

                    //check password
                    if (!Util.isPasswordValid(etPassword.getText().toString())) {
                        etPassword.setError("Password is too short (minimum 6 character");
                        isFormValid = false;
                        break;
                    }

                    //confirm password
                    if (!Util.isPasswordValid(etConfirmPassword.getText().toString())) {
                        etConfirmPassword.setError("Too short (minimum 6 characters)");
                        isFormValid = false;
                        break;
                    }

                    // check password match
                    if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                        etConfirmPassword.setError("Passwords do NOT match");
                        isFormValid = false;
                        break;
                    }

                    /**
                     * determine the ROLE type based on the type of user who's creating this new user
                     * if the user is anonymous the role for the user being created
                     * will be STANDARD and CRITIC if the user is created by an ADMIN
                     */
                    UserRole role = null;
                    try {
                        role = (UserRole) getIntent().getExtras().get("USER_ROLE");
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    // Create user
                    createNewUser(new User(
                            etFullName.getText().toString(),
                            etEmailAddress.getText().toString(),
                            etPassword.getText().toString(),

                            // if role was not passed it will be standard
                            (role != null) ? role.name() : UserRole.STANDARD.name()));

                    break;

                case R.id.tvActivitySignUpAlreadyHaveAnAccount:
                    //go to login page
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    break;
                default:
            }
        }
    }
}