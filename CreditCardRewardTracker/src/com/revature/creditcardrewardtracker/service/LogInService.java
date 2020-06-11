package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;

public class LogInService {
	
	Connection connection;
	IUserRepo d;
	
	public LogInService(Connection connection) {
		this.connection = connection;
		d = new UserRepoDB(connection);
	}
	
	
	public String logInVerification(String username, String password) {
		String user = d.checkUser(username, password);
		return user;
	}
	
	public boolean adminVerification(String username) {
		boolean isAdmin = d.checkAdmin(username);
		return isAdmin;
		
	}
	
	
}
