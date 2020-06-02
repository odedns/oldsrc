import java.lang.reflect.*;
public class FooWrapper { 
 public static void main(String argv[]) {
try {
 Class main = Class.forName("Foo");
String s[] = new String[1];
 Class args[] = new Class[1];
args[0] =  s.getClass();
Foo a = (Foo) main.newInstance();
Method m = main.getMethod("main",args);
 Object margs[] =  new Object[1];
margs[0] = argv;
    	m.invoke(a,margs);
 } catch(Exception e) {
 e.printStackTrace();
}
 }
}
