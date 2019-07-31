package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Signup_form extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText username,email_id,password,confirm_password ;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private ImageView userProfilePic;
    private static int PICK_IMAGE = 123;
    String uname, email, pass, conf_pass;
    Uri imagePath;
    private StorageReference storageReference;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && requestCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        setupUIViews();

        username =  findViewById(R.id.user_name);
        email_id =   findViewById(R.id.email_id);
        password =  findViewById(R.id.pass);
        confirm_password =  findViewById(R.id.confirm_pass);
        register = findViewById(R.id.register_button);
        userProfilePic = findViewById(R.id.ivProfilePic);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE );
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString().trim();
                email = email_id.getText().toString().trim();
                pass = password.getText().toString().trim();
                conf_pass = confirm_password.getText().toString().trim();

                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(Signup_form.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isDigitsOnly(uname)){
                    Toast.makeText(Signup_form.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup_form.this, "Email-id cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Signup_form.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<6){
                    Toast.makeText(Signup_form.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass.equals(conf_pass)){
                    Toast.makeText(Signup_form.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(Signup_form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sendEmailVerification();
                                        startActivity(new Intent(getApplicationContext(),Login_form.class));
                                        Toast.makeText(Signup_form.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Signup_form.this, "Email verification code has been sent", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Signup_form.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                    // ...
                                }
                            });

                }

            }
        });

    }

    private void setupUIViews() {
    }

    public void sendEmailVerification() {
        // [START send_email_verification]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            sendUserData();
                            Log.d(TAG, "Email sent.");
                        }
                        else{

                        }
                    }
                });
        // [END send_email_verification]
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup_form.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Signup_form.this, "Upload successful", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(uname, email);
        myRef.setValue(userProfile);
    }

}
