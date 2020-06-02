import java.text.*;
import java.util.*;
import java.net.*;
import onjlib.utils.*;

class A {

	public static void main(String argv[])
	{
		double d = 1.605;
		int n = 1;

                Debug.print("ffo");
		System.out.println("d = " + d);
		System.out.println("d2 = " + roundDecimal(d,2));
		int a[]= new int[0];

		if(a == null) {
			System.out.println("a is null");
		} else {
			System.out.println("length = " + a.length);
		}

		Calendar c = Calendar.getInstance();
		c.set(2000,1,1,0,0);
		Date d1 = c.getTime();
		System.out.println("c = " + c.toString());
		System.out.println("d1 = " + d1.toString());

		/*
		DecimalFormat df = new DecimalFormat("#");
		String s = df.format(n);
		System.out.println("n = " + s);
		tokenize("a,b,,,e");

		n = Integer.parseInt("1");
		System.out.println("n = " + n);

		InetAddress in = null;
		try {
			in = InetAddress.getByName("10.224.13.178");
			in = InetAddress.getLocalHost();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("in = " + in.toString());
		byte b[] = in.getAddress();
		for(int i = 0; i < 4; ++i) {
			System.out.println("b[" + i + "]= " +
					(int) (b[i] &0xff));
		}

		short port = 2000;
		System.out.println("port = " + port);
		System.out.println("port[0]  = " + (int )((port & 0xff00)>>8 ));
		System.out.println("port[1]  = " + (int)((port & 0x00ff) ));
		*/


	}


	static void tokenize(String s)
	{
		StringTokenizer st = new StringTokenizer(s,",",true);
		String token = null;
		int i = 0;
		while(st.hasMoreElements()) {
			++i;
			token = st.nextToken();
			System.out.println("token[" + i + "]=" + token);
		}

	}
	static String roundDecimal(double d, int decPlaces)
	{
		StringBuffer sb = new StringBuffer(25);

		sb.append("#######");
		if(decPlaces > 0 ) {
			sb.append('.');
		}
		for(int i = 0; i < decPlaces; ++i) {
			sb.append('0');
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		String s = df.format(d);
		Double d2 = new Double(s);
		return(s);
	}


}
