package com.cyberagora.medicalhub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationSelect extends AppCompatActivity {

    private Spinner spinnerState;
    private Spinner spinnerArea;
    private static final String[] states = {"Abu Dhabi","Dubai"};
    private ArrayList<String> areas;
    private String selectedState;
    private String selectedArea;

    FirebaseFirestore db; //Reference to database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);

        db = FirebaseFirestore.getInstance();

        //dropdown list for states
        spinnerState = (Spinner)findViewById(R.id.spinnerState);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(LocationSelect.this,android.R.layout.simple_spinner_item,states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);

        //When a change is made in the dropdown
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("item", (String) parent.getItemAtPosition(position));

                selectedState=(String) parent.getItemAtPosition(position);      //the selected state is stored in a variable

                //getting the document that conatins arrays listing Areas in each state
                db.collection("country").document("uae").get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if(task.isSuccessful())
                                {
                                    //getting all the results and maping the data
                                    DocumentSnapshot document = task.getResult();
                                    Map<String, Object> map = document.getData();

                                    //looping through the map
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        //finding the selected state in the list of states
                                        if (entry.getKey().equals(selectedState)) {

                                            areas = (ArrayList<String>) entry.getValue();   //array list will contain all areas in the state

                                            //dropdown for areas
                                            spinnerArea = (Spinner)findViewById(R.id.spinnerArea);
                                            ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(LocationSelect.this, android.R.layout.simple_spinner_item,areas);
                                            areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinnerArea.setAdapter(areaAdapter);

                                            spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    //Log.v("item", (String) parent.getItemAtPosition(position));
                                                    selectedArea=(String) parent.getItemAtPosition(position);       //setting variable as the selected area
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {
                                                    // TODO Auto-generated method stub
                                                }
                                            });
                                            break;
                                        }
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void go(View view){
        Intent intent = new Intent(this,HospitalListActivity.class);
        intent.putExtra("selectedState",selectedState);
        intent.putExtra("selectedArea",selectedArea);
        startActivity(intent);
    }



}
