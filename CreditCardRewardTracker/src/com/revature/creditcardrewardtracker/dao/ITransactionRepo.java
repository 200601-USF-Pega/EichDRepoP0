package com.revature.creditcardrewardtracker.dao;

import java.util.List;

import com.revature.creditcardrewardtracker.models.Transaction;

public interface ITransactionRepo {
	
	public void addTransaction(Transaction newTransaction);
	
	public List<Transaction> listTransactions(String username);
	
	public List<Transaction> listTransactionsForCategory(String username, String category);
	
	public List<Transaction> listTransactionsForCreditCard(String username, int cardID);
	
	public List<Transaction> listTransactionsForDateRange(String username, java.util.Date startDate, java.util.Date endDate);

}
