/*
 * Created on Jul 21, 2003
 */
package com.ibm.odcb.tutorial.businessobjects;

/**
 * @author fenil
 */
public class Stock  implements java.io.Serializable
{

	private String symbol, currentPrice, volume, change;
	private double percentage;

	public Stock() {

	}

	public Stock(String symbol, float currentPrice) {
		setSymbol(symbol);
		setCurrentPrice(Float.toString(currentPrice));
		setVolume(Long.toString(0));
		setChange(Long.toString(0));
	}

	/**
	 * @return
	 */
	public String getChange() {
		return change;
	}

	/**
	 * @return
	 */
	public String getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param string
	 */
	public void setChange(String string) {
		change = string;
	}

	/**
	 * @param string
	 */
	public void setCurrentPrice(String string) {
		currentPrice = string;
	}

	/**
	 * @param string
	 */
	public void setSymbol(String string) {
		symbol = string;
	}

	/**
	 * @param string
	 */
	public void setVolume(String string) {
		volume = string;
	}

	/**
	 * @return
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * @param d
	 */
	public void setPercentage(double d) {
		percentage = d;
	}

}
