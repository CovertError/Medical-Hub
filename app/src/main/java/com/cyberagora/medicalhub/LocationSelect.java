package com.cyberagora.medicalhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LocationSelect extends AppCompatActivity {

    private Spinner spinnerCountry;
    private Spinner spinnerState;
    private Spinner spinnerArea;
    private static final String[] countries = {"item 1", "item 2", "item 3"};
    private static final String[] states = {"item 1", "item 2", "item 3"};
    private static final String[] areas = {"item 1","item 2", "item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);

        spinnerCountry = (Spinner)findViewById(R.id.spinnerCountry);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(LocationSelect.this,
                android.R.layout.simple_spinner_item,countries);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter1);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerState = (Spinner)findViewById(R.id.spinnerState);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(LocationSelect.this,
                android.R.layout.simple_spinner_item,states);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter2);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerArea = (Spinner)findViewById(R.id.spinnerArea);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(LocationSelect.this,
                android.R.layout.simple_spinner_item,areas);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapter3);
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


}
