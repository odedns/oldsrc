
import java.util.logging.*;
import java.nio.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Logtest {

    public Logtest() {
    }

	public static void main(String argv[])
	{
		Logger log = Logger.getLogger("general");
		log.setUseParentHandlers(false);
		ConsoleHandler ch = new ConsoleHandler();
		ch.setFormatter(new MsgFormat());
		log.addHandler(ch);

		log.warning("this is a warning message");
		log.fine("fine message");
		log.info("info message");
		log.severe("severe error ");
		IntBuffer inbuf = IntBuffer.allocate(10);
		for(int i=0; i < 10; ++i) {
			inbuf.put(i+100);
		}
		inbuf.flip();
		while(inbuf.hasRemaining()) {
			System.out.println(inbuf.get());
		}

	}
}