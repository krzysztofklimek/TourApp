package com.example.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AdminAddTourActivity extends Activity {

	ArrayAdapter<String> adapter;
	ArrayList<String> spinnerValuesList = new ArrayList<String>();
	String[] spinnerValuesArray;

	
	private static EditText tourNameEdit, tourDayEdit, tourMonthEdit;
	private static EditText tourYearEdit, tourDescriptionEdit;
	private static android.widget.Spinner spinner;
	
	private String name, day, month, year, description, guide;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_tour);
		
		tourNameEdit = (EditText) findViewById(R.id.tourNameEdit);
		tourDayEdit = (EditText) findViewById(R.id.tourDayEdit);
		tourMonthEdit = (EditText) findViewById(R.id.tourMonthEdit);
		tourYearEdit = (EditText) findViewById(R.id.tourYearEdit);
		tourDescriptionEdit = (EditText) findViewById(R.id.tourDescriptionEdit);
		
		

		createSpinner();

		/*
		 * new SpinnerFill().execute();
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 * 
		 * spinnerValuesArray = new String[spinnerValuesList.size()];
		 * 
		 * for (int i = 0; i < spinnerValuesList.size(); i++) {
		 * spinnerValuesArray[i] = spinnerValuesList.get(i); }
		 * 
		 * adapter = new ArrayAdapter<String>(this, R.layout.item_spinner,
		 * R.id.itemSpinner, spinnerValuesArray); spinner = (Spinner)
		 * findViewById(R.id.spinner); spinner.setAdapter(adapter);
		 */

	}

	private void createSpinner() {

		new SpinnerFill().execute();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		spinnerValuesArray = new String[spinnerValuesList.size()];
		for (int i = 0; i < spinnerValuesList.size(); i++) {
			spinnerValuesArray[i] = spinnerValuesList.get(i);
		}

		adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.itemSpinner, spinnerValuesArray);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setAdapter(adapter);

	}
	
	
	public void onAddTourClick(){
		name = tourNameEdit.getText().toString();
		day = tourDayEdit.getText().toString();
		month = tourMonthEdit.getText().toString();
		year = tourYearEdit.getText().toString();
		description = tourDescriptionEdit.getText().toString();
		guide = (String) spinner.getSelectedItem().toString();
		
	}

	private class SpinnerFill extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {

			try {
				String result = "";
				String urlAdress = "http://10.0.2.2/inz/spinnerGuides.php";
				URL url = new URL(urlAdress);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((result = in.readLine()) != null) {
					Scanner sc = new Scanner(result).useDelimiter("/");
					while (sc.hasNext()) {

						spinnerValuesList.add(sc.next());

					}
					sc.close();
				}
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
			return null;
		}

	}
}
