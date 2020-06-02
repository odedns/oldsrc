package genbeans;
import javax.ejb.*;
import javax.naming.*;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;

public class Client {
 String url       = "t3://localhost:7001";
 BufferedReader m_br = null;
  genbeans.CarCMPHome m_home;


  public static void main(String[] args) {
	System.out.println("\nBeginning .Client...\n");

	Client cl = new Client();
	cl.run();

  }

  public Client()
  {
  }
  void run()
  {
   try {
		m_br = new BufferedReader(new InputStreamReader(System.in));
		Hashtable h = new Hashtable();
		h.put(Context.INITIAL_CONTEXT_FACTORY,
			"weblogic.jndi.WLInitialContextFactory");
		h.put(Context.PROVIDER_URL, url);
	  Context ctx          = new InitialContext(h);
	  m_home = (CarCMPHome ) ctx.lookup("CarCMPHome");


	handleMenu();
	ctx.close();

	} catch (Exception e) {
			e.printStackTrace();
	}
	System.out.println("\nEnd Car  Client...\n");
  }
  void handleMenu()
  {

	boolean done = false;
	while(!done) {
		System.out.println("Enter Choice: ");
			System.out.println("C - create");
		System.out.println("F - Find");
			System.out.println("A - FindAll");
		System.out.println("U - Update");
		System.out.println("R - Remove");
			System.out.println("Q - Quit");
		try {
				String s = m_br.readLine();
				char c = s.charAt(0);
			switch (c) {
					case 'C':
						handleCreate();
						break;
				case 'F':
						handleFind();
						break;
					case 'U':
						handleUpdate();
					break;
					case 'R':
						handleRemove();
						break;
				case 'Q':
					done = true;
					break;
				default:
						System.out.println("unsupported option : " + c);
						break;
				}

			}
		catch (Exception ex) {
				ex.printStackTrace();

			}
	}


 }

 void handleCreate()
 {

	  String s;
	  int id;
	System.out.println("in handleCreate");
	  try {
			  System.out.println("Create :");
		  System.out.print("enter id: ");
		  s= m_br.readLine();
		  id = Integer.parseInt(s);
		  System.out.print("enter name : ");
		  s= m_br.readLine();


		System.out.println("trying to create: " + id + "-" + s);
		Integer i = new Integer(id);
		CarCMPRemote mycar = (CarCMPRemote) m_home.create(i,s,s);
		System.out.println("create successful ..");
		System.out.println("created : " + mycar.getId() + "-" + mycar.getName());


	  }
	  catch (Exception ex) {
		ex.printStackTrace();
	  }


 }

 void handleFind()
 {
	  String s;
	  int id;
		System.out.println("in handleFind");
	try {
		System.out.println("Find :");
		System.out.print("enter id: ");
		s= m_br.readLine();
		id = Integer.parseInt(s);
		System.out.println("trying to find : " + id );
		CarCMPRemote mycar = (CarCMPRemote) m_home.findByPrimaryKey(new Integer(id));
		if (null != mycar) {
				System.out.println("found : " + mycar.getId() + "-" + mycar.getName());
		} else {
			System.out.println("no found ...");
		}

	}
	catch (Exception ex) {
		ex.printStackTrace();

	}



}
void handleRemove()
 {
	System.out.println("in handleRemove");
	String s;
	int id;
	try {
		System.out.print("enter id: ");
		s= m_br.readLine();
		id = Integer.parseInt(s);
		System.out.println("trying to remove : " + id );
		/*
		CarCMPRemote mycar = (CarCMPRemote) m_home.findByPrimaryKey(new Integer(id));
		mycar.remove();
		*/
		m_home.remove(new Integer(id));

			System.out.println("removed : " + id);

	}
	catch (Exception ex) {
		ex.printStackTrace();

	}

 }


 void handleUpdate()
 {
	System.out.println("in handleUpdate");
		String s;
	int id;
	try {
		System.out.print("enter id: ");
		s= m_br.readLine();
		id = Integer.parseInt(s);
		System.out.println("trying to update : " + id );

		CarCMPRemote mycar = (CarCMPRemote) m_home.findByPrimaryKey(new Integer(id));
		mycar.setName("updated name ");

	}
	catch (Exception ex) {
		ex.printStackTrace();

	}

 }
}
