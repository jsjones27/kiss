package com.aj3.kiss;

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
	
	public String toString() {
		return item.toString();
	}
	
	public boolean equals(ListItem li) {
		return this.getItem().equals(li.getItem());
	}
}