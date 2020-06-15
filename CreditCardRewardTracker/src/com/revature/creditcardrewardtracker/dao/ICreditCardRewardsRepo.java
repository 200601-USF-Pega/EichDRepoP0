package com.revature.creditcardrewardtracker.dao;

public interface ICreditCardRewardsRepo {

	public boolean addCashBackCategory(int cardId, String category, double percentageback);
	
	public boolean deleteCashBackCategory(int categoryId);
	
	public boolean updateCashBackCategory(int categoryId, int option, Object obj);
}
