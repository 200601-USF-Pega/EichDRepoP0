package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.CreditCardRepoDB;
import com.revature.creditcardrewardtracker.dao.ICreditCardRepo;
import com.revature.creditcardrewardtracker.models.CreditCard;

public class CreditCardService {
	
	String username;
	Connection connection;
	ICreditCardRepo d;
	Scanner sc;
	
	public CreditCardService(String username, Connection connection, Scanner sc) {
		this.username = username;
		this.connection = connection;
		d = new CreditCardRepoDB(connection);
		this.sc = sc;
	}
	
	public CreditCard createNewCreditCard() {
		
		CashbackCategoryService cashback = new CashbackCategoryService();
		
		CreditCard card = new CreditCard();
		
		try {
			sc.nextLine();
			System.out.println("What is the name of the credit card?");
			String creditCardName = sc.nextLine();
			card.setCreditCardName(creditCardName);
			
			/*System.out.println("What are the last 4 digits of the credit card?");
			int cardID = Integer.parseInt(input.nextLine());
			card.setCreditCardID(cardID);*/
			
			card.setCardCashBackCategories(cashback.createNewCashbackCategory(sc));
			
			card = d.addCreditCard(username, card);
			
			System.out.println(card);
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
	
	public void deleteCreditCard() {
		System.out.println("Please enter the Card ID for the credit card you wish to delete.");
		
		List<CreditCard> cards = d.getCreditCards(username);
		for (CreditCard c : cards) {
			System.out.println(c.stringNameAndId());
		}
		
		int id = sc.nextInt();
		
		
		boolean result = d.deleteCard(id);
		if (result == true) {
			System.out.println(id + " deleted successfully.");
		} else {
			System.out.println(id + " not deleted.");
		}
	}
	
	
	public void updateCardName() {
		System.out.println("Please enter the Card ID for the credit card you wish to modify.");
		
		List<CreditCard> cards = d.getCreditCards(username);
		for (CreditCard c : cards) {
			System.out.println(c.stringNameAndId());
		}
		
		int id = sc.nextInt();	
		
		sc.nextLine();
		System.out.println("What would you like to rename this card?");
		String newName = sc.nextLine();
		
		d.updateCard(id, newName);
		
	}

}
