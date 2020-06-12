package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.CreditCardRepoDB;
import com.revature.creditcardrewardtracker.dao.ICreditCardRepo;
import com.revature.creditcardrewardtracker.models.CreditCard;

public class CreditCardService {
	
	String username;
	Connection connection;
	ICreditCardRepo d;
	
	public CreditCardService(String username, Connection connection) {
		this.username = username;
		this.connection = connection;
		d = new CreditCardRepoDB(connection);
	}
	
	public CreditCard createNewCreditCard(Scanner input) {
		
		CashbackCategoryService cashback = new CashbackCategoryService();
		
		CreditCard card = new CreditCard();
		
		try {
			input.nextLine();
			System.out.println("What is the name of the credit card?");
			String creditCardName = input.nextLine();
			card.setCreditCardName(creditCardName);
			
			System.out.println("What are the last 4 digits of the credit card?");
			int cardID = Integer.parseInt(input.nextLine());
			card.setCreditCardID(cardID);
			
			card.setCardCashBackCategories(cashback.createNewCashbackCategory(input));
			
			card = d.addCreditCard(username, card);
					
			return card;
		} catch (Exception e) {
			System.out.println("Invalid input type.");
		} 
		
		return card;
		
		
	}
	
	public List<CreditCard> getCreditCards() {
		List<CreditCard> cardsOnFile;
		cardsOnFile = d.getCreditCards(username);
		return cardsOnFile;
	}
	

}
