package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;
import com.revature.creditcardrewardtracker.models.CreditCard;
import com.revature.creditcardrewardtracker.models.User;

public class UserService {
	
	public String createNewUser(Scanner sc, Connection connection) {
		User user = new User();
		
		
		System.out.println("Please enter a username.");
		user.setUsername(sc.next());
		
		System.out.println("Please enter a password.");
		String password = sc.next();
		
		System.out.println("Please re-enter your password.");
		String passwordVerify = sc.next();
		
		user.setAdmin(false);
		
		if (password.equals(passwordVerify)) {
			user.setPassword(password);
			
			IUserRepo d = new UserRepoDB(connection);
			user = d.addUser(user);
			
			System.out.println("User successfully created.");
			return user.getUsername();
		} else {
			System.out.println("Passwords do not match. Exiting.");
			return null;
		}
	}
	
	public void addCardToUser(User user, CreditCard card) {
		user.addCardsToFile(card);
	}

}
