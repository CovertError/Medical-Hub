package com.cyberagora.medicalhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityPage extends AppCompatActivity {
    private FirebaseFirestore dbms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        dbms = FirebaseFirestore.getInstance();
    }
}
