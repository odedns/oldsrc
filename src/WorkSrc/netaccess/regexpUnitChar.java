//package util.regexp;


/**
 * regexpUnitChar class is simply a character in a regular expression.
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
     

public class regexpUnitChar
    extends regexpUnit
{
    private char c; // the character

    regexpUnitChar(char c)
    {
        super(1, 1);
        this.c = c;
    }

    boolean matches(char c)
    {
        if ( c == this.c )
            return super.matches(c);
        else
            return false;
    }

    public String toString()
    {
        return (new Character(c)).toString();
    }
}
