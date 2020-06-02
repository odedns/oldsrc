class A {
		
		void foo()
		{
			String callingClassName = 
	            sun.reflect.Reflection.getCallerClass(2).getName();
			System.out.println("calling class = " + callingClassName);
		}
	}

class B {
	
	void bar()
	{
		A a = new A();
		a.foo();
	}
}
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("in Test...");
		B b = new B();
		b.bar();
	}

}
