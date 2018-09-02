package com.example.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	
	EditText output;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		output = (EditText) findViewById(R.id.tekst);				
	}
	
	
	private class MyTask extends AsyncTask<Void, Void, Void> {
		private String password="";
		
		@Override
		protected Void doInBackground(Void... arg0){
			String str = "bla";
			try {
				String result = "";
				URL url = new URL("http://10.0.2.2/inz/login.php");
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((result = in.readLine()) != null) {
					password = result;
				}
				
			} catch (MalformedURLException e) {
				
			} catch (IOException e) {
				
			}
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Void result){
			output.setText(password);
			super.onPostExecute(result);
		}
	}
	
	
	
	public void onLoginClick(View view) {
		new MyTask().execute();
	}	
}
