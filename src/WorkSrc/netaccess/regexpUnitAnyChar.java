//package util.regexp;

/**
 * regexpUnitAnyChar class signifies a single
 * occurance of any character ('?' in unix regular expression convention).
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

public class regexpUnitAnyChar
    extends regexpUnit
{
    regexpUnitAnyChar()
    {
        super(1, 1);
    }

    boolean matches(char c)
    {
        return super.matches(c);
    }

    public String toString()
    {
        return new String("?");
    }
}
