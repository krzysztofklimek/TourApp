package com.example.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//import com.example.tourapp.TouristActivity.NamesFill;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GuideActivity extends Activity {
	
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
		setContentView(R.layout.activity_guide);

		email = (String) getIntent().getExtras().get(EXTRA_EMAIL);
		createList();
	}
	
	
	private void createList(){
		
		new NamesFill().execute();
		
		adapter = new GuideListAdapter(GuideActivity.this, tours);
		list = (ListView) findViewById(R.id.listViewGuide);
		list.setAdapter(adapter);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Przygotowujemy menu; jeœli jest pasek akcji to dodajemy do niego
		// elementy.
		getMenuInflater().inflate(R.menu.guide_menu_options, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			Intent intent1 = new Intent(this, GuideActivity.class);
			intent1.putExtra(GuideActivity.EXTRA_EMAIL, (String) email);
			startActivity(intent1);
			return true;
		case R.id.action_logout:
			Intent intent3 = new Intent(this, LoginActivity.class);
			startActivity(intent3);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	private class NamesFill extends AsyncTask<Void, Void, Void> {
		

		@Override
		protected Void doInBackground(Void... voids) {

			try {
				String result = "";
				String urlAdress = "http://10.0.2.2/inz/guideTourList.php/?string=" + email;
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
	
}
