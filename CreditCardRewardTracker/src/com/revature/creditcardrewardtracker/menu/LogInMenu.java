package com.revature.creditcardrewardtracker.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.service.LogInService;
import com.revature.creditcardrewardtracker.service.UserService;

public class LogInMenu implements IMenu {
	
	public void menu(Scanner sc, Connection connection) {
		
		System.out.println("Welcome to the Credit Card Reward Calculator and Tracker!");
		listMenuOptions();
		
		//Scanner sc = new Scanner(System.in);
		
		int option = sc.nextInt();
		boolean isMenuActive = true;
		
		while (isMenuActive == true) {
			switch (option) {
			//log in
			case (1) :
				LogInService login = new LogInService(connection);
	
				System.out.println("Please enter your username:");
				String username = sc.next();
				
				System.out.println("Please enter your password:");
				String password = sc.next();
				
				String user = login.logInVerification(username, password);
				user = user.toLowerCase();
				
				if (user == null) {
					break;
				}
				
				AdminMenuFactory menuFactory = new AdminMenuFactory();
				IUserMenu menu = menuFactory.getMenu(login.adminVerification(username));
				menu.menu(sc, user, connection);
				isMenuActive = false;
				break;
			// create a new account
			case (2) :
				UserService creation = new UserService();
				String newUsername = creation.createNewUser(sc, connection);
				MainMenu newUserMenu = new MainMenu();
				newUsername = newUsername.toLowerCase();
				newUserMenu.menu(sc, newUsername, connection);
				isMenuActive = false;
				break;
			case (3) :
				System.out.println("Goodbye");
				isMenuActive = false;
				//sc.close();
				System.exit(0);
				return;
			default :
				System.out.println("Please enter 1 to log in or 2 to create a new account.");
				listMenuOptions();
				option = sc.nextInt();
				break;
			}
			
		}	

	}
	
	private static void listMenuOptions() {
		System.out.println("To continue, please log in or create a new account.");
		System.out.println("To log in, please enter 1.");
		System.out.println("To create a new account, please enter 2.");
		System.out.println("To exit the application, please enter 3.");
	}
	

}