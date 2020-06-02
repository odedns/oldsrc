package tests;

import java.lang.ref.*;

/**
 * Insert the type's description here.
 * Creation date: (15/01/03 11:05:46)
 * @author: Doron
 */
public class RefTest {
/**
 * RefTest constructor comment.
 */
 static WeakReference weak = null;
public RefTest() {
	super();
}

static void foo()
{
	String s = new String("foo");
	weak = new WeakReference(s);
	s = null;
}
public static void main(String argv[])
{
	System.out.println("in RefTest main");
	foo();
	try {
		Thread.sleep(60000);
	}catch(InterruptedException ie) {
		ie.printStackTrace();
	}

	System.out.println("now trying yo read weak");
	Object o = weak.get();
	if(o == null) {
		System.out.println("weak has been gc");
	} else {
		System.out.println("o = " + o);
	}
	
}

}
