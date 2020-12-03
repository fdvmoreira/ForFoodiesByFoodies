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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SignUp extends AppCompatActivity {

    final int REQUEST_CODE = 200;
    private Bitmap bitmapProfilePicture;
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
                        Snackbar.make(ivProfilePicture, "Upload Successful", Snackbar.LENGTH_LONG).show();
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
                            final StorageReference photoRef = mStorageProfilePicturesRef.child(user.getUserId() + ".jpg");
                            photoRef.putBytes(byteArrayOutputStream.toByteArray()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
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
                                                if (task.isSuccessful())
                                                    Toast.makeText(getApplicationContext(), " User " + user.getUserId(), Toast.LENGTH_SHORT).show();
                                            }

                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DATABASE", "User was saved!");

                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("DATABASE", e.getMessage() + "");
                                            }
                                        });
                                    }
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // upload successful
                                    Log.d("STORAGE", "Image upload was successful! ");
                                    Toast.makeText(getApplicationContext(), "Profile picture uploaded successfully!", Toast.LENGTH_SHORT).show();
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
                            try {
                                task.getException().printStackTrace();
                            } catch (Exception e) {
                                Log.e("UPLOAD: ", e.getMessage() + "");
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
                    //TODO: 1 validate inputs -> 2 create new user -> 3 save profile photo -> 4 add user to database
                    boolean isFormValid = true;

                    //Log.e("CLICK","Creating ...");

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

                    // call the function to create user
//                    createNewUser(new User(etFullName.getText().toString(), etEmailAddress.getText().toString(), etPassword.getText().toString()));
                    createNewUser(new User(etFullName.getText().toString(), etEmailAddress.getText().toString(), etPassword.getText().toString(), "testURL"));

                    break;
                case R.id.tvActivitySignUpAlreadyHaveAnAccount:

                    startActivity(new Intent(getApplicationContext(), Login.class));
                    break;
                default:
            }
        }


    }
}