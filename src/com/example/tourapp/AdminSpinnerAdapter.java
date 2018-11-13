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

class AdminSpinnerAdapter extends ArrayAdapter<Guide> {

	AdminSpinnerAdapter(Context context, ArrayList<Guide> guides) {
		super(context, R.layout.item_spinner, guides);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View customView = inflater.inflate(R.layout.item_spinner, parent, false);

		Guide guide = getItem(position);
		TextView emailText = (TextView) customView.findViewById(R.id.itemSpinner);
		emailText.setText(guide.getEmail());

		return customView;
	}

}
