package com.revature.creditcardrewardtracker.models;

import java.util.List;

import com.revature.creditcardrewardtracker.exceptions.InvalidCreditCardNumberException;
import com.revature.creditcardrewardtracker.exceptions.InvalidNameException;

public class CreditCard  {
	
	//the credit card object contains the unique ID, name, and an ArrayList of CategoryCashBack objects
	
	private String creditCardName;
	private int creditCardID;
	private List<CategoryCashBack> cardCashBackCategories;
	
	public CreditCard() {
		
	}
	
	public CreditCard(String creditCardName, int creditCardID) {
		this.setCreditCardName(creditCardName);
		this.setCreditCardID(creditCardID);
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		//technically should check database for no other matching ID, 
		//but I don't think it would break it
		char[] chars = creditCardName.toCharArray();
		for (char c : chars) {
			if(Character.isDigit(c)) {
				throw new InvalidNameException();
			}
		}
		this.creditCardName = creditCardName;
	}

	public int getCreditCardID() {
		return creditCardID;
	}

	public void setCreditCardID(int creditCardID) {
		//needs to check the database that no other card has same ID
		Integer id = (Integer) creditCardID;
		if (id.toString().length() != 4) {
			throw new InvalidCreditCardNumberException();
		}
		this.creditCardID = creditCardID;
	}

	public List<CategoryCashBack> getCardCashBackCategories() {
		return cardCashBackCategories;
	}

	public void setCardCashBackCategories(List<CategoryCashBack> cardCashBackCategories) {
		this.cardCashBackCategories = cardCashBackCategories;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardName=" + creditCardName + ", creditCardID=" +  creditCardID
				+ ", cardCashBackCategories=" + cardCashBackCategories + "]";
	}

}
