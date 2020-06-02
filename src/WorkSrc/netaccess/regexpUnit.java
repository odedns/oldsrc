//package util.regexp;

/**
 * The regexpUnit class is an abstract class representing
 * one part of a regular expression, such as a character,
 * an asterisk, a character range etc.
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


public abstract class regexpUnit
{
  /**
   * Each regexp unit may match a certain number of characters e.g
   * a "?" matches one character, a "*" matches 0 to infinity number
   * of characters etc.
   * Each unit has three variables to control this factor:
   *     minMatch   - the shortest string that matches this unit.
   *     maxMatch   - the longest  string that matches this unit.
   *     matchCount - the number of times matches method was called,
   *                  i.e the number of characters actually matched
   *                  by this unit.
   *
   *            Use maxMatch -1 to signify no max limit (e.g. "*")
   **/
    private int minMatch;
    private int maxMatch;
    private int matchCount;


  /**
   * @method wasMatched - The wasMatched method checks weather the regexpUnit
   *                      was matched or not; a regexpUnit is concidered
   *                      matched when it's matches method was called with
   *                      characters that match it all.
   *                      e.g - If the unit is "*" it is always matched
   *                            (even if it's matches routine was not called).
   *                            If the unit is "?" it is matched after being
   *                            called once.
   *                            If the unit is 3 occurances of A-Z it is
   *                            matched after being called 3 times with
   *                            characters from that range.
   *
   **/
    boolean wasMatched()
    {
        return ( ((matchCount <= maxMatch)  ||  (maxMatch == -1)) // -1 no max
        &&       (matchCount >= minMatch) );
    }


  /**
   * @method reset - When testing a string to match a regular expression
   *                 we try to match in a few different ways;
   *                 e.g if the pattern is "*a" and we test the string "xyza"
   *                 we try first to replace the asterisk with nothing, than
   *                 we try the asterisk as matching only "x" than "xy" etc.
   *                 The regexp.matches method checks all of those possibilities
   *                 serialy.
   *                 After the test failed in one way another way is tried.
   *                 When trying the new way we need to reset the regexpUnit
   *                 start matchCount from the beginning maybe more F.F.U.
   *
   **/
    void reset()
    {
        matchCount = 0;
    }

    
  /**
   * @method mathces - checks weather a character matches the specific 
   *                   regular expression part.
   * @param  c       - the character to test.
   * @return value   - True:  the character matches.
   *                   False: the character does not match.
   *
   **/
    boolean matches(char c)
    {
        if ( (maxMatch != -1)           // signifies ignore maxMatch (infinite)
        &&   (matchCount >= maxMatch) )
            return false;
        else
        {
            matchCount++;
            return true;
        }
    }


  /**
   * @method constructor, initializations common to all regexpUnits.
   *
   **/
    regexpUnit(int minMatch, int maxMatch)
    {
        this.minMatch = minMatch;
        this.maxMatch = maxMatch;
        matchCount = 0;
    }


  /**
   * @method toString - converts regexpUnit to a string.
   *                    this is mainly used for debugging.
   * @return value    - a string representing the unit.
   *
   **/
    public abstract String toString();
}
