package com.revature.creditcardrewardtracker.models;

import java.util.Date;

public class Transaction {
	
	private int cardID;
	private Date date;
	private String category;
	private double total;

	public Transaction() {
		
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Transaction [cardID=" + cardID + ", date=" + date + ", category=" + category + ", total=" + total + "]";
	}


	
}
