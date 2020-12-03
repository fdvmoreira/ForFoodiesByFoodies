package com.fdvmlab.foodbyfoodiesforfoodies;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Util {
    /**
     * Validate email address
     *
     * @param emailAddress email address
     * @return true if is valid or false otherwise
     */
    public static boolean isEmailValid(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     * validate password
     *
     * @param password firebase app password
     * @return true if is valid or false otherwise
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    /**
     * validate input text
     *
     * @param text plain text
     * @return true if is valid or false otherwise
     */
    public static boolean isTextValid(String text) {
        return text.isEmpty();
    }

    /**
     * Upload the bitmap
     *
     * @param bitmapImage
     * @param imageId
     * @param storageReference
     */
    public void uploadImage(Bitmap bitmapImage, String imageId, StorageReference storageReference) {
        // convert to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // move bitmap obj into baos
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // upload image
        storageReference.child(imageId).putBytes(baos.toByteArray())
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("", "Upload" + ((task.isSuccessful()) ? "successful!" : " failed!"));
                        }
                    }
                });
    }
}
