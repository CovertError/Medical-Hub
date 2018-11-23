package com.cyberagora.medicalhub;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //Check if the user is already logged in
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null) //Checks if the user is already logged in
        {
            Toast.makeText(MainActivity.this, "Already logged in!", Toast.LENGTH_LONG).show();  //Creates a popup message on screen
            //TODO launch main menu activity
        }
    }

    public void login(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void guest(View view)
    {
        //TODO launch main menu as no user
        Intent intent = new Intent(this, LocationSelect.class);
        startActivity(intent);
    }
}
