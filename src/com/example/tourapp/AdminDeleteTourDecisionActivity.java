package com.example.tourapp;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminDeleteTourDecisionActivity extends Activity {

	public static final String EXTRA_EMAIL = "email";
	String email;
	String tourName, tourDate;
	TextView tourNameText, tourDateText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_delete_tour_decision);

		String message = (String) getIntent().getExtras().get(EXTRA_EMAIL);
		divideMessage(message);

		tourNameText = (TextView) findViewById(R.id.tourNameText);
		tourDateText = (TextView) findViewById(R.id.tourDateText);

		tourNameText.setText(tourName);
		tourDateText.setText(tourDate);

	}

	protected void divideMessage(String str) {
		Scanner sc = new Scanner(str).useDelimiter(",");
		email = sc.next();
		tourName = sc.next();
		tourDate = sc.next();
		sc.close();
	}

	public void onDeleteClick(View view) {
		new DeleteTourTask().execute();
	}

	private class DeleteTourTask extends AsyncTask<Void, Void, Void> {
		
		
		String finalName = tourName.toLowerCase().replace(" ", "%20");

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				String urlAdress = "http://10.0.2.2/inz/adminTourDelete.php/?string=" + finalName + "/" + tourDate;
				URL url = new URL(urlAdress);
				url.openStream();
			} catch (IOException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void voids) {

			Toast toast = Toast.makeText(AdminDeleteTourDecisionActivity.this, "Deleted: " + tourName,
					Toast.LENGTH_SHORT);
			toast.show();
			Intent intent = new Intent(AdminDeleteTourDecisionActivity.this, AdminActivity.class);
			intent.putExtra(AdminActivity.EXTRA_EMAIL, (String) email);
			startActivity(intent);
		}
	}

}
