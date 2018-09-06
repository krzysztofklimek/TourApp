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
	EditText login, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		login = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.password);
	}

	public void onLoginClick(View view) {
		new MyTask().execute();
	}

	private class MyTask extends AsyncTask<Void, Void, Void> {
		private String type = "";
		private String log = login.getText().toString();
		private String pass = password.getText().toString();

		@Override
		protected Void doInBackground(Void... voids) {

			try {
				String result = "";
				String urlAdress = "http://10.0.2.2/inz/login.php/?string=" + log + "/" + pass;
				URL url = new URL(urlAdress);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((result = in.readLine()) != null) {
					type = result;
				}

			} catch (MalformedURLException e) {

			} catch (IOException e) {

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void voids) {

			if (type.equals("t")) {
				Intent intent = new Intent(LoginActivity.this, TouristActivity.class);
				startActivity(intent);
			} else if (type.equals("g")) {
				Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		}
	}

}
