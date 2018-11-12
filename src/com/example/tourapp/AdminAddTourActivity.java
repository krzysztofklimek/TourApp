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
import android.widget.Spinner;

public class AdminAddTourActivity extends Activity {

	private ArrayAdapter<String> adapter;

	
	private ArrayList<String> spinnerValues = new ArrayList<String>();
	//private List <String> valuesToSpinnerArrayList = new ArrayList<String>();;
	private static android.widget.Spinner spinner;
	List<String> arl = new ArrayList<String>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_tour);

		spinner = (Spinner) findViewById(R.id.spinner);
		
		//List<String> arl = new ArrayList<String>();
		arl.add("qq");
		arl.add("ww");
		new SpinnerFill().execute();
		
		//spinnerValues = new ArrayList<String>(arl);
		for(String x : arl){
			spinnerValues.add(x);
		}
		//valuesToSpinnerArrayList = new ArrayList<String>();
		//new SpinnerFill().execute();
		//spinnerValues = new ArrayList<String>(valuesToSpinnerArrayList);
		adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.itemSpinner, spinnerValues);
		spinner.setAdapter(adapter);

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
					while(sc.hasNext()){
						arl.add(sc.next());
						//valuesToSpinnerArrayList.add(sc.next());
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
