//package util.regexp;

import java.util.Vector;

/**
 * Main class for regexp package.
 * Tests weather a string matches a regular expression.
 * Testing is done by building a regexp object composed of a vector of
 * regexp units (see regexpUnit) and testing the match on it.
 * The object is not public, it is only used inside the package.
 *
 *
 * Change history
 * ==============
 *
 * ==================================================================
 * | When    | Who        | What                                    |
 * ==================================================================
 * | 28Dec97 | Uri Shahar | Created                                 |
 * ------------------------------------------------------------------
 * |         |            |                                         |
 * ==================================================================
 **/

public class regexp
{
    private Vector          units;    // composing units.
    private int             currentUnitIndex;
    private regexpUnit      currentUnit;

  /**
   * @method regexp - builds the regexpUnit vector.
   * @param  re     - a string representing regular expression.
   *
   **/
    private regexp(String re)
    {
		regexpUnitSet uset;
		boolean charEscaped = false;

        units = new Vector();

        for (int i = 0; i < re.length(); i++)
        {
			
			
            switch ( re.charAt(i) )
            {

				// support the escape char to suppress 
				// special meaning of special chars 
				
				case '\\':
					charEscaped = !charEscaped;
					break;

                case '*':
					if(charEscaped) {
						units.addElement(new regexpUnitChar(re.charAt(i)));
					} else {
	                    units.addElement(new regexpUnitAnyString());
						charEscaped = false;
					}
                    break;

                case '?':
					if(charEscaped) {
						units.addElement(new regexpUnitChar(re.charAt(i)));
					} else {
					     units.addElement(new regexpUnitAnyChar());
						 charEscaped = false;
					}
					 break;

				case '[':
					// a set
					if(charEscaped) {
						units.addElement(new regexpUnitChar(re.charAt(i)));
					} else {

						uset = new regexpUnitSet();
						// skip set chars
						// decrement i since its gonna be incremented by
						// the loop anyway
						i += uset.init(re.substring(i)) - 1;
						// add the set
						units.addElement(uset);
						charEscaped = false;
					}
					break;

                default:
					charEscaped = false;
                    units.addElement(new regexpUnitChar(re.charAt(i)));
            }
        }
        currentUnitIndex = 0;
        currentUnit      = (regexpUnit) units.elementAt(0);
    }

  /**
   * @method setCurrentUnit - Sets the current unit in regular expression.
   *                          When moving forward this is very simple, however
   *                          when moving back there is something to be done,
   *                          all units in the range between the current unit
   *                          and the value set are units that were tested,
   *                          i.e. their match functions were called, however
   *                          this path did not work so all those units should
   *                          be reset (see regexpUnit).
   *
   **/
    private void setCurrentUnit(int index)
    {
        if ( index < currentUnitIndex )
            while ( index <= currentUnitIndex )
            {
                ((regexpUnit) (units.elementAt(currentUnitIndex))).reset();
                currentUnitIndex--;
            }

        currentUnitIndex = index;
        currentUnit = (regexpUnit) units.elementAt(currentUnitIndex);
    }


  /**
   * @method wasMatched - called when the string reached it's end to
   *                      tell weather it fully matched the regexp.
   *                      A match means all of the units were matched
   *                      (see regexpUnit).
   *                      Since the test is done from first unit to last
   *                      and units are skipped only when they are matched,
   *                      we only need to test from current unit forward.
   *
   * @return value - true:  the regexp was matched.
   *                 false: the regexp was not matched.
   *
   **/
    private boolean wasMatched()
    {
        for (int i = currentUnitIndex; i < units.size(); i++)
            if ( !((regexpUnit) units.elementAt(i)).wasMatched() )
                return false;

        return true;
    }


  /**
   * @method matches           - tests weather a string matches the regexp,
   *                             starting from a specified unit.
   * @param  s                 - the string to matche.
   * @param  startingUnitIndex - index in units vector to start from.
   *
   * @return value - true:  there is a match.
   *                 false: there is no match.
   *
   **/
    private boolean matches(String s, int startingUnitIndex)
    {
        if ( s.length() == 0 )
            return wasMatched();

        if ( startingUnitIndex >= units.size() )
            return false;

        setCurrentUnit(startingUnitIndex);

        if ( !currentUnit.wasMatched() ) {
		//	System.out.println("notmatched for s " + s + 
		//		"unitindex " + startingUnitIndex);			
            if ( !currentUnit.matches(s.charAt(0)) )
                return false;
			else
                return matches(s.substring(1), startingUnitIndex);
        } else
            if ( matches(s, startingUnitIndex + 1) )
                return true;
            else
            {
                setCurrentUnit(startingUnitIndex);
                return ( currentUnit.matches(s.charAt(0))
                &&       matches(s.substring(1), startingUnitIndex) );
            }
    }


  /**
   * @method matches - Packages interface to the outside world.
   *                   Tests weather a string matches a regular expression.
   * @param  re      - The regular expression in string representation.
   * @param  s       - The string to matche.
   * @return value   - true:  there is a match
   *                   false: there is no match
   *
   **/
    public static boolean matches(String re, String s)
    {
        return new regexp(re).matches(s, 0);
    }


    public static void main(String[] argv)
    {
        if ( argv.length != 2 )
        {
            System.out.println("Usage:  regexp <expression> <string>");
            System.exit(1);
        }
        else
            System.out.println(matches(argv[0], argv[1]));
		
    }


  /**
   * @method toString - converts regexp to a string.
   *                    this is mainly used for debugging.
   * @return value    - a string representing the unit.
   *
   **/
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        for (int i = currentUnitIndex; i < units.size(); i++)
            sb.append(((regexpUnit) units.elementAt(i)).toString());

        return sb.toString();
    }
}
