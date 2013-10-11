package com.example.kiss;

public class Item {
	private int id;
	private String name;
	private String catagory;
	
	public Item(int id,String name, String catagory) {
		this.id = id;
		this.name = name;
		this.catagory = catagory;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	
	
	
}
