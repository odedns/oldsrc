package work;

import java.util.Vector;
import java.io.*;

abstract class Base {
	static void foo()
	{
		System.out.println("in Base.foo()");
	}	
	static void print()
	{
		System.out.println("in Base.print()");
	}
	
}


class Derived extends Base {
	static void foo()
	{
		System.out.println("in Derived.foo()");
	}	
	
	static void foo2()
	{
		System.out.println("in Derived.foo2()");
		
	}	
	
	
}


public class T {


	static void foo() throws Exception
	{
		System.out.println("in foo");
		try {
			throw new Exception("foo exception");
		} 
		/*
		catch(Exception e) {
			e.printStackTrace();
		}
		*/
		finally {
			System.out.println("in finally");

		}

	}

	public static void main(String args[])
	{
		
			
		System.out.println("T ...");
		try {
			//foo();
			Derived d = new Derived();
			d.foo();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
