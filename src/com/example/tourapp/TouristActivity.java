package com.example.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class TouristActivity extends Activity { // implements
												// android.widget.CompoundButton.OnCheckedChangeListener
												// {

	public static final String EXTRA_EMAIL = "email";
	String email;
	String tourName;
	String tourDate;
	ArrayList<Tour> tours = new ArrayList<Tour>();
	ListView list;
	ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourist);

		email = (String) getIntent().getExtras().get(EXTRA_EMAIL);

		createList();

	}

	private void createList() {

		new NamesFill().execute();

		adapter = new TouristListAdapter(TouristActivity.this, tours);
		list = (ListView) findViewById(R.id.listViewTourist);
		list.setAdapter(adapter);
	}

	private ArrayList<Tour> orderTours(ArrayList<Tour> arg) {

		Tour tour;
		boolean loop = true;
		int repeat;

		do {
			repeat = 0;
			for (int i = 0; i < arg.size() - 1; i++) {
				if (arg.get(i).getValue() > arg.get(i + 1).getValue()) {
					tour = arg.get(i);
					arg.add(i, arg.get(i + 1));
					arg.add(i + 1, tour);
					repeat++;
				}
			}
		} while (repeat != 0);

		return arg;
	}

	public void onCheckBoxClick(View view) {

		CheckBox checkBox = (CheckBox) view;
		String str = ((TextView) view).getText().toString();
		Scanner sc = new Scanner(str).useDelimiter("\n");
		tourName = sc.next().toLowerCase();
		tourName = tourName.replace(" ", "%20");
		tourDate = sc.next();
		sc.close();

		if (checkBox.isChecked()) {
			new TourChooseResign().execute();
			Toast toast = Toast.makeText(this, "Wybra³eœ:\n" + str, Toast.LENGTH_SHORT);
			toast.show();
		} else {
			new TourChooseResign().execute();
			Toast toast = Toast.makeText(this, "Zrezygnowa³eœ:\n" + str, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Przygotowujemy menu; jeœli jest pasek akcji to dodajemy do niego
		// elementy.
		getMenuInflater().inflate(R.menu.tourist_menu_options, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_tourist_tours:
			Intent intent1 = new Intent(this, TouristChoosenToursActivity.class);
			intent1.putExtra(AdminActivity.EXTRA_EMAIL, (String) email);
			startActivity(intent1);
			return true;
		case R.id.action_refresh:
			Intent intent2 = new Intent(this, TouristActivity.class);
			intent2.putExtra(TouristActivity.EXTRA_EMAIL, (String) email);
			startActivity(intent2);
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

	/*	private String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();
		}

		public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
			InputStream is = new URL(url).openStream();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
				return json;
			} finally {
				is.close();
			}
		}*/

		@Override
		protected Void doInBackground(Void... voids) {

			JSONObject json;
			InputStream is = null;

			try {
				String url = "http://10.0.2.2/inz/touristTourList.php/?string=" + email;

				is = new URL(url).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				
				
				StringBuilder sb = new StringBuilder();
				int cp;
				while ((cp = rd.read()) != -1) {
					sb.append((char) cp);
				}
				
				String jsonText = sb.toString();
				
				
				json = new JSONObject(jsonText);

				JSONArray jsonArray = json.getJSONArray("tours");

				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println(i);

					JSONObject jsonTour = jsonArray.getJSONObject(i);

					String name = (String) jsonTour.get("tourName");
					String date = (String) jsonTour.get("tourDate");
					String description = (String) jsonTour.get("tourDesc");
					String checkbox = (String) jsonTour.get("tourBool");
					tours.add(new Tour(name.toUpperCase(), date, description, checkbox));
				}
				is.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		/*
		 * @Override protected Void doInBackground(Void... voids) {
		 * 
		 * try { String result = ""; String urlAdress =
		 * "http://10.0.2.2/inz/touristTourList.php/?string=" + email; URL url =
		 * new URL(urlAdress); BufferedReader in = new BufferedReader(new
		 * InputStreamReader(url.openStream())); while ((result = in.readLine())
		 * != null) { Scanner sc = new Scanner(result).useDelimiter("/");
		 * while(sc.hasNext()){ String name = sc.next(); String date =
		 * sc.next(); String description = sc.next(); String checkbox =
		 * sc.next(); tours.add(new Tour(name.toUpperCase(), date, description,
		 * checkbox)); } sc.close(); } } catch (MalformedURLException e) {}
		 * catch (IOException e) {} return null; }
		 */

	}

	private class TourChooseResign extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				String urlAdress = "http://10.0.2.2/inz/tourChooseResign.php/?string=" + email + "/" + tourName + "/"
						+ tourDate;
				URL url = new URL(urlAdress);
				url.openStream();
			} catch (IOException e) {
			}
			return null;
		}
	}
}
