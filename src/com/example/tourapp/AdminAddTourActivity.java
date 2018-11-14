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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.content.Intent;

public class AdminAddTourActivity extends Activity {
	
	public static final String EXTRA_EMAIL = "email";
	String email;

	ArrayAdapter<String> adapter;
	ArrayList<String> spinnerValuesList = new ArrayList<String>();
	String[] spinnerValuesArray;

	
	EditText tourNameEdit, tourDayEdit, tourMonthEdit;
	EditText tourYearEdit, tourDescriptionEdit;
	
	private static android.widget.Spinner spinner;
	
	//private String name, day, month, year, description, guide;
	
	
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_tour);
		
		email = (String) getIntent().getExtras().get(EXTRA_EMAIL);
		
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
	
	public void onAddTourClick(View view) {
		new AddTourTask().execute();
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
	
	
/*	public void onAddTourClick(View view) {
		new AddTourTask().execute();
	}*/

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
	
	
	private class AddTourTask extends AsyncTask<Void, Void, Void> {
		
		private String name = tourNameEdit.getText().toString();
		private String day = tourDayEdit.getText().toString();
		private String month = tourMonthEdit.getText().toString();
		private String year = tourYearEdit.getText().toString();
		private String description = tourDescriptionEdit.getText().toString();
		private String guide = (String) spinner.getSelectedItem().toString();

		
		@Override
		protected Void doInBackground(Void... voids) {
			try {
				String urlAdress = "http://10.0.2.2/inz/adminTourAdd.php/?string=" + name + "/" + year
						+ "-" + month + "-" + day + "/" + description + "/" + guide;
				URL url = new URL(urlAdress);
				url.openStream();
			} catch (IOException e) {}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {

			/*Toast toast = Toast.makeText(this, "Added new tour", Toast.LENGTH_SHORT);
	        toast.show();*/
			Intent intent = new Intent(AdminAddTourActivity.this, AdminActivity.class);
			intent.putExtra(AdminActivity.EXTRA_EMAIL, (String) email);
	        startActivity(intent);
		}
	}
}
