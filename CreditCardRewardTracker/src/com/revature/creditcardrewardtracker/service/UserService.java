package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;
import com.revature.creditcardrewardtracker.models.CreditCard;
import com.revature.creditcardrewardtracker.models.User;

public class UserService {
	
	private Connection connection;
	private Scanner sc;
	private ValidationService validation;
	
	public UserService(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
		this.validation = new ValidationService(connection, sc);
	}
	
	public String createNewUser() {
		User user = new User();
		
		boolean hasUsername = false;
		
		while (hasUsername == false) {
			System.out.println("Please enter a username.");
			System.out.println("Usernames must be between 5 and 25 characters and are not case sensitive.");
			String username = sc.next();
			if (validation.usernameLengthValidation(username) == true) {
				if (validation.usernameUniqueValidation(username) == true) {
					user.setUsername(username);
					hasUsername = true;
				}
			}
		}
		
		boolean hasPassword = false;
		
		while (hasPassword == false) {
			System.out.println("Please enter a password.");
			String password = sc.next();
			
			System.out.println("Please re-enter your password.");
			String passwordVerify = sc.next();
			
			if (password.equals(passwordVerify)) {
				if(validation.passwordLengthValidation(password) == true) {
					hasPassword = true;
					user.setPassword(password);
				}
			} else {
				System.out.println("Passwords do not match.");
			}
		}
		
			user.setAdmin(false);

			IUserRepo d = new UserRepoDB(connection);
			user = d.addUser(user);
			
			System.out.println("User " + user.getUsername() + " successfully created.");
			return user.getUsername();
		
	}
	
	public void addCardToUser(User user, CreditCard card) {
		user.addCardsToFile(card);
	}

}
