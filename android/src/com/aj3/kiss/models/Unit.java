package com.aj3.kiss.models;

public class Unit {
	private int id;
	private String name;
	
	public Unit(int id) {
		this.id = id;
	}
	
	public Unit() {}

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
}
