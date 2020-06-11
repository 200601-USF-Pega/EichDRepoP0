package com.revature.creditcardrewardtracker.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.service.TransactionService;

public class TransactionHistoryMenu implements IUserMenu {
	
	@Override
	public void menu(Scanner sc, String username, Connection connection) {
		
		/**view transaction history
		launches sub menu that lets you: 
			- view all transaction records
			- pull total spent in a certain category
			- pull total spent for a certain time period
			- pull total spent for a certain credit card
			- pull total rewards earned for certain category 
			- pull total rewards for a certain time period
			- pull total rewards for a certain credit card
		**/
		
		//Scanner sc = new Scanner(System.in);
		listMenuOptions();
		
		TransactionService service = new TransactionService(username, connection);
		
		int menuOption = sc.nextInt();
		
		boolean isMenuActive = true;
		
		while (isMenuActive == true) {			
			switch (menuOption) {
			case (0) :
				//view all transaction records
				System.out.println("Pulling All Transaction Records");
				System.out.println(service.getUserTransactions());
				break;
			case (1) :
				//calc total spent in a certain category
				System.out.println(service.getTotalForCategories(sc));
				break;
			case (2) :
				//calc total spent on a specific cc
				System.out.println(service.getTotalForCard(sc));
				break;
			case (3) :
				//calc total spent for date range
				System.out.println("What is the start date for your range? Please use MMDDYY for the date format.");
				int startDate = sc.nextInt();
				System.out.println("What is the end date for your range? Please use MMDDYY for the date format.");
				int endDate = sc.nextInt();
				System.out.println("Calculating Total Spent Between " + startDate + " and " + endDate);
				break;
			case (4) :
				// calc total cash back
				System.out.println("Calculating Total Cash Back");
				break;
			case (5) :
				//calc total cash back for category
				System.out.println("What category?");
				String cashBackCategory = sc.nextLine();
				System.out.println("Calculating Total Cash Back Earned in " + cashBackCategory);
				break;
			case (6) :
				//calc total cash back for CC
				System.out.println("What credit card? Please provide the last 4 CC numbers.");
				int cashBackCardID = sc.nextInt();
				System.out.println("Calculating Total Cash Back Earned on Card " + cashBackCardID);
				break;
			case (7) :
				//calc total cash back for date range
				System.out.println("What is the start date for your range? Please use MMDDYY for the date format.");
				int cashBackStartDate = sc.nextInt();
				System.out.println("What is the end date for your range? Please use MMDDYY for the date format.");
				int cashBackEndDate = sc.nextInt();
				System.out.println("Calculating Total Spent Between " + cashBackStartDate + " and " + cashBackEndDate);
				break;
			case (8) :
				isMenuActive = false;
				return;
			default :
				System.out.println("Please enter a menu option from 0 to 8.");
				listMenuOptions();			
			}
			menuOption = sc.nextInt();
		}
	}
		
	private static void listMenuOptions() {
		System.out.println("TRANSACTION HISTORY MENU");
		System.out.println("[0] View All Transaction Records");
		System.out.println("[1] Calculate Total Spent in a Specific Category");
		System.out.println("[2] Calculate Total Spent on a Specific Credit Card");
		System.out.println("[3] Calculate Total Spent for a Specific Date Range");
		System.out.println("[4] Calculate Total Cash Back");
		System.out.println("[5] Calculate Total Cash Back in a Specific Category");
		System.out.println("[6] Calculate Total Cash Back on a Specific Credit Card");
		System.out.println("[7] Calculate Total Cash Back for a Specific Date Range");
		System.out.println("[8] Return to Main Menu");
	}


}
