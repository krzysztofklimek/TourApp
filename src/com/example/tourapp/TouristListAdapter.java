package com.example.tourapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

class TouristListAdapter extends ArrayAdapter<Tour> {

	TouristListAdapter(Context context, ArrayList<Tour> tours) {
		super(context, R.layout.list_tourist, tours);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View customView = inflater.inflate(R.layout.list_tourist, parent, false);

		Tour tour = getItem(position);
		//TextView nameText = (TextView) customView.findViewById(R.id.tourName);
		//TextView dateText = (TextView) customView.findViewById(R.id.tourDate);
		TextView descriptionText = (TextView) customView.findViewById(R.id.tourDescription);
		CheckBox checkBox = (CheckBox) customView.findViewById(R.id.checkBox);

		//nameText.setText(tour.getName());
		//dateText.setText(tour.getDate());
		descriptionText.setText(tour.getDescription());
		checkBox.setChecked(tour.getChecked());
		checkBox.setText(tour.getName() + "\n" + tour.getDate());
		
		return customView;
	}
	
	
}
