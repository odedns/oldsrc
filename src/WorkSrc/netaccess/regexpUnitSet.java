//package util.regexp;


/**
 * regexpUnitSet class is a character set in a regular expression.
 * sets are grouped within brackets ([]).
 * a set can be any combination of the following:
 * 
 * A group of characters :  	[abcdrttt]
 * A range of characters: 	[a-z] [a-z0-9]
 * Any combination of both: 	[a-z#$%&0-9]
 * Sets can be negated using the '^' or '!' chars :   [!abc] or [!a-z]
 *
 * In order to use the '[' ']' '^' and '!' chars in the set as regular
 * characters (ignore their special meaning) use the escape char '\'
 * for example :  [abc\^\!\]] 
 * uses special characters as regular characters in the set.
 *
 *
 *
 * Change history
 * ==============
 *
 * ==================================================================
 * | When    | Who        | What                                    |
 * ==================================================================
 * | 12Mar98 | Oded Nissan| Created                                 |
 * ------------------------------------------------------------------
 * |         |            |                                         |
 * ==================================================================
 **/
     

public class regexpUnitSet
    extends regexpUnit
{
    private char[]  v;  // set vector
	
	// constructor
	regexpUnitSet()
	{
		// call super class constructor
		super(1,1);
	}


/**
 * init the set class
 * input string is the string containing a set of chars.
 * the method returns the number of characters the caller should skip
 **/
    public int init(String s)
    {
			
		int i= 0;
		char c=0;
		boolean done = false;
		boolean charEscaped = false;

    

//		System.out.println("calling UnitSet.init()" + "s = " + s);

		v = new char[s.length()];  // allocate array
		//
		// copy string to char array.

		
		for(i = 0; !done && i < s.length(); ++i) {
			c = s.charAt(i);

			switch(c) {

			case '[':
				if(charEscaped) {
					v[i] = c;
					charEscaped = false;
				} else {
					continue;
				}
				break;

			case ']':
				if(charEscaped) {
					v[i] = c;
					charEscaped = false;
				}  else {
					done = true;
				}
				break;

			case '\\':
				charEscaped = !charEscaped;
				break;
			default:
			  	v[i] = c;
				charEscaped = false;
				break;
			} // switch

			
		} // for
		

		return(i);
        
    }

    /** the UnitSet's matches method.
     * compare the input char to the set in the char vector.
     * returns true in case of a match.
     **/
    boolean matches(char c)
    {
         int i;
		 int st_range, end_range;
		 boolean mSet = false, done = false;



		for(i = 0;  !done && i < v.length; ++i) {


			switch(v[i]) {

			case '-':
				// range
				st_range = v[i-1];
				end_range = v[++i];
				if(c <= end_range && c>= st_range) {
						mSet = !mSet;
						done = true;
					
				}
				break;

			case '^':
			case '!':
				// negate the group
				mSet = !mSet;
				break;

			default:
				// regular char
				if(v[i]  == c) {
					mSet = !mSet;
					done = true;
				}
				break;
			}	 // end switch
			


		} // for


		return(mSet && super.matches(c));
    }

/**
 * convert the UnitSet's char vector to a string.
 **/
    public String toString()
    {
        return (new String (v));
    }
}
