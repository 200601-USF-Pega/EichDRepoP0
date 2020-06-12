package com.revature.creditcardrewardtracker.service;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.dao.CreditCardRepoDB;
import com.revature.creditcardrewardtracker.dao.ICreditCardRepo;
import com.revature.creditcardrewardtracker.dao.ITransactionRepo;
import com.revature.creditcardrewardtracker.dao.TransactionRepoDB;
import com.revature.creditcardrewardtracker.models.CategoryCashBack;
import com.revature.creditcardrewardtracker.models.CreditCard;
import com.revature.creditcardrewardtracker.models.Transaction;

public class TransactionService {
	
	ITransactionRepo d;
	String username;
	Connection connection;
	
	public TransactionService(String username, Connection connection) {
		d = new TransactionRepoDB(connection);
		this.connection = connection;
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
		
		transaction.setCashBackTotal(this.calculateCashBack(transaction));
		
		d.addTransaction(transaction);
		
		return transaction;
		
	}
	
	public List<Transaction> getUserTransactions() {
		List<Transaction> list = d.listTransactions(username);
		return list;
	}
	
	public void updateTransaction(Scanner sc) {
		d.printResultSet(username);
		System.out.println("Please input the Transaction ID for the transaction to be updated.");
		int id = sc.nextInt();
		System.out.println("You selected Transaction ID " + id + " to modify. Enter YES to confirm.");
		if (sc.next().equalsIgnoreCase("YES")) {
			System.out.println("What would you like to update?");
			System.out.println("Enter the option for what you'd like to modify: [1] Date; [2] Category; [3] Transaction Total; [4] CardID");
			int option = sc.nextInt();
			switch (option) {
				case (1) :
					//date
					System.out.println("What date would you like to change it to? Please use the YYYYMMDD format.");
					java.util.Date newDate = convertIntToDate(sc.nextInt());
					d.updateTransaction(id, option, newDate);
					break;
				case (2) :
					//category
					sc.nextLine();
					System.out.println("What category would you like to change it to?");
					String newCat = sc.nextLine().toUpperCase();
					d.updateTransaction(id, option, newCat);
					break;
				case (3) :
					//transaction total
					System.out.println("What is the new Transaction Total?");
					double total = sc.nextDouble();
					d.updateTransaction(id, option, total);
					break;
				case (4) :
					//card
					System.out.println("What is the new Card ID?");
					int cardID = sc.nextInt();
					d.updateTransaction(id, option,  cardID);
					break;
				default :
					System.out.println("Invalid input");
			}
			d.printResultSet(username);
		}
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
	
	public double getTotalCashBack() {
		List<Transaction> list = d.listTransactions(username);
		return calculateTotalCashBackFromList(list);
	}
	
	public double getTotalCashBackForCategories(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What category would like you pull records from?");
		String category = sc.nextLine().toUpperCase();
		List<Transaction> list = d.listTransactionsForCategory(username, category);
		
		return calculateTotalCashBackFromList(list);
	}
	
	public double getTotalCashBackForCard(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What credit card would like you pull records from? Please provide the last 4 digits of the card.");
		int card = sc.nextInt();
		List<Transaction> list = d.listTransactionsForCreditCard(username, card);
		
		return calculateTotalCashBackFromList(list);	
	}
	
	
	public double getTotalCashBackForDateRange(Scanner sc) {
		
		sc.nextLine();
		System.out.println("What is the start date for your date range? Please use YYYYMMDD format.");
		int date1 = sc.nextInt();
		System.out.println("What is the end date for your date range? Please use YYYYMMDD format.");
		int date2 = sc.nextInt();
		
		List<Transaction> list = d.listTransactionsForDateRange(username, convertIntToDate(date1), convertIntToDate(date2));
		
		return calculateTotalCashBackFromList(list);
	}
	
	public void removeTransaction(Scanner sc) {
		d.deleteTransaction(username, sc);
	}
	
	private double calculateCashBack(Transaction transaction) {
		int card = transaction.getCardID();
		double rate = 0.0;
		double everythingRate = 0.0;
		
		double cashBack;
		
		ICreditCardRepo ccr = new CreditCardRepoDB(connection);
		List<CreditCard> cardsOnFile = ccr.getCreditCards(username);
		
		for (CreditCard cc : cardsOnFile) {
			if (cc.getCreditCardID() == card) {
				for (CategoryCashBack cat : cc.getCardCashBackCategories()) {
					if (cat.getCategoryOfCashBack().equalsIgnoreCase(transaction.getCategory())) {
						rate = cat.getPercentageOfCashBack();
						break;
					} else if (cat.getCategoryOfCashBack().equalsIgnoreCase("everything")) {
						everythingRate = cat.getPercentageOfCashBack();
						break;
					}
				}
			}
		}
		
		if (rate > everythingRate) {
			cashBack = transaction.getTotal() * rate;
		} else {
			cashBack = transaction.getTotal() * everythingRate;
		}
		
		return cashBack;
	}
	
	public static java.util.Date convertIntToDate(int yyyymmdd) {
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
	
	private static double calculateTotalCashBackFromList(List<Transaction> list) {
		double total = 0.0;
		
		for (Transaction transaction : list) {
			total += transaction.getCashBackTotal();
		}
		
		return total;
	}
	
}
