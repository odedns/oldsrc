/*
 * Created on Jul 21, 2003
 */
package com.ibm.odcb.tutorial.businessobjects;
import java.util.Date;
/**
 * @author fenil
 */
public class Position implements java.io.Serializable
{
	private int refNum;
	private User user;
	private Portfolio portfolio;
	private Stock stock;
	private float price;
	private int quantity;
	private Date purchaseDate = new Date();
	//private String symbol;
	public Position()
	{
	}
	public Position(
		int refNum,
		User user,
		Portfolio portfolio,
		Stock stock,
		float price,
		int quantity)
	{
		this.refNum = refNum;
		this.user = user;
		this.portfolio = portfolio;
		this.stock = stock;
		this.price = price;
		this.quantity = quantity;
	}
	/**
	 * @return
	 */
	public Portfolio getPortfolio()
	{
		return portfolio;
	}
	/**
	 * @return
	 */
	public float getPrice()
	{
		return price;
	}
	/**
	 * @return
	 */
	public int getQuantity()
	{
		return quantity;
	}
	/**
	 * @return
	 */
	public int getRefNum()
	{
		return refNum;
	}
	/**
	 * @return
	 */
	public User getUser()
	{
		return user;
	}
	
	/**
	 * @param portfolio
	 */
	public void setPortfolio(Portfolio portfolio)
	{
		this.portfolio = portfolio;
	}
	/**
	 * @param f
	 */
	public void setPrice(float f)
	{
		price = f;
	}
	
	/**
	 * @param i
	 */
	public void setQuantity(int i)
	{
		quantity = i;
	}
	/**
	 * @param i
	 */
	public void setRefNum(int i)
	{
		refNum = i;
	}
	/**
	 * @param user
	 */
	public void setUser(User user)
	{
		this.user = user;
	}
	/**
	 * @return
	 */
	public Stock getStock()
	{
		return stock;
	}
	/**
	 * @param stock
	 */
	public void setStock(Stock stock)
	{
		this.stock = stock;
	}
	public String getSymbol()
	{
		return this.stock.getSymbol();
	}
	public void setSymbol(String symbol)
	{
		this.stock.setSymbol(symbol);
	}
	/**
	 * @return
	 */
	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	/**
	 * @param date
	 */
	public void setPurchaseDate(Date date)
	{
		purchaseDate = date;
	}

}
