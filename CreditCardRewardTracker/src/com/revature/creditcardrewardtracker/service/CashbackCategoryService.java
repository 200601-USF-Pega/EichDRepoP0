package com.revature.creditcardrewardtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.models.CategoryCashBack;

public class CashbackCategoryService {
	
	public List<CategoryCashBack> createNewCashbackCategory(Scanner s) {
		
		//Scanner cashScan = new Scanner(System.in);
		List<CategoryCashBack> categories = new ArrayList<CategoryCashBack>();
		
		System.out.println("What are the cash back categories and their percentage back? Enter done when complete");
		
		String scanResult;
		do {
			CategoryCashBack category = new CategoryCashBack();
			
			System.out.println("What is the name of the category?");
			scanResult = s.nextLine();
			if (scanResult.equalsIgnoreCase("done")) {
				break;
			}
			category.setCategoryOfCashBack(scanResult);
			
			System.out.println("What is the cash back rate?");
			System.out.println("Please provide the cash back rate in 0.00 format (i.e. 5% would be 0.05).");
			category.setPercentageOfCashBack(s.nextDouble());
			s.nextLine();
			
			categories.add(category);
			
		} while (!scanResult.equalsIgnoreCase("done"));
		
		return categories;
		
	}

}
