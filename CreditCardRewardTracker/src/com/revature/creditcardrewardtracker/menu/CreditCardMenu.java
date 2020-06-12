package com.revature.creditcardrewardtracker.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.service.CreditCardService;

public class CreditCardMenu implements IUserMenu {

	@Override
	public void menu(Scanner sc, String username, Connection connection) {
		
		listMenuOptions();
		CreditCardService cardService = new CreditCardService(username, connection);
		
		int menuOption = sc.nextInt();
		
		boolean isMenuActive = true;
		
		while (isMenuActive == true) {			
			switch (menuOption) {
			case (0) :
				//Add a new credit card
				cardService.createNewCreditCard(sc);
				listMenuOptions();
				break;
			case (1) :
				//Remove a credit card on file
				listMenuOptions();
				break;
			case (2) :
				//Update a credit card on file
				listMenuOptions();
				break;
			case (3) :
				//Add a cash back category to a credit card
				listMenuOptions();
				break;
			case (4) :
				// Remove a cash back category from a credit card
				listMenuOptions();
				break;
			case (5) :
				//Update a cash back category
				listMenuOptions();
				break;
			case (6) :
				//List all credit cards
				System.out.println(cardService.getCreditCards());
				System.out.println();
				listMenuOptions();
				break;
			case (7) :
				//returns to main menu
				System.out.println("Returning to Main Menu");
				isMenuActive = false;
				return;
			default :
				System.out.println("Please enter a menu option from 0 to 7.");
				listMenuOptions();
			}
			menuOption = sc.nextInt();
		}
		
		
	}

	private static void listMenuOptions() {
		System.out.println("CREDIT CARD MENU");
		System.out.println("[0] Add a new credit card");
		System.out.println("[1] Remove a credit card on file");
		System.out.println("[2] Update a credit card on file");
		System.out.println("[3] Add a cash back category to a credit card");
		System.out.println("[4] Remove a cash back category from a credit card");
		System.out.println("[5] Update a cash back category");
		System.out.println("[6] List all credit cards");
		System.out.println("[7] Return to main menu");
	}
}