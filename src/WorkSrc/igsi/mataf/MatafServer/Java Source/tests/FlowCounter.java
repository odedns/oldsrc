package tests;

/**
 * @author Eyal Ben Ze'ev
 *
 * This is a counter that counts the number of states the Flow Processor
 * has entered. Please do not change without telling me - Eyal.
 * Window>Preferences>Java>Code Generation.
 */
public class FlowCounter {

	private static FlowCounter counter;
	private int stateconter;
	
	private FlowCounter() {
	}
	
	public static synchronized FlowCounter getInstance() {
		if (counter==null)
			counter = new FlowCounter();
		return counter;
	}
	
	public void promoteCounter () {
		stateconter++;
	}
	
	public int getTheNumberOfStates() {
		return stateconter;
	}
}
