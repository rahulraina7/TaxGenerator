package com.yojee.model;

import java.math.BigDecimal;

public class Item {
	
	public enum Itemtype {
	    BOOKS,
	    FOOD,
	    MEDICAL,
	    OTHERS;
	}

	String name;
	int quantity;
	BigDecimal price;
	BigDecimal netPrice = new BigDecimal("0");
	Itemtype itemType = Itemtype.OTHERS; 
	boolean imported = false;

	
	public int getQuantity() {
		return quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public Itemtype getItemType() {
		return itemType;
	}

	public boolean isImported() {
		return imported;
	}

	public Item(int quantity, String name, BigDecimal price){
		this.quantity = quantity;
		this.name = name;
		this.price = price;
		
		if(name.indexOf("imported") > -1){
			this.imported = true;
		}
		
		if(name.indexOf("chocolate") > -1 || name.indexOf("food") > -1){
			this.itemType = Itemtype.FOOD;
		}else if(name.indexOf("book") > -1){
			this.itemType = Itemtype.BOOKS;
		}else if(name.indexOf("pills") > -1 || name.indexOf("medicine") > -1 || name.indexOf("medical") > -1 ){
			this.itemType = Itemtype.MEDICAL;
		}
	}
}
