package tests;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import mataf.decorator.LogicalToVisualDecorator;
/**
 * @author O702998
 *
 * Class: LogicalToVisualTest.java
 * Description:
 * Created at: Dec 16, 2003
 * 
 */
public class LogicalToVisualTest {

	public static void main(String[] args) {
		String input;
		String print1 = "";
		String print2 = "";
		try {

			LogicalToVisualDecorator l2v = new LogicalToVisualDecorator();
			PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			input = new String("ישראל 1234");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n\r";
			input = new String("Israel");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n\r";
			input = new String("Israel ישראל");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n\r";
			input = new String("ישראל Israel");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n\r";
			input = new String("ישראל שלמה");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n\r";
			input = new String("United States 12345");
			print1 += input + "\n\r";
			print2 += l2v.addDecoration(input) + "\n";
			input = new String("ישראל גולן 1234 ABCD");
			print1 += input + "\n\r\f";
			print2 += l2v.addDecoration(input) + "\n\r";
			Doc doc = new SimpleDoc(print2.getBytes("Cp862"), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
			DocPrintJob job = ps.createPrintJob();
			job.print(doc, null);
			System.out.println("After:\n"+print2+"******");
		}
		catch (Exception e) {
		}	
	}
}	
