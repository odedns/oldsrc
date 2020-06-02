/**
 * Date: 22/03/2007
 * File: Test.java
 * Package: jpatest
 */
package jpatest;

import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * @author a73552
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory =
	         Persistence.createEntityManagerFactory("myjpa");
	      EntityManager manager = factory.createEntityManager( );
	      
	     try {
	    	 EntityTransaction trx = manager.getTransaction();
	    	 trx.begin();
	    	 Team t = manager.find(Team.class, 11);
	    	 System.out.println("got team:" + t.getName());
	    	 //Players p = manager.find(Players.class, 1);
	    	 //System.out.println("player name: " + p.getFirstName() + ' ' + p.getLastName());
	    	 /*
	    	 List<Players> l = t.getPlayers();
	    	 for(Players p:l) {
	    		 System.out.println("player name: " + p.getFirstName() + ' ' + p.getLastName());
	    	 }
	    	 
	    	 */
	    	 
	    	 //Team t = manager.find(Team.class, 1);
	    	 Player p = new Player();
	    	 p.setId(112);
	    	 p.setName("Terrel Owens");
	    	 p.setPos(2);
	    	 p.setSalary(1000000);
	    	 p.setTeam(t);
	    	 t.getPlayers().add(p);	    	 
	    	 manager.persist(p);
	    	 trx.commit();
	    	 /*
	    	 
	    	 Car c = new Car();
	    	 c.setDesc("Blue");
	    	 c.setModel("Ibiza");
	    	 c.setId(new Long(110));
	    	 c.setYear(1998);	    	
	    	 manager.persist(c);
	    	 
	    	 trx.commit();
	    	 
	    	 System.out.println("added car");
	    	 c = manager.find(Car.class,new Long(10));
	    	 if(c != null) {
	    		 System.out.println("found car");
	    	 } else {
	    		 System.out.println("casr not found");
	    	 }
	    	 */
	     } catch(Exception e) {
	    	 e.printStackTrace();
	     }
	     finally {
	    	 manager.close();
	    	 factory.close();
	     }
	}

}
