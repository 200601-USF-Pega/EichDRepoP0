package com.revature.creditcardrewardtracker.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.service.CalculatorService;

public class CalculatorMenu implements IUserMenu {
	
	@Override
	public void menu(Scanner sc, String username, Connection connection) {
		
		listMenuOptions();
		CalculatorService calcServ = new CalculatorService(connection, username, sc);
		
		//Scanner sc = new Scanner(System.in);
		int menuOption = sc.nextInt();
		
		boolean isMenuActive = true;
		
		while (isMenuActive == true) {			
			switch (menuOption) {
			case (0) :
				//launches credit card picking tool
				System.out.println("Launching Card Picker");
				calcServ.selectBestCard();
				listMenuOptions();
				break;
			case (1) :
				//launches cash back calculator
				System.out.println("Launching Cash Back Calculator");
				calcServ.calculatePercentageBack();
				listMenuOptions();
				break;
			case (2) :
				//returns to main menu
				System.out.println("Returning to Main Menu");
				isMenuActive = false;
				return;
			default :
				System.out.println("Please enter a menu option from 0 to 2.");
				listMenuOptions();
			}
			menuOption = sc.nextInt();
		}
	}
	
	private static void listMenuOptions() {
		System.out.println("CALCULATOR MENU");
		System.out.println("[0] Pick Best Card for Upcoming Transaction");
		System.out.println("[1] See Potential Cash Back for a Specific Credit Card");
		System.out.println("[2] Return to Main Menu");
	}

}
