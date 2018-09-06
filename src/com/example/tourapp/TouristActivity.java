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
import android.widget.ListView;

public class TouristActivity extends Activity {
	
	ArrayList <String> tourNames = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourist);
		
		createList();
	}
	
	
	private void createList(){
		
		new NamesFill().execute();
		
		
		
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				TouristActivity.this,
				R.layout.list_tourist,
				tourNames);*/
		
		
		String[] myItems = {"qwe","asd","dfg","gg","xxx"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				TouristActivity.this,
				R.layout.test,
				R.id.value,
				tourNames);
		
		ArrayAdapter<String> adapternext = new ArrayAdapter<String>(
				TouristActivity.this,
				R.layout.test,
				R.id.valuenext,
				myItems);
		
		ListView list = (ListView) findViewById(R.id.listViewTourist);
		list.setAdapter(adapter);
		//list.setAdapter(adapternext);
		
	}
	
	
	private class NamesFill extends AsyncTask<Void, Void, Void> {
		

		@Override
		protected Void doInBackground(Void... voids) {

			try {
				String result = "";
				String urlAdress = "http://10.0.2.2/inz/touristTourList.php";
				URL url = new URL(urlAdress);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((result = in.readLine()) != null) {
					//tourNames.add(result);
					Scanner sc = new Scanner(result).useDelimiter("/");
					while(sc.hasNext()){
						String str = sc.next();
						tourNames.add(str);
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
