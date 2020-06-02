package mataf.services.printer;

import javax.print.attribute.standard.PrinterState;

/**
 * @author tibig
 *
 */

public class PrinterConstants {

	public static final String PRINT_CONTROL = "prtControl.";
	public static final String PRINT_MODE = "prtControl.printMode";
	public static final String MODE_NORMAL = "0";
	public static final String MODE_RESTORE = "1";
	public static final String PRINT_DATA = "prtControl.printData";
	public static final String FORM_NAME = "prtControl.formName";
	public static final String FORMAT_ID = "prtControl.formatId";
	public static final String REPLACE_CONTROL_TAGS = "prtControl.replControlTags";
	public static final String CARBON = "prtControl.Carbon";
	public static final String CODEPAGE = "prtControl.Codepage";
	public static final String CP_WIN = "264";
	public static final String CP_OS2 = "488";
	public static final String COPIES = "prtControl.Copies";
	public static final String DUPLEX = "prtControl.Duplex";
	public static final String TRAY = "prtControl.Tray";
	public static final String WATER = "prtControl.Water";
	public static final String NO_COPY = "";
	public static final String SINGLE_COPY = "11";
	public static final String DOUBLE_COPY = "14";
	public static final String FORM_FILE_EXT = "map";
	public static final String NO_PRINTERS_AVAILABLE = "לא נמצאו מדפסות";
	public static final String NO_BRANCH_SERVER_FOUND = "מדפסת סניפית אינה קיימת";
	public static final String CONTROL_TAGS = "@Carbon@Codepage@Copies@Duplex@Tray@Subform:Water";
	public static final String TAG_PATTERN = "\\[##.+?\\]";
	public static final String TAG_PREFIX = "[##";
	public static final String TAG_SUFFIX = "]";
	public static final String RESP_PATTERN = "-(\\w+)";
	public static final int RESP_GROUP = 1;
	public static final String RESP_SUFFIX = "_DESC";
	public static final String VECTOR_PATTERN = "^(\\p{Alpha}\\w+)\\.?(\\p{Alpha}\\w+)\\((\\p{Digit}+)\\)";
	public static final int VECTOR_GROUP_PART1 = 1;
	public static final int VECTOR_GROUP_PART2 = 3;
	public static final int VECTOR_GROUP_PART3 = 2;
	
	/*
	 * Print job status
	 */
	public static final String COMPLETED = "completed";
	public static final String CANCELED = "canceled";
	public static final String FAILED = "failed";
	public static final String NOEVENTS = "noevents";
	public static final String ATTENTION = "attention";

	/*
	 * Printer service states
	 */	
	public static final int IDLE = PrinterState.IDLE.getValue();	
	public static final int PROCESSING = PrinterState.PROCESSING.getValue();	
	public static final int STOPPED = PrinterState.STOPPED.getValue();	
	public static final int UNKNOWN = PrinterState.UNKNOWN.getValue();	
}
