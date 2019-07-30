package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity<firebase> extends AppCompatActivity {

    private FirebaseAuth firebase ;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.menuLogout :{
                 firebase.getInstance().signOut();
                 finish();
                 startActivity(new Intent(getApplicationContext(), Login_form.class));}

             case R.id.profileMenu:
                 startActivity(new Intent(MainActivity.this, ProfileActivity.class));
         }


        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.logout);

    }
}
