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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //initialize firebase
    private FirebaseUser user;  //Reference to the user

    private EditText signEmail; //To get reference from the UI TextBox
    private EditText signPass;  //
    private EditText signRePass;//

    private String email;
    private String pass;
    private String rePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
    }

    private void signUpF(View view)
    {
        signEmail = (EditText) findViewById(R.id.sign_email);
        signPass = (EditText) findViewById(R.id.sign_pass);
        signRePass = (EditText) findViewById(R.id.sign_repass);

        email = signEmail.getText().toString();
        pass = signPass.getText().toString();
        rePass = signRePass.getText().toString();

        if(isValidEmail(email) == true) //Checks if email is valid
        {
            //TODO add to check if username is also different from all the usernames in database
            if (pass == rePass && pass.length() >= 5)   //Criteria for email
            {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()  //Method for creating user in firebase database
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();   //This is basically a pop up bubble that prints on screen

                            //TODO launch main menu activity after sign up with the user as parameter

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else    //Password doesn't match the given criteria
            {
                if(pass.length() < 5)   //Checks
                {
                    Toast.makeText(SignUpActivity.this, "Password should be 5 characters long or more...", Toast.LENGTH_LONG).show();
                    signPass.setText("");
                    signRePass.setText("");
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();
                    signPass.setText("");
                    signRePass.setText("");
                }
            }

        }
        else
        {
            Toast.makeText(SignUpActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
            signEmail.setText("");
        }
    }

    //This method validates email address
    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailValid = new InternetAddress(email);
            emailValid.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
