package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;

public class ValidationService {

	private Connection connection;
	private Scanner sc;
	
	public ValidationService(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}
	
	
	//validates that the username is available to be used to prevent unique errors from DB
	//returns false if used, true if new
	public boolean usernameUniqueValidation(String username) {
		IUserRepo ur = new UserRepoDB(connection);
		List<String> usernames = ur.getAllUsers();
		
		for (String user : usernames) {
			if (user.equalsIgnoreCase(username)) {
				System.out.println(username + " has already been used. Please select a different username.");
				return false;
			}
		}
		return true;
	}
	
	public boolean usernameLengthValidation(String username) {
		
		if(username.length() >= 5 && username.length() <= 25) {
			return true;
		} else {
			System.out.println("Username length is invalid. Please make your username between 5 and 25 characters.");
			return false;
		}
	}
	
	public boolean passwordLengthValidation(String password) {
		
		if(password.length() >= 5 && password.length() <= 25) {
			return true;
		} else {
			System.out.println("Password length is invalid. Please make your passowrd between 5 and 25 characters.");
			return false;
		}
	}
	

}
