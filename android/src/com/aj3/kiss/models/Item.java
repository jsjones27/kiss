package com.aj3.kiss.models;

/*
 * Represents a single Item.
 * 
 * An item is a type of product. It belongs to a certain category and is measured by a certain unit.
 * It can have an associated UPC code.
 */
public class Item {
	public static final int NO_ID = -1;
	public static final double INITIAL_QUANTITY = 2;	// initial quantity of Item when added to grocery list
	public static final double THRESHOLD_QUANTITY = 1;	// minimum quantity of Item before adding to grocery list
	
	private int id;
	private String name;
	private Category category;
	private Unit unit;
	private String upc; 
	
	public Item() {
		this.id = NO_ID;
	}
	
	public Item(int id) {
		this.id = id;
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
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public String getUpc() {
		return upc;
	}
	
	public void setUpc(String upc) {
		this.upc = upc;
	}	
	
	public String toString() {
		return this.getName();
	}
	
	public boolean equals(Item item) {
		return this.getName().equals(item.getName());
	}
}
