package com.cyberagora.medicalhub;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
}
