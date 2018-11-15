package com.cyberagora.medicalhub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private EditText loginEmail;
    private EditText loginPass;

    private String displayName;
    private String email;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginF(View view)
    {
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPass = (EditText) findViewById(R.id.login_pass);

        email = loginEmail.getText().toString();
        pass = loginPass.getText().toString();

        if(!email.equals("") && pass.length() >= 5)
        {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        user = mAuth.getCurrentUser();
                        if (user != null) {
                            displayName = user.getDisplayName().toString();
                        }

                        //TODO launch the main menu activity, giving the user as a parameter
                        Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();    //Creates a popup message on screen
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();    //Creates a popup message on screen

                    }
                }
            });
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Invalid Email or Password!.", Toast.LENGTH_SHORT).show();    //Creates a popup message on screen
        }
    }
}
