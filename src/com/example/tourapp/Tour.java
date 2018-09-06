package com.example.tourapp;

public class Tour {

	private String name;
	private String date;
	private String description;
	private boolean checked;

	public Tour(String name, String date, String description, String checked) {
		this.name = name;
		this.date = date;
		this.description = description;
		if(checked.equals("true"))
			this.checked = true;
		else
			this.checked = false;
		
	}

	public void setName(String n) {
		name = n;
	}

	public void setDate(String da) {
		date = da;
	}

	public void setDescription(String de) {
		description = de;
	}
	
	public void setChecked(boolean b){
		checked = b;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean getChecked(){
		return checked;
	}
}
