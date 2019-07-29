package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_form extends AppCompatActivity {

    Button forgot;
    TextInputEditText forgotEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_form);

        forgot = findViewById(R.id.forgot_button);
        forgotEmail = findViewById(R.id.forgot_email_id);

        firebaseAuth = FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgotEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(Forgot_form.this, "Enter the email-id", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(forgotEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Forgot_form.this, "Password reset email has been sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login_form.class));
                        }else{
                            Toast.makeText(Forgot_form.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                }

        });
    }
}
