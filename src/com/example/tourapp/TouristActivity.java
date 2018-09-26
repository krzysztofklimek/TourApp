package com.example.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TouristActivity extends Activity { // implements android.widget.CompoundButton.OnCheckedChangeListener {

	public static final String EXTRA_EMAIL = "email";
	String email;
	String tourName;
	String tourDate;
	ArrayList <Tour> tours = new ArrayList <Tour>();
	ListView list;
	ListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourist);
		
		email = (String) getIntent().getExtras().get(EXTRA_EMAIL);
		
		createList();	
	
	}
	
	
	private void createList(){
		
		new NamesFill().execute();
		
		adapter = new TouristListAdapter(TouristActivity.this, tours);
		list = (ListView) findViewById(R.id.listViewTourist);
		list.setAdapter(adapter);	
	}
	
	public void onCheckBoxClick(View view){
			
		CheckBox checkBox = (CheckBox) view;
		String str = ((TextView)view).getText().toString();
		Scanner sc = new Scanner(str).useDelimiter("\n");
		tourName = sc.next().toLowerCase();// + " " + sc.next().toLowerCase();
		tourName = tourName.replace(" ", "%20");
		//tourName = sc.next().toLowerCase() + "%20" + sc.next().toLowerCase();
		tourDate = sc.next();
		sc.close();
		
		
		if(checkBox.isChecked()){
			new TourChooseResign().execute();
			Toast toast = Toast.makeText(this, "Wybra³eœ:\n" + str, Toast.LENGTH_SHORT);
			toast.show();
		}
		else{
			new TourChooseResign().execute();
			Toast toast = Toast.makeText(this, "Zrezygnowa³eœ:\n" + str, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	
	private class NamesFill extends AsyncTask<Void, Void, Void> {
		

		@Override
		protected Void doInBackground(Void... voids) {

			try {
				String result = "";
				String urlAdress = "http://10.0.2.2/inz/touristTourList.php/?string=" + email;
				URL url = new URL(urlAdress);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((result = in.readLine()) != null) {
					Scanner sc = new Scanner(result).useDelimiter("/");
					while(sc.hasNext()){
						String name = sc.next();
						String date = sc.next();
						String description = sc.next();
						String checkbox = sc.next();
						tours.add(new Tour(name.toUpperCase(), date, description, checkbox));
					}
					sc.close();
				}
			} 
			catch (MalformedURLException e) {} 
			catch (IOException e) {}
			return null;
		}		
	}
	
	private class TourChooseResign extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				String urlAdress = "http://10.0.2.2/inz/tourChooseResign.php/?string=" + email + "/" + tourName + "/" + tourDate;
				URL url = new URL(urlAdress);
				url.openStream();
			} catch (IOException e) {}
			return null;
		}
	}
}
