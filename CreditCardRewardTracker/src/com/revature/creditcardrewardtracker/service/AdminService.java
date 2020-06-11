package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.IUserRepo;
import com.revature.creditcardrewardtracker.dao.UserRepoDB;

public class AdminService {
	
	Scanner sc;	
	IUserRepo d;
	
	public AdminService(Scanner sc, Connection connection) {
		this.sc = sc;
		d = new UserRepoDB(connection);
	}
	
	public List<String> getAllUsers() {
		List<String> results;
		results = d.getAllUsers();
		return results;
	}
	
	public void changeUserPassword() {
		System.out.println("Please enter the username associated with the account to reset password.");
		String username = sc.next();
	
		System.out.println("Please enter new password for user.");
		String newPassword = sc.next();

		boolean result = d.changePassword(username, newPassword);
		if (result == true) {
			System.out.println(username + " password's updated.");
		} else {
			System.out.println(username + " password not changed.");
		}
	}
	
	public void promoteUserAccount() {
		System.out.println("Please enter the username associated with the account to be promoted to admin.");
		String username = sc.next();
		
		boolean result = d.promoteAdmin(username);
		if (result == true) {
			System.out.println(username + " promoted to admin successfully.");
		} else {
			System.out.println(username + " not promoted to admin.");
		}
	}
	
	public void demoteUserAccount() {
		System.out.println("Please enter the username associated with the account to be demoted to user.");
		String username = sc.next();
		
		boolean result = d.demoteAdmin(username);
		if (result == true) {
			System.out.println(username + " demoted to standard account successfully.");
		} else {
			System.out.println(username + " not demoted to standard account.");
		}
	}

}
