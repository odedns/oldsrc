/**
 * Date: 26/03/2007
 * File: OrderItem.java
 * Package: jpatest
 */
package jpatest;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * @author a73552
 *
 */
@Entity
public class OrderItem {
	@Id
	private int itemId;
	private String description;
	private float price;
	@ManyToOne
	@JoinColumn(name="ORDERID", referencedColumnName = "orderId")
	private Orders orders;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
