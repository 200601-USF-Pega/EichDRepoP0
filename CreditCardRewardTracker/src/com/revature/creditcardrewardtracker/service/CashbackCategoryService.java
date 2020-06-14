package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.CreditCardRepoDB;
import com.revature.creditcardrewardtracker.dao.CreditCardRewardsRepoDB;
import com.revature.creditcardrewardtracker.dao.ICreditCardRepo;
import com.revature.creditcardrewardtracker.dao.ICreditCardRewardsRepo;
import com.revature.creditcardrewardtracker.models.CategoryCashBack;
import com.revature.creditcardrewardtracker.models.CreditCard;

public class CashbackCategoryService {
	
	private Scanner sc;
	private String username;
	private ICreditCardRepo d;
	private ICreditCardRewardsRepo ccrr;

	public CashbackCategoryService(String username, Connection connection, Scanner sc) {
		this.sc = sc;
		this.username = username;
		ccrr = new CreditCardRewardsRepoDB(connection);
		d = new CreditCardRepoDB(connection);
		
	}
	
	public List<CategoryCashBack> createNewCashbackCategory() {
		
		//Scanner cashScan = new Scanner(System.in);
		List<CategoryCashBack> categories = new ArrayList<CategoryCashBack>();
		
		System.out.println("What are the cash back categories and their percentage back? Enter done when complete");
		
		
		try {
			String scanResult;
			do {
				CategoryCashBack category = new CategoryCashBack();
				
				System.out.println("What is the name of the category?");
				scanResult = sc.nextLine();
				if (scanResult.equalsIgnoreCase("done")) {
					break;
				}
				category.setCategoryOfCashBack(scanResult);
				
				System.out.println("What is the cash back rate?");
				System.out.println("Please provide the cash back rate in 0.00 format (i.e. 5% would be 0.05).");
				category.setPercentageOfCashBack(sc.nextDouble());
				sc.nextLine();
				
				categories.add(category);
				
			} while (!scanResult.equalsIgnoreCase("done"));
			
			return categories;
		} catch (Exception e) {
			System.out.println("Invalid input type.");
		}
		
		return categories;
		
	}
	
	public void addCashbackCategory() {
		//CategoryCashBack category = new CategoryCashBack();
		System.out.println("Which card would you like to add a new category to? Please enter the Card ID.");
		
		List<CreditCard> cards = d.getCreditCards(username);
		for (CreditCard c : cards) {
			System.out.println(c.stringNameAndId());
		}
		
		int id = sc.nextInt();
		
		sc.nextLine();
		System.out.println("What is the name of the category?");
		String category = sc.nextLine();

		
		System.out.println("What is the cash back rate?");
		System.out.println("Please provide the cash back rate in 0.00 format (i.e. 5% would be 0.05).");
		double percentageback = sc.nextDouble();
		
		boolean result = ccrr.addCashBackCategory(id, category, percentageback);
		
		if (result == true) {
			System.out.println("Category " + category + " successfully added to card " + id + ".");
		} else {
			System.out.println("Category failed to be added.");
		}
		
	}

}
