package com.aj3.kiss.models;

/*
 * Represents a single category.
 * 
 * Items can be classified into categories.
 */
public class Category {
	private int id;
	private String name;
	
	public Category(int id) {
		this.id = id;
	}
	
	public Category() {}

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
