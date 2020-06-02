package onjlib.ejb.emptest;

import java.util.Collection;

import onjlib.ejb.utils.HomeFactory;
import onjlib.utils.GTimer;

public class Client {
  EmpdemoHome m_home;
   EmpdemoSLHome m_slhome;
  EmpdemoSL m_edemo;

  static final int TRIES = 10;


  public static void main(String[] args) {
	System.out.println("\nBeginning .Client...\n");

	Client cl = new Client();
//    cl.create();
   //cl.createOne(2005);
   for(int i=0; i < 3; ++i) {
	   cl.findOne(100);
   }
   cl.findOneSL(110);
//   cl.findAllSL();
//   cl.findAll();

  }

  public Client()
  {
	  try {
		HomeFactory hf = HomeFactory.getInstance();
		m_home = (EmpdemoHome) hf.getHomeIF("emptest.EmpdemoHome", EmpdemoHome.class);

		m_slhome = (EmpdemoSLHome) hf.getHomeIF("emptest.EmpdemoSLHome", EmpdemoSLHome.class);
		m_edemo = m_slhome.create();


	  } catch(Exception e) {
		  e.printStackTrace();
	  }
  }


  void create()
  {
	  try {

		for(int i=0; i < 500; ++i) {
			  m_home.create(i, "Emp-" + i, 10);
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
 }

  void createOne(int i)
  {
	  try {
		GTimer t = new GTimer();
		t.start();

		  m_home.create(i, "Emp-" + i, 100);
		t.stop();
		System.out.println("createOne() elapsed : " + t.getElapsedTime());
	} catch(Exception e) {
		e.printStackTrace();
	}

  }


  void findOne(int i)
  {
	  try {
		GTimer t = new GTimer();
		t.start();
		Empdemo ed  = m_home.findByPrimaryKey(new Integer(i));
		t.stop();
		System.out.println("findOne() elapsed : " + t.getElapsedTime());
		 System.out.println( ed.getId() + " " + ed.getName());
	} catch(Exception e) {
		e.printStackTrace();
	}


   }
  void findAll()
  {
	  long total =0;
	  long elapsed;
	  Collection c=null;
	  try {
		for(int i=0; i < TRIES; ++i) {
			GTimer t = new GTimer();
			t.start();
			c  = m_home.findAll();
			t.stop();
			elapsed = t.getElapsedTime();
			System.out.println("findAll() elapsed : " + elapsed);
			total+=elapsed;
		System.out.println("findAll() avg time : " + total/TRIES);
		}
		Object o[] = c.toArray();
		for(int i=0; i < o.length; ++i) {
			Empdemo ed = (Empdemo)o[i];
			System.out.println( ed.getId() + " " + ed.getName());
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
  }



  void remove()
  {
	  try {

		for(int i=0; i < 200; ++i) {
			  m_home.remove(new Integer(i));
		}
	} catch(Exception e) {
		e.printStackTrace();
	}

 }




  void findOneSL(int i)
  {
	  try {
		GTimer t = new GTimer();
		t.start();
		Empinfo e  = m_edemo.getEmp(i);
		t.stop();
		System.out.println("getEmp() elapsed : " + t.getElapsedTime());
		if(null != e ) {
		 System.out.println(e.toString());
		}
	} catch(Exception e) {
		e.printStackTrace();
	}


   }
  void findAllSL()
  {
	  long total =0;
	  long elapsed;
	  try {
		for(int i=0; i < TRIES; ++i) {
			GTimer t = new GTimer();
			t.start();
			Empinfo ei[]  = m_edemo.getAll();
			t.stop();
			elapsed = t.getElapsedTime();
			System.out.println("getAll() elapsed : " + elapsed);
			total+=elapsed;
		}
		System.out.println("getAll() avg time: " + total/TRIES);
	} catch(Exception e) {
		e.printStackTrace();
	}
  }
}
