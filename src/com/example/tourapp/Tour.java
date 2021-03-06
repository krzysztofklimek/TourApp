package com.example.tourapp;

import java.util.Scanner;

public class Tour {

	private String name;
	private String date;
	private String description;
	private boolean checked;
	private int value;
	
	private String guideName;
	private String guideSurname;

	public Tour(String name, String date, String description, String checked) {
		this.name = name;
		this.date = date;
		this.description = description;
		if(checked.equals("true"))
			this.checked = true;
		else
			this.checked = false;
		
		Scanner sc = new Scanner(date).useDelimiter("-");
		int year = Integer.parseInt(sc.next());
		int month = Integer.parseInt(sc.next());
		int day = Integer.parseInt(sc.next());
		sc.close();
		
		this.value = year*10000 + month*100 + day;
		
		
	}
	
	public Tour(String name, String date, String description, String checked, String guideName, String guideSurname) {
		this.name = name;
		this.date = date;
		this.description = description;
		if(checked.equals("true"))
			this.checked = true;
		else
			this.checked = false;
		
		this.guideName = guideName;
		this.guideSurname = guideSurname;
		
		
		Scanner sc = new Scanner(date).useDelimiter("-");
		int year = Integer.parseInt(sc.next());
		int month = Integer.parseInt(sc.next());
		int day = Integer.parseInt(sc.next());
		sc.close();
		
		this.value = year*10000 + month*100 + day;
		
		
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
	
	public void setGuideName(String name){
		guideName = name;
	}
	
	public void setGuideSurname(String surname){
		guideSurname = surname;
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
	
	public int getValue(){
		return value;
	}
	
	public String getGuideName(){
		return guideName;
	}
	
	public String getGuideSurname(){
		return guideSurname;
	}
	
}



