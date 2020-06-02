package tests;



class MyException extends Exception {

	public MyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}


public class TestException {

	void foo1() throws ClassNotFoundException
	{
		Class c = Class.forName("nonExistentClass");
	}
	
	void foo2() throws ClassNotFoundException
	{
		foo1();
	}
	
	
	void foo3() throws MyException
	{
		try {
			foo2();
		} catch(ClassNotFoundException ce){
			throw new MyException("Error in foo2() ",ce);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TestException te = new TestException();
			te.foo3();
		} catch(MyException me){
			me.printStackTrace();
		}
	}

}
