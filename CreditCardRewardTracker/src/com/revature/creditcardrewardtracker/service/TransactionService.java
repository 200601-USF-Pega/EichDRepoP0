package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.ITransactionRepo;
import com.revature.creditcardrewardtracker.dao.TransactionRepoDB;
import com.revature.creditcardrewardtracker.models.Transaction;

public class TransactionService {
	
	ITransactionRepo d;
	String username;
	
	public TransactionService(String username, Connection connection) {
		d = new TransactionRepoDB(connection);
		this.username = username;
	}
	
	public Transaction recordNewTransaction(Scanner sc) {
		
		Transaction transaction = new Transaction();
		
		System.out.println("Preparing entry for Transaction");
		
		System.out.println("What are the last 4 digits of the credit card used?");
		transaction.setCardID(sc.nextInt());
		
		System.out.println("When did the transaction occur? Please use the YYYYMMDD format.");
		int dateInt = sc.nextInt();
		transaction.setDate(convertIntToDate(dateInt));
		
		sc.nextLine();
		System.out.println("Which category does the purchase fall under?");
		//will need to print out all the categories in the DB here
		transaction.setCategory(sc.nextLine().toUpperCase());
		
		System.out.println("What was the total for this transaction? Please use 0.00 for the format.");
		transaction.setTotal(sc.nextDouble());
		
		d.addTransaction(transaction);
		
		return transaction;
		
	}
	
	public List<Transaction> getUserTransactions() {
		List<Transaction> list = d.listTransactions(username);
		return list;
	}
	
	public double getTotalForCategories(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What category would like you pull records from?");
		String category = sc.nextLine().toUpperCase();
		List<Transaction> list = d.listTransactionsForCategory(username, category);
		
		return calculateTotalFromList(list);
	}
	
	public double getTotalForCard(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What credit card would like you pull records from? Please provide the last 4 digits of the card.");
		int card = sc.nextInt();
		List<Transaction> list = d.listTransactionsForCreditCard(username, card);
		
		return calculateTotalFromList(list);	
	}
	
	public double getTotalForDateRange(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What is the start date for your date range? Please use YYYYMMDD format.");
		int date1 = sc.nextInt();
		System.out.println("What is the end date for your date range? Please use YYYYMMDD format.");
		int date2 = sc.nextInt();
		
		List<Transaction> list = d.listTransactionsForDateRange(username, convertIntToDate(date1), convertIntToDate(date2));
		
		return calculateTotalFromList(list);
	}
	
	private static java.util.Date convertIntToDate(int yyyymmdd) {
		Integer rawDate = (Integer) yyyymmdd;
		String stringDate = rawDate.toString();
		java.util.Date date = null;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		try {
			date = format.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return date;
	}
	
	private static double calculateTotalFromList(List<Transaction> list) {
		double total = 0.0;
		
		for (Transaction transaction : list) {
			total += transaction.getTotal();
		}
		
		return total;
	}
}
