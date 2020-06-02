
import java.io.*;
import java.util.*;
import java.util.regex.*;


public class T {
	public static void main(String argv[])
	{
		//foo();
		//
		String a = "aaaaa";
		String z = "zzzz";

		System.out.println("a.hashCode() = " + a.hashCode());
		System.out.println("z.hashCode() = " + z.hashCode());

		int i = 0;
//		assert i > 0;
/*
		Pattern p = Pattern.compile("a*b");
		 Matcher m = p.matcher("aaaaab");
		System.out.println("matches = " + m.matches());
		*/
		System.out.println("matches = " + Pattern.matches("a*b","aaaab"));

	}

	void bar()
	{
		ArrayList al = new ArrayList();

		for(int i=0; i<10; ++i) {
			al.add(new String("String: " + i));
		}



		String v[]= new String[1];
		v = (String[]) al.toArray(v);
		for(int i=0; i<10; ++i) {
			System.out.println("v[" + i + "]=" + (String)v[i]);
		}

	}


	static void foo()
	{
		String fname = "foo.properties";
		Properties props = new Properties();

		try {
			InputStream is = fname.getClass().getResourceAsStream("/" + fname);
			props.load(is);
		} catch(IOException ie) {
			ie.printStackTrace();
		}

		System.out.println(props.toString());
	}



}

