package com.revature.creditcardrewardtracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.creditcardrewardtracker.models.CategoryCashBack;
import com.revature.creditcardrewardtracker.models.CreditCard;

public class CreditCardRepoDB implements ICreditCardRepo {
	
	Connection connection;
	
	public CreditCardRepoDB(Connection connection) {
		this.connection = connection;
	}

	@Override
	public CreditCard addCreditCard(String username, CreditCard card) {
		//1. insert credit card to creditcards table
		//2. add creditcardcategories to creditcardrewards table
	
		try {
			PreparedStatement creditCardStatement = connection.prepareStatement("INSERT INTO creditcards VALUES (?, ?, ?)");
			creditCardStatement.setInt(1,  card.getCreditCardID());
			creditCardStatement.setString(2,  card.getCreditCardName());
			creditCardStatement.setString(3,  username);
			creditCardStatement.executeUpdate();
			
			for (CategoryCashBack category : card.getCardCashBackCategories()) {
				Statement categoryStatement = connection.createStatement();
				categoryStatement.executeUpdate("INSERT INTO creditcardrewards(cardid, category, percentageofcashback) "
						+ "VALUES ('" + card.getCreditCardID() + "', '"
						+ category.getCategoryOfCashBack() + "', '" + category.getPercentageOfCashBack() + "');");
			}
			
			return card;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CreditCard> getCreditCards(String username) {
		List<CreditCard> listOfCards = new ArrayList<CreditCard>();
		ResultSet RSCards;
		ResultSet RSCats;
		try {
			Statement s = connection.createStatement();
			RSCards = s.executeQuery("SELECT * FROM CreditCards"
					+ " WHERE username = '" + username + "';");

			//ResultSet rsa = s.getResultSet();
			while (RSCards.next()) {
				Statement ns = connection.createStatement();
				CreditCard tempCard = new CreditCard();
				tempCard.setCreditCardID(RSCards.getInt("cardid"));
				tempCard.setCreditCardName(RSCards.getString("cardname"));
				
				List<CategoryCashBack> categories = new ArrayList<CategoryCashBack>();
				RSCats = ns.executeQuery("SELECT * FROM creditcardrewards"
						+ " WHERE cardid = " + tempCard.getCreditCardID() + ";");
				//ResultSet rsn = s.getResultSet();
				while (RSCats.next()) {
					CategoryCashBack tempCat = new CategoryCashBack();
					tempCat.setCategoryOfCashBack(RSCats.getString("category"));
					tempCat.setPercentageOfCashBack(RSCats.getDouble("percentageofcashback"));
					categories.add(tempCat);
				}
				
				tempCard.setCardCashBackCategories(categories);
				
				listOfCards.add(tempCard);
			}
			
			return listOfCards;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
