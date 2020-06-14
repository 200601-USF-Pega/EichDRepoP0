package com.revature.creditcardrewardtracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreditCardRewardsRepoDB implements ICreditCardRewardsRepo {
	
	Connection connection;
	
	public CreditCardRewardsRepoDB(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean addCashBackCategory(int cardId, String category, double percentageback) {
		try {
			PreparedStatement categoryStatement = connection.prepareStatement("INSERT INTO creditcardrewards(cardid, category, percentageofcashback) VALUES (?, ?, ?)");
			categoryStatement.setInt(1, cardId);
			categoryStatement.setString(2, category);
			categoryStatement.setDouble(3,  percentageback);
			categoryStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeCashBackCategory(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCashBackCategory(int categoryId, int option, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
