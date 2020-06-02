
import java.util.*;
import java.text.*;

class Passwd {

	static String myGenPasswd()
	{
		final int PASSWD_LEN = 8;
		char c[] = new char[PASSWD_LEN];
		int n = 0;

		for(int i = 0; i < PASSWD_LEN; ++i) {
			n =  (int) (Math.random() * 62 );
			if(n >= Character.MAX_RADIX) {
				n = n % Character.MAX_RADIX;
				c[i] = Character.forDigit(n,
						Character.MAX_RADIX);
				c[i] = Character.toUpperCase(c[i]);
			} else {
				c[i] = Character.forDigit(n,
						Character.MAX_RADIX);
			}
		}
		return(new String(c));
	}



	static String generatePassword()
	{
		double charVal, rand;
		String password="";
		char c;
		int numOfChars=0;

		while(numOfChars < 6){
			rand = Math.floor(Math.random() * 9);
			numOfChars =  (new Double(rand)).intValue();
		}

		while(!containsDigits(password)){
			password = "";
			for (int i=0; i<numOfChars; i++) {
				  rand = Math.floor(Math.random() * 62);
				  int val = (new Double(rand)).intValue();
				  if (val > 36){
				      c =  Character.forDigit(val - 26,
						      Character.MAX_RADIX);
				      c =  Character.toUpperCase(c);
				  }else{
				      c = Character.forDigit(val,
						      Character.MAX_RADIX);
			          }
			          password += String.valueOf(c);
		        }
	    }
	    return password;

  	}

	static boolean containsDigits(String password)
	{
		    if (password.equals("")) return false;
		    String tmpString="";
		    String digits = "0123456789";
		    boolean hasDigit=false;
		    for(int i=0;i<password.length();i++){
		    	tmpString=password.substring(i,i+1);
		    	if ( digits.indexOf(tmpString) > -1){
		    	   hasDigit=true;
		    	   break;
		        }
    		    }
		    return hasDigit;
  	}

	static boolean isDigits(String str)
	{
		boolean result = true;
		for(int i=0; i<str.length(); i++){
			if(!Character.isDigit(str.charAt(i)))
				  result=false;
		}
			return result;
	}



	public static void main(String argv[])
	{

		/*
		for(int i=0; i < 10; ++i) {
			System.out.println("password=|" +
					generatePassword() + '|');
		}
		*/
		for(int i=0; i < 200; ++i) {
			System.out.println("password=|" + myGenPasswd() + '|');
		}
	}
}
