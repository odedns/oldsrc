
import java.util.logging.*;
import java.util.Date;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MsgFormat extends Formatter {

    public MsgFormat() {
    }

	public String format(LogRecord lr)
	{
		StringBuffer sb = new StringBuffer(80);
		Date d = new Date(lr.getMillis());
		long mills = lr.getMillis();
		sb.append(lr.getLevel().toString());
		sb.append(": [");
		sb.append(d.toString());
		sb.append("] ");
		sb.append(lr.getMessage());
		sb.append('\n');
		return(sb.toString());
	}
}