/**
 * Date: 26/03/2007
 * File: Order.java
 * Package: jpatest
 */
package jpatest;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

/**
 * @author a73552
 *
 */


@Entity
@Table(name="ORDERS")
public class Orders {
	@Id
	private int orderId;
	private String description;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "orders")
	private List<OrderItem> items;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<OrderItem> getItems() {
		if(items == null) {
			items = new LinkedList();
		}
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
