//package util.regexp;


/**
 * regexpUnitAnyString class signifies a match for a string
 * of any length (in unix regular expression '*').
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


public class regexpUnitAnyString
    extends regexpUnit
{
    regexpUnitAnyString()
    {
        super(0, -1);
    }

    boolean matches(char c)
    {
        return super.matches(c);
    }

    public String toString()
    {
        return new String("*");
    }
}
