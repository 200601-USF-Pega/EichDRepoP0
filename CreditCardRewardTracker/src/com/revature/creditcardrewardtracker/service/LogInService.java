package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;

public class LogInService {
	
	private static final Logger logger = Logger.getLogger(LogInService.class);

	
	private IUserRepo d;
	private ValidationService validation;
	
	public LogInService(Connection connection, Scanner sc) {
		d = new UserRepoDB(connection);
		validation = new ValidationService(connection);
	}
	
	
	public String logInVerification(String username, String password) {
		if (validation.usernameExistsValidation(username) == true) {
			String user = d.checkUser(username, password);
			if(logger.isInfoEnabled()){
    			logger.info("User " + user + " successfully logged in.");
    		}
			return user;
		} else {
			System.out.println("Username not found. Please try again.");
			return null;
		}
	}
	
	public boolean adminVerification(String username) {
		boolean isAdmin = d.checkAdmin(username);
		return isAdmin;
		
	}
	
	
}
