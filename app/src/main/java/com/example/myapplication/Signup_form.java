package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_form extends AppCompatActivity {

    TextInputEditText username,email_id,password,confirm_password ;
    Button register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        username = (TextInputEditText) findViewById(R.id.user_name);
        email_id =  (TextInputEditText) findViewById(R.id.email_id);
        password = (TextInputEditText)  findViewById(R.id.pass);
        confirm_password =  (TextInputEditText) findViewById(R.id.confirm_pass);
        register = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString().trim();
                String email = email_id.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String conf_pass = confirm_password.getText().toString().trim();

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
                if(pass.length()<8){
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
                                        startActivity(new Intent(getApplicationContext(),Login_form.class));
                                        Toast.makeText(Signup_form.this, "Registration successful", Toast.LENGTH_SHORT).show();
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


}
