package work;

import migdal.services.*;
import com.ibm.dse.services.jdbc.*;
import com.ibm.dse.base.*;

import java.text.*;
import migdal.common.mechira.*;
import migdal.operations.blogic.mechira.*;
import migdal.utils.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Insert the type's description here.
 * Creation date: (24/09/02 17:07:07)
 * @author: Doron
 */
public class Test {
/**
 * Test constructor comment.
 */
public Test() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (11/06/01 16:16:21)
 */
public static void init() throws Exception 
{

	Context.reset();
	Settings.reset("C:/Migdal/dse/server/dse.ini");
	Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
	
}

static void testBL() throws Exception
{
	HandleMechiraScrBOp op = new HandleMechiraScrBOp();
	// we need this context of the state transition vars.
	Context ctxBase = (Context) Context.readObject("htmlSessionCtx");
	// the context that the BL uses
	Context ctx = (Context) Context.readObject("mechiraProcCtx");
	// chain the, together.
	ctx.chainTo(ctxBase);
	// pass the created context to the BL.
	op.setContext(ctx);
	// fill the context with some test values.
	
	op.testParams();
	// execute the BL
	op.execute();
	

}


static String formatDate(Date d, String format)
{
	SimpleDateFormat fmt = new SimpleDateFormat(format);
	return(fmt.format(d));	
}



static String formatDate(String dStr, String format)
{
	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
	String s= null;
	try {
		Date d = fmt.parse(dStr);
		fmt.applyPattern(format);
		s = fmt.format(d);

	}catch(ParseException pe) {
		pe.printStackTrace();
		s = null;		
	}
	return(s);	
}






	public static void main(String args[])
	{
		try {
			init();
			testBL();
			/*
			IshurScreenOneBOp bop1 = (IshurScreenOneBOp)Class.forName("migdal.operations.blogic.mechira.IshurScreenOneBOp").newInstance();
			System.out.println("scr 1 foname succeeded");
			IshurScreenTwoBOp bop = (IshurScreenTwoBOp)Class.forName("migdal.operations.blogic.mechira.IshurScreenTwoBOp").newInstance();
			System.out.println("scr2 foname succeeded");
			*/

			/*
			KeyedCollection kc = (KeyedCollection)DataElement.readObject("kodPniyaLetikRecordData");
			kc.setValueAt("taarichPniaLeTik", "1/1/2003");
			String s= (String) kc.getValueAt("taarichPniaLeTik");
			System.out.println("s = " + s);
			
			Date d = KeyedColUtils.getDate(kc,"taarichPniaLeTik");
			System.out.println("d = " + d);

			kc.setValueAt("taarichPniaLeTik", "1.1");
			float f = KeyedColUtils.getFloat(kc,"taarichPniaLeTik");
			System.out.println("f = " + f);			

			kc.setValueAt("taarichPniaLeTik", "100");
			int i = KeyedColUtils.getInt(kc,"taarichPniaLeTik");
			System.out.println("i = " +i);			
	
*/
			System.out.println("formatDate =" + formatDate(new Date(),"MM/yyyy"));
			System.out.println("formatDate =" + formatDate("11/12/2002","MM/yyyy"));

			java.util.Vector ar = MechiraUtils.getKisuyDates("12/2002", "MM/yyyyy");
			System.out.println("ar = " + ar.toString());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

/**
 * Insert the method's description here.
 * Creation date: (14/10/02 10:20:21)
 * @param ic com.ibm.dse.base.IndexedCollection
 */
public static void print(IndexedCollection ic) throws Exception
{
	for(int i=0; i<ic.size(); ++i) {
		KeyedCollection kc = (KeyedCollection) ic.getElementAt(i);
		System.out.println("kc = " + kc.toString());				

	}
	
}

public static java.util.Vector getKisuyDates(String dateStr, String format) throws Exception
{

	java.util.Vector v = new java.util.Vector();
	Date d = DateUtils.getDateFromString(dateStr,format);
	String dStr = DateUtils.formatDate(d,"MM/yyyy");
	v.add(dStr);
	Date currDate = new Date();
	Calendar c = Calendar.getInstance();
	c.setTime(currDate);
	c.set(Calendar.HOUR,0);
	c.set(Calendar.MINUTE,0);
	c.set(Calendar.SECOND,0);
	c.set(Calendar.MILLISECOND,0);
	currDate = c.getTime();
	
	int currDay = c.get(Calendar.DAY_OF_MONTH);
	long millis = currDate.getTime();
	c.setTime(d);
	c.set(Calendar.DAY_OF_MONTH,currDay);
	c.set(Calendar.HOUR,0);
	c.set(Calendar.MINUTE,0);
	c.set(Calendar.SECOND,0);
	c.set(Calendar.MILLISECOND,0);
	
	c.add(Calendar.MONTH,1);
	while(currDate.after(d)) {
		d = c.getTime();
		System.out.println("d = " + d.toString());
		dStr = DateUtils.formatDate(d,"MM/yyyy");
		v.add(dStr);
		c.add(Calendar.MONTH,1);
	}

	if(currDay > 16 && !d.after(currDate)) {
		c.setTime(currDate);
		c.add(Calendar.MONTH,1);		
		currDate = c.getTime();
		dStr = DateUtils.formatDate(currDate,"MM/yyyy");		
		v.add(dStr);
	}
	
	return(v);
	
}
}
