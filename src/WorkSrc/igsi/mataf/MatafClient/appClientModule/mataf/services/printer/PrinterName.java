package mataf.services.printer;

import javax.print.attribute.EnumSyntax;

/**
 * @author tibig
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrinterName extends EnumSyntax {
	public static final PrinterName NONE = new PrinterName(-1);
	public static final PrinterName DEFAULT = new PrinterName(0);
	public static final PrinterName SECONDARY = new PrinterName(1);

	protected PrinterName(int value) {
		super(value);
	}

	private static final String[] stringTable = { "none", "default", "secondary" };

	protected String[] getStringTable() {
		return stringTable;
	}

	private static final PrinterName[] enumValueTable = { NONE, DEFAULT, SECONDARY };

	protected EnumSyntax[] getEnumValueTable() {
		return enumValueTable;
	}

	public static final int length = 2; 
}
