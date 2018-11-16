package com.cyberagora.medicalhub;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //initialize firebase
    private FirebaseUser user;  //Reference to the user
    private FirebaseFirestore db; //Reference to database

    private EditText signUser;
    private EditText signEmail; //To get reference from the UI TextBox
    private EditText signPass;  //
    private EditText signRePass;//

    private String username;
    private String email;
    private String pass;
    private String rePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        signUser = (EditText) findViewById(R.id.sign_username);
        signEmail = (EditText) findViewById(R.id.sign_email);
        signPass = (EditText) findViewById(R.id.sign_pass);
        signRePass = (EditText) findViewById(R.id.sign_repass);

        signUser.requestFocus();    //Sets the typing cursor to the username text box
    }

    public void signUpF(View view)
    {
        username = signUser.getText().toString();
        email = signEmail.getText().toString();
        pass = signPass.getText().toString();
        rePass = signRePass.getText().toString();

        if(isValidEmail(email)) //Checks if email is valid
        {
            //TODO check if username is also different from all the usernames in database
            if ((pass.equals(rePass)) && (pass.length() >= 6))   //Criteria for password
            {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()  //Method for creating user in firebase database
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            // Set Username
                            user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            user.updateProfile(profileUpdates);

                            //Send Verification Email
                            sendVerificationEmail();

                            //Logout
                            mAuth.signOut();

                            finish();
                            //Launch Login Activity
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_LONG).show();  //Creates a popup message on screen
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Account Creation Error!", Toast.LENGTH_LONG).show();   //Creates a popup message on screen
                            }
                        }
                    }
                });
            }
            else    //Password doesn't match the given criteria
            {
                if(pass.length() < 5)   //Checks
                {
                    Toast.makeText(SignUpActivity.this, "Password should be 6 characters long or more...", Toast.LENGTH_LONG).show();   //Creates a popup message on screen
                    signPass.setText("");
                    signRePass.setText("");
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();    //Creates a popup message on screen
                    signPass.setText("");
                    signRePass.setText("");
                }
            }

        }
        else
        {
            Toast.makeText(SignUpActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();    //Creates a popup message on screen
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

    private void sendVerificationEmail()
    {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Email sent
                            Toast.makeText(SignUpActivity.this, "Verification Email Sent!", Toast.LENGTH_LONG).show();    //Creates a popup message on screen
                        }
                        else
                        {
                            //Email not sent
                            Toast.makeText(SignUpActivity.this, "Verification Email not Sent!", Toast.LENGTH_LONG).show();    //Creates a popup message on screen
                        }
                    }
                });
    }
}
