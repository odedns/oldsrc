/**
 * Date: 26/03/2007
 * File: OrderTesr.java
 * Package: jpatest
 */
package jpatest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author a73552
 *
 */
public class OrderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * @param args
		 */
		EntityManagerFactory factory = null;
		EntityManager manager = null;
		
		try {
			factory =
		         Persistence.createEntityManagerFactory("myjpa");
		    manager = factory.createEntityManager( );
	    	 EntityTransaction trx = manager.getTransaction();
	    	 trx.begin();

		      Orders o = new Orders();
		      o.setOrderId(1);
		      o.setDescription("my order");
		      OrderItem item = new OrderItem();
		      item.setItemId(1000);
		      item.setDescription("my item");
		      item.setPrice(100);
		      item.setOrders(o);
		      manager.persist(o);
		      manager.persist(item);
		      trx.commit();
		      System.out.println("added order and items");
		     } catch (Exception e) {
		    	 e.printStackTrace();
		     }
		     finally {
		    	 manager.close();
		    	 factory.close();
		     }
	}

}
