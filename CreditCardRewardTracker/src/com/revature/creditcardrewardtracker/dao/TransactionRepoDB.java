package com.revature.creditcardrewardtracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.creditcardrewardtracker.models.Transaction;

public class TransactionRepoDB implements ITransactionRepo {

	Connection connection;
	
	public TransactionRepoDB(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void addTransaction(Transaction newTransaction) {
		try {
			PreparedStatement s = connection.prepareStatement("INSERT INTO transactionrecords(date, category, transactiontotal, cashbacktotal, cardid) VALUES (?, ?, ?, ?, ?)");
						
			s.setDate(1, convertUtilToSQLDate(newTransaction.getDate()));
			s.setString(2, newTransaction.getCategory());
			s.setDouble(3,  newTransaction.getTotal());
			s.setDouble(4,  newTransaction.getCashBackTotal());
			s.setInt(5,  newTransaction.getCardID());
			s.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Transaction> listTransactions(String username) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		
		try {
			Statement s = connection.createStatement();
			ResultSet rs;
			rs = s.executeQuery("SELECT date, category, transactiontotal, cashbacktotal, t.cardid FROM transactionrecords as t "
				+ "INNER JOIN ("
					+	"SELECT cardid FROM creditcards "
					+	"WHERE username = '" + username
					+ 	"') AS c ON t.cardid = c.cardid;");
			
			//printResultSet(rs);

			transactionList = createTransactionList(rs, transactionList);
			
			return transactionList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Transaction> listTransactionsForCategory(String username, String category) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		try {
			Statement s = connection.createStatement();
			ResultSet rs;
			rs = s.executeQuery("SELECT date, category, transactiontotal, cashbacktotal, t.cardid FROM transactionrecords as t "
				+ "INNER JOIN ("
					+	"SELECT cardid FROM creditcards "
					+	"WHERE username = '" + username
					+ 	"') AS c ON t.cardid = c.cardid "
					+	"WHERE category = '" + category + "';");
			
			//ResultSet copy = rs;
			//printResultSet(copy);

			transactionList = createTransactionList(rs, transactionList);
						
			return transactionList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public List<Transaction> listTransactionsForCreditCard(String username, int cardID) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		try {
			Statement s = connection.createStatement();
			ResultSet rs;
			rs = s.executeQuery("SELECT date, category, transactiontotal, cashbacktotal, t.cardid FROM transactionrecords as t "
				+ "INNER JOIN ("
					+	"SELECT cardid FROM creditcards "
					+	"WHERE username = '" + username
					+ 	"') AS c ON t.cardid = c.cardid "
					+	"WHERE c.cardId = " + cardID + ";");
			
			transactionList = createTransactionList(rs, transactionList);
			
			//printResultSet(rs);
			
			return transactionList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public List<Transaction> listTransactionsForDateRange(String username, java.util.Date startDate, java.util.Date endDate) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		java.sql.Date sqlStartDate = convertUtilToSQLDate(startDate);
		java.sql.Date sqlEndDate = convertUtilToSQLDate(endDate);
		try {
			Statement s = connection.createStatement();
			ResultSet rs;
			rs = s.executeQuery("SELECT date, category, transactiontotal, cashbacktotal, t.cardid FROM transactionrecords as t "
				+ "INNER JOIN ("
					+	"SELECT cardid FROM creditcards "
					+	"WHERE username = '" + username
					+ 	"') AS c ON t.cardid = c.cardid "
					+	"WHERE date BETWEEN '" + sqlStartDate + "' AND '" + sqlEndDate + "';");
			
			transactionList = createTransactionList(rs, transactionList);
			
			printResultSet(rs);
			
			return transactionList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateTransaction(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransaction(String username, Scanner sc) {
		
		try {
			Statement s = connection.createStatement();
			ResultSet rs;
			rs = s.executeQuery("SELECT * FROM transactionrecords as t "
				+ "INNER JOIN ("
					+	"SELECT cardid FROM creditcards "
					+	"WHERE username = '" + username
					+ 	"') AS c ON t.cardid = c.cardid;");
			
			printResultSet(rs);
			
			System.out.println("Please input the Transaction ID for the transaction to be deleted.");
			int option = sc.nextInt();
			System.out.println("You selected Transaction ID " + option + " to delete. Enter YES to confirm.");
			if (sc.next().equalsIgnoreCase("YES")) {
				s.execute("DELETE FROM transactionrecords "
						+ "WHERE transactionid = " + option +";");
				System.out.println("Record successfully deleted.");
			} else {
				System.out.println("Record will not be deleted.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static java.sql.Date convertUtilToSQLDate(java.util.Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
	
	private static java.util.Date convertSQLToUtilDate(java.sql.Date date) {
		java.util.Date utilDate = new java.util.Date(date.getTime());
		return utilDate;
	}
	
	private static List<Transaction> createTransactionList(ResultSet rs, List<Transaction> transactionList) {
		try {
			while(rs.next()) {
				Transaction tempTransaction = new Transaction();
				tempTransaction.setCardID(rs.getInt("cardid"));
				tempTransaction.setCategory(rs.getString("category"));
				tempTransaction.setTotal(rs.getDouble("transactiontotal"));
				tempTransaction.setDate(convertSQLToUtilDate(rs.getDate("date")));
				tempTransaction.setCashBackTotal(rs.getDouble("cashbacktotal"));
				transactionList.add(tempTransaction);
			}
			return transactionList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void printResultSet(ResultSet rs) {
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			   System.out.println("Printing out transactions");
			   int columnsNumber = rsmd.getColumnCount();
			   while (rs.next()) {
			       for (int i = 1; i <= columnsNumber; i++) {
			           if (i > 1) System.out.print(",  ");
			           String columnValue = rs.getString(i);
			           System.out.print(columnValue + " " + rsmd.getColumnName(i));
			       }
			       System.out.println("");
			   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
