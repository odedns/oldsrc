// FrontEnd Plus GUI for JAD
// DeCompiled : CodesTable.class

package mataf.utils;


class CodesTable
{

    public boolean existChar;
    public char charValue;

    CodesTable()
    {
        existChar = false;
        charValue = '\0';
    }

    public void setCharCode(char cCode)
    {
        existChar = true;
        charValue = cCode;
    }
}
