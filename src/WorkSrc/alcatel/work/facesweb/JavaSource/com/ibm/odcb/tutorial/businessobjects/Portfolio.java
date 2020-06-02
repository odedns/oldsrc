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
public class Portfolio implements java.io.Serializable
{

	private User user;
	private String portfolioName;
	private List positions;

	public Portfolio() {
		positions = new ArrayList(10);
	}

	public Portfolio(User user, String portfolioName) {
		this();
		this.user = user;
		this.portfolioName = portfolioName;
	}
	/**
	 * @return
	 */
	public String getPortfolioName() {
		return portfolioName;
	}

	/**
	 * @return
	 */
		public Position[] getPositions() {
			return (Position[])positions.toArray(new Position[positions.size()]);
		}

//	public List getPositions() {
//		return positions;
//	}

	/**
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param string
	 */
	public void setPortfolioName(String string) {
		portfolioName = string;
	}

	/**
	 * @param list
	 */
		public void setPositions(Position[] positions) {
			this.positions = Arrays.asList(positions);
		}
//	public void setPositions(List positions) {
//		this.positions = positions;
//	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public void addPosition(Position position) {
		positions.add(position);
	}
	
	public void removePosition(Position position)
	{
		positions.remove(position);
	}
}
