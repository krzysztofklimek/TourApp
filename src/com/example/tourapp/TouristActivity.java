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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TouristActivity extends Activity {

	public static final String EXTRA_EMAIL = "email";
	String email;
	ArrayList <Tour> tours = new ArrayList <Tour>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourist);
		
		email = (String) getIntent().getExtras().get(EXTRA_EMAIL);
		
		createList();
	}
	
	
	private void createList(){
		
		new NamesFill().execute();
		
		ListAdapter adapter = new TouristListAdapter(TouristActivity.this, tours);
		
		ListView list = (ListView) findViewById(R.id.listViewTourist);
		list.setAdapter(adapter);
	
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
					//tourNames.add(result);
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
}
