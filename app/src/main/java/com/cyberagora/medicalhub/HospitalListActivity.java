package com.cyberagora.medicalhub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HospitalListActivity extends AppCompatActivity {

    ListView hospitalListView;
    ArrayList<String> hospitalNameList;
    ArrayList<Double> hospitalRatingList;
    String state;
    String area;

    FirebaseFirestore db; //Reference to database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        db = FirebaseFirestore.getInstance();

        state = getIntent().getStringExtra("selectedState");
        area = getIntent().getStringExtra("selectedArea");

        Toast.makeText(HospitalListActivity.this , state +" "+ area,Toast.LENGTH_SHORT).show();

        hospitalNameList = new ArrayList<String>();
        hospitalRatingList = new ArrayList<Double>();

        hospitalListView = (ListView) findViewById(R.id.hospitalListView);
        final HospitalListCustomAdapter hospitalListCustomAdapter = new HospitalListCustomAdapter(getApplicationContext(),hospitalNameList,hospitalRatingList);
        hospitalListView.setAdapter(hospitalListCustomAdapter);

        db.collection("hospital").whereEqualTo("state",state).whereEqualTo("area",area).orderBy("overall_rating")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            hospitalListCustomAdapter.add(document.get("name").toString(),(Double)document.get("overall_rating"));
                        }
                    }
                    else{
                        Toast.makeText(HospitalListActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}
