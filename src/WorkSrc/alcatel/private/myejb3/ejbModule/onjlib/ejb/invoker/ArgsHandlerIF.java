package onjlib.ejb.invoker;

import java.util.ArrayList;

/**
 * This is an inteface for the special handling
 * of arguments by the invoker.
 * @author Oded Nissan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ArgsHandlerIF
{

	/**
	 * This method is called by the invoker before passing the args
	 * to the ejb.
	 * @param arraylist the list of arguments to pack
	 * @return the packed args.
	 */
    public abstract Object packArgs(Object args);

    /**
     * This method is called by the EJB before activating
     * the service.
     * @param arraylist list of packed args.
     * @return unpacked list of args.
     */
    public abstract Object unpackArgs(Object args);
}
