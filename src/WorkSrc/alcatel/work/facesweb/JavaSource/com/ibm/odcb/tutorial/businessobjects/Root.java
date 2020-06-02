/*
 * Created on Jul 21, 2003
 */
package com.ibm.odcb.tutorial.businessobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fenil
 */
public class Root implements java.io.Serializable {

	private String name;
	private List users, stocks;
	private Stock placeHolderStock;

	public Root() {
			users = new ArrayList(7);
			stocks = new ArrayList(7);
			placeHolderStock = new Stock("", 0);
			name = "All Users";

			// Creating 5 stocks      
			Stock IBM = new Stock("IBM", (float) 123.83);
			stocks.add(IBM);
			Stock SUNW = new Stock("SUNW", (float) 5.61);
			stocks.add(SUNW);
			Stock MSFT = new Stock("MSFT", (float) 34.55);
			stocks.add(MSFT);
			Stock AMZN = new Stock("AMZN", (float) 43.35);
			stocks.add(AMZN);
			Stock YHOO = new Stock("YHOO", (float) 49.91);
			stocks.add(YHOO);

			// Creating User PJ
			User U = new User(1, "Pierre Jackson", "PJ");
			users.add(U);
			// Creating one portfolio
			Portfolio Port = new Portfolio(U, "PJ portfolio 1");
			U.addPortfolio(Port);
			// Creating the positions    
			Position Pos = new Position(2, U, Port, IBM, (float) 61.85, 200);
			Port.addPosition(Pos);
			Pos = new Position(3, U, Port, SUNW, (float) 12.65, 133);
			Port.addPosition(Pos);
			Pos = new Position(4, U, Port, SUNW, (float) 1.95, 400);
			Port.addPosition(Pos);
			Pos = new Position(5, U, Port, YHOO, (float) 12.41, 175);
			Port.addPosition(Pos);
			Pos = new Position(6, U, Port, SUNW, (float) 2.55, 100);
			Port.addPosition(Pos);
			Pos = new Position(7, U, Port, MSFT, (float) 48.12, 250);
			Port.addPosition(Pos);
			Pos = new Position(8, U, Port, YHOO, (float) 10.21, 300);
			Port.addPosition(Pos);
			Pos = new Position(9, U, Port, IBM, (float) 54.75, 500);
			Port.addPosition(Pos);
			Pos = new Position(10, U, Port, AMZN, (float) 9.85, 500);
			Port.addPosition(Pos);

			// Creating User William Wenders
			U = new User(5, "William Wenders", "WW");
			users.add(U);
			// Creating first portfolio
			Port = new Portfolio(U, "WW portfolio 1");
			U.addPortfolio(Port);
			// Creating Positions
			Pos = new Position(11, U, Port, IBM, (float) 120.85, 100);
			Port.addPosition(Pos);
			Pos = new Position(12, U, Port, MSFT, (float) 40.32, 250);
			Port.addPosition(Pos);
			Pos = new Position(13, U, Port, YHOO, (float) 12.41, 300);
			Port.addPosition(Pos);
			// Creating second portfolio
			Port = new Portfolio(U, "WW portfolio 2");
			U.addPortfolio(Port);
			// Creating Positions
			Pos = new Position(21, U, Port, AMZN, (float) 8.15, 250);
			Port.addPosition(Pos);
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public Stock getPlaceHolderStock() {
		return placeHolderStock;
	}

	/**
	 * @return
	 */
	public Stock[] getStocks() {
		return (Stock[]) stocks.toArray(new Stock[stocks.size()]);
	}
	//	public List getStocks() {
	//		return stocks;
	//	}

	/**
	 * @return
	 */
	public User[] getUsers() {
		return (User[]) users.toArray(new User[users.size()]);
	}
	//	public List getUsers() {
	//		return users;
	//	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param stock
	 */
	public void setPlaceHolderStock(Stock stock) {
		placeHolderStock = stock;
	}

	/**
	 * @param list
	 */
	public void setStocks(Stock[] stocks) {
		this.stocks = Arrays.asList(stocks);
	}

	//	public void setStocks(List stocks) {
	//		this.stocks = stocks;
	//	}

	/**
	 * @param list
	 */
	public void setUsers(User[] users) {
		this.users = Arrays.asList(users);
	}

	//	public void setUsers(List users) {
	//		this.users = users;
	//	}
}
