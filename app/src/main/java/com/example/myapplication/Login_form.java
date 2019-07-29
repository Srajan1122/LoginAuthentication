package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_form extends AppCompatActivity {

    EditText email ,password;
    Button login,forgot_button;
    private FirebaseAuth firebaseauth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        email = (EditText) findViewById(R.id.login_email_id);
        password = (EditText) findViewById(R.id.login_pass);
        login = (Button) findViewById(R.id.login_button);
        forgot_button = findViewById(R.id.forgot_button);

        firebaseauth = FirebaseAuth.getInstance();

        forgot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Forgot_form.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = email.getText().toString().trim();
                String login_pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(email_id)){
                    Toast.makeText(Login_form.this, "Email-id is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(login_pass)){
                    Toast.makeText(Login_form.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseauth.signInWithEmailAndPassword(email_id, login_pass)
                        .addOnCompleteListener(Login_form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(firebaseauth.getCurrentUser().isEmailVerified()){
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        Toast.makeText(Login_form.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Login_form.this, "PLease verify your email", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(Login_form.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });

    }

    public void register_text(View view){
        startActivity(new Intent(getApplicationContext(),Signup_form.class));
    }

}
