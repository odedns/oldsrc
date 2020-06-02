/*
 * Created on 16/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;
import org.hibernate.cfg.*;
import org.hibernate.*;
import java.net.URL;
import java.util.*;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Test
{

	public static void main(String[] args)
	{
		String fname = "c:/ibm/wks/dbproj/src/hibernate.cfg.xml";
		try {
									// com.ibm.db2j.jdbc.Db2jDriver
			Class c = Class.forName("com.ibm.db2j.jdbc.DB2jDriver");
			Configuration cfg  = new Configuration();			
			URL url = ClassLoader.getSystemResource("hibernate.cfg.xml"); 
			
			SessionFactory sessionFactory =	cfg.configure().buildSessionFactory();

			Session s = sessionFactory.openSession();
			
			Transaction trx = s.beginTransaction();
			
			Team team = new Team();
			team.setName("Ravens");
			team.setCity("Baltimore");
			Player player = new Player();
			player.setFirstName("Ray");
			player.setLastName("Lewis");
			player.setDraftDate(new Date());
			player.setAnnualSalary(new Float( 1000000));
			Set players = new HashSet();
			players.add(player);
			team.setPlayers(players);
			player.setTeam(team);
			s.save(team);
			System.out.println("team saved");
			trx.commit();
			s.flush();
			s.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
