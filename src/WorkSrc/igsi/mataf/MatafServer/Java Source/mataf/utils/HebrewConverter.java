package mataf.utils;


public abstract class HebrewConverter
{

    private static BidiReorder bidiReorder = new BidiReorder();
    private static char codeEbcOld2Heb[] = null;
    
    public HebrewConverter()
    {
    }
    
    public static String convert2Hebrew(String aStr)
    {
        return convert2Hebrew(aStr, true);
    }

    public static String convert2Hebrew(String aStr, boolean aReverse)
    {
        makeTableMataf();
        char aCc[] = aStr.toCharArray();
        for(int i = 0; i < aCc.length; i++)
        {
            int iCode = aCc[i];
            if(iCode==38 || iCode==162 || (iCode > 96 && iCode <= 122))
            	aCc[i] = codeEbcOld2Heb[iCode];
            else
            	aCc[i] = aCc[i];//' ';
                
        }

        String rStr = new String(aCc);
        if(aReverse)
        {
            char revArray[] = new char[rStr.length()];
            bidiReorder.Convert(rStr.toCharArray(), revArray, 2);
            rStr = new String(revArray);
        }
        return rStr;
    }

    static void makeTableMataf()
    {
        if(codeEbcOld2Heb != null)
            return;
        codeEbcOld2Heb = new char[256];
        
		codeEbcOld2Heb[38] = ('à');
        codeEbcOld2Heb[97] = ('á');
        codeEbcOld2Heb[98] = ('â');
        codeEbcOld2Heb[99] = ('ã');
        codeEbcOld2Heb[100] = ('ä');
        codeEbcOld2Heb[101] = ('å');
        codeEbcOld2Heb[102] = ('æ');
        codeEbcOld2Heb[103] = ('ç');
        codeEbcOld2Heb[104] = ('è');
        codeEbcOld2Heb[105] = ('é');
        codeEbcOld2Heb[106] = ('ê');
        codeEbcOld2Heb[107] = ('ë');
        codeEbcOld2Heb[108] = ('ì');
        codeEbcOld2Heb[109] = ('í');
        codeEbcOld2Heb[110] = ('î');
        codeEbcOld2Heb[111] = ('ï');
        codeEbcOld2Heb[112] = ('ð');
        codeEbcOld2Heb[113] = ('ñ');
        codeEbcOld2Heb[114] = ('ò');
        codeEbcOld2Heb[115] = ('ó');
        codeEbcOld2Heb[116] = ('ô');
        codeEbcOld2Heb[117] = ('õ');
        codeEbcOld2Heb[118] = ('ö');
        codeEbcOld2Heb[119] = ('÷');
        codeEbcOld2Heb[120] = ('ø');
        codeEbcOld2Heb[121] = ('ù');
        codeEbcOld2Heb[122] = ('ú');
        codeEbcOld2Heb[162] = ('à');
                
    }

    public static String StringToHexString(String aStr)
        throws Exception
    {
        return StringToHexString(aStr, true);
    }

    public static String StringToHexString(String aStr, boolean needBlank)
        throws Exception
    {
        StringBuffer strBuff = new StringBuffer();
        for(int i = 0; i < aStr.length(); i++)
        {
            String sHex = Integer.toHexString(aStr.charAt(i));
            if(sHex.length() == 1)
                sHex = "0" + sHex;
            strBuff.append(sHex);
            if(needBlank)
                strBuff.append(" ");
        }

        return strBuff.toString().toUpperCase();
    }

    public static void printStringToHex(String aStr)
        throws Exception
    {
        int bytesInRow = 16;
        StringBuffer sbRet = new StringBuffer("\n");
        String ss = StringToHexString(aStr);
        for(int i = 0; i < ss.length() / 3; i++)
        {
            if(i % 4 == 0)
                sbRet.append("  ");
            if(i % bytesInRow == 0)
            {
                String sTemp = "" + (0x186a0 + i);
                sTemp = sTemp.substring(1);
                sbRet.append("\n").append(sTemp).append(": ");
            }
            String sPrint = ss.substring(i * 3, (i + 1) * 3);
            sbRet.append(sPrint);
        }

        System.out.println(sbRet.append("\n").toString());
    }
    
}
