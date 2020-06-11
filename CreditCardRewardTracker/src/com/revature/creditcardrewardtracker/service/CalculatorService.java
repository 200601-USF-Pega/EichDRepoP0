package com.revature.creditcardrewardtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.models.CategoryCashBack;
import com.revature.creditcardrewardtracker.models.CreditCard;

public class CalculatorService {
	
	ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
	
	//this method is to simulate having cards in a DB for testing purposes, will be removed once DB added
	private void addCards() {
		cards.add(new CreditCard("Amex Gold", 3000));
		List<CategoryCashBack> categories = new ArrayList<CategoryCashBack>();
		categories.add(new CategoryCashBack("Dining", 0.04));
		categories.add(new CategoryCashBack("Groceries", 0.04));
		cards.get(0).setCardCashBackCategories(categories);
		
		cards.add(new CreditCard("Chase Freedom Unlimited", 3333));
		List<CategoryCashBack> categories2 = new ArrayList<CategoryCashBack>();
		categories2.add(new CategoryCashBack("Everything", 0.03));
		cards.get(1).setCardCashBackCategories(categories2);
		
		cards.add(new CreditCard("BofA Cash Rewards", 2100));
		List<CategoryCashBack> categories3 = new ArrayList<CategoryCashBack>();
		categories3.add(new CategoryCashBack("Online", 0.03));
		categories3.add(new CategoryCashBack("Everything", 0.01));
		categories3.add(new CategoryCashBack("Groceries", 0.02));
		cards.get(2).setCardCashBackCategories(categories3);
		
	}
	
	public void selectBestCard(Scanner sc) {
		this.addCards();
		System.out.println("Please enter the category of the purchase.");
		String category = sc.next();
		
		String bestCard = null;
		double bestRate = 0.00;
		
		for (int i = 0; cards.size() > i; i++) {
			CreditCard tempCard = cards.get(i);
			for (int j = 0; tempCard.getCardCashBackCategories().size() > j; j++) {
				List<CategoryCashBack> tempCategoriesList = tempCard.getCardCashBackCategories();
				if (tempCategoriesList.get(j).getCategoryOfCashBack().equalsIgnoreCase(category)) {
					double tempRate = tempCategoriesList.get(j).getPercentageOfCashBack();
					if (tempRate > bestRate) {
						bestRate = tempRate;
						bestCard = tempCard.getCreditCardName();
					}
				}
			}
		}
		if (bestRate > 0.00) {
			System.out.println("The best card for " + category + " is " + bestCard + " at the rate of " + (bestRate*100) + "%.");
		} else {
			System.out.println("No cards were found with a cashback category of " + category + ".");
		}
		
	}
	
	public void calculatePercentageBack(Scanner sc) {
		this.addCards();
		System.out.println("What are the last 4 digits of the card you plan on using?");
		int cardNumber = sc.nextInt();
		
		for (int i = 0; cards.size() > i; i++) {
			CreditCard tempCard = cards.get(i);
			if (tempCard.getCreditCardID() == cardNumber) {
				System.out.println("What is the category of the purchase?");
				String category = sc.next();
				
				double bestRate = 0.00;
				for (int j = 0; tempCard.getCardCashBackCategories().size() > j; j++) {
					List<CategoryCashBack> tempCategoriesList = tempCard.getCardCashBackCategories();
					if (tempCategoriesList.get(j).getCategoryOfCashBack().equalsIgnoreCase(category)) {
						double tempRate = tempCategoriesList.get(j).getPercentageOfCashBack();
						if (tempRate > bestRate) {
							bestRate = tempRate;
						}
					}
				}
				
				System.out.println("How much do you plan on spending?");
				double total = sc.nextDouble();
				double savings = total*bestRate;
				double adjustedPrice = total - savings;
				
				System.out.println("Using your " + tempCard.getCreditCardName() + " card will save you $" + savings + " for an adjusted total of $" + adjustedPrice + ".");
				
			}
		}
	}

}
