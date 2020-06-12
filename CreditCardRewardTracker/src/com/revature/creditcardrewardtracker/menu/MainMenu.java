package com.revature.creditcardrewardtracker.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.service.CreditCardService;
import com.revature.creditcardrewardtracker.service.TransactionService;

public class MainMenu implements IUserMenu {
	
	public void menu(Scanner sc, String username, Connection connection) {
		
		System.out.println("Welcome to the Credit Card Reward Calculator and Tracker!");
		System.out.println("This console application is designed to help you save money "
				+ "by determing the highest possible cash back rate for your purchase.");
		System.out.println();
		
		System.out.println("To begin saving money, please select one of our main menu options.");
		System.out.println();

		listMenuOptions();
		
		//Scanner sc = new Scanner(System.in);
		int menuOption = sc.nextInt();
		
		boolean isMenuActive = true;
		
		while (isMenuActive == true) {			
			switch (menuOption) {
			case (0) :
				//launch calculator menu/tool
				System.out.println("Launching calculator");
				CalculatorMenu calcMenu = new CalculatorMenu();
				calcMenu.menu(sc, username, connection);
				break;
			case (1) :
				//add credit card, reward categories, and % back
				System.out.println("Launching database to add a new credit card");
				CreditCardService cardService = new CreditCardService(username, connection);
				System.out.println(cardService.createNewCreditCard(sc).toString());
				break;
			case (2) :
				//add to transaction history
				//records the transaction date (used to make transaction ID), credit card used, category, and total spent
				System.out.println("Launching database to add new transaction record");
				TransactionService transactionService = new TransactionService(username, connection);
				System.out.println(transactionService.recordNewTransaction(sc));
				break;
			case (3) :
				//view transaction history
				System.out.println("Launching transaction history tool");
				TransactionHistoryMenu transactionHistoryMenu = new TransactionHistoryMenu();
				transactionHistoryMenu.menu(sc, username, connection);
				break;
			case (4) :
				//view credit cards on file
				System.out.println("Launching Credit Card database");
				CreditCardService cardService2 = new CreditCardService(username, connection);
				System.out.println(cardService2.getCreditCards());
				break;
			case (5) :
				//exit
				System.out.println("Logging out");
				IMenu logInMenu = new LogInMenu();
				logInMenu.menu(sc, connection);
				break;
			default :
				//else
				System.out.println("Please enter the number of one of the main menu options from 0 to 5.");
				listMenuOptions();
			}
			
			menuOption = sc.nextInt();
		}
		
	}
	
	private static void listMenuOptions() {
		System.out.println("MAIN MENU");
		System.out.println("[0] Calculate Maximum Rewards");
		System.out.println("[1] Add Credit Card to File");
		System.out.println("[2] Add Transaction Record to File");
		System.out.println("[3] View Transaction History");
		System.out.println("[4] View Credit Cards on File");
		System.out.println("[5] Log out");
	}

}