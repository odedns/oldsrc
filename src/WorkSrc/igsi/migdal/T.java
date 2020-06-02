package work;

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
			foo();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
