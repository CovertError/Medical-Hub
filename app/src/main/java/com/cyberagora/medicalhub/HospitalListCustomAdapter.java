package com.cyberagora.medicalhub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class HospitalListCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> hospitalNameList;
    ArrayList<Double> hospitalRatingList;
    LayoutInflater inflter;

    public HospitalListCustomAdapter(Context context, ArrayList<String> hospitalNameList, ArrayList<Double> hospitalRatingList)
    {
        this.context=context;
        this.hospitalNameList= hospitalNameList;
        this.hospitalRatingList=hospitalRatingList;

        inflter = (LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return hospitalNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void add(String hospitalName, double hospitalRating)
    {
        hospitalNameList.add(hospitalName);
        hospitalRatingList.add(hospitalRating);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflter.inflate(R.layout.activity_hospital_list_view, null);
            TextView hospitalName = (TextView) convertView.findViewById(R.id.hospitalName);
            TextView hospitalRating = (TextView) convertView.findViewById(R.id.hospitalRating);

            hospitalName.setText(hospitalNameList.get(position));
            hospitalRating.setText(hospitalRatingList.get(position).toString());
        }
        return convertView;
    }
}
