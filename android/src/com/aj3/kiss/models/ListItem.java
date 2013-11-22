package com.aj3.kiss.models;

import android.app.Activity;

import com.aj3.kiss.helpers.DatabaseHelper;

/*
 * Represents an entry in some list.
 * 
 * An Item has some quantity associated with it: together these form the ListItem.
 */
public class ListItem {
	private Item item;
	private double quantity;
	
	public ListItem(Item item, double quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public ListItem() {}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double updateQuantity(double quantity) {
		double newQuantity = this.getQuantity() + quantity;
		if (newQuantity < 0) {
			// Throw exception here
		}
		this.setQuantity(newQuantity);
		return newQuantity;
	}
	
	public String toString() {
		return getItem().toString();
	}
	
	public boolean equals(ListItem li) {
		return this.getItem().equals(li.getItem());
	}
}