package mataf.utils;

/**
 * @author Oded Nissan 18/11/2003
 * This class provides static native OS methods.
 */

public class NativeUtils {
	/**
	 * load the nativeutils DLL file.
	 */
	static
    {
      System.loadLibrary("nativeutils");
    }
    
    /**
     * cannot instantiate class.
     */  
	private NativeUtils()
	{
	}

	/**
	 * call the native OS Beep system call.
	 * @param freq the beep frequency.
	 * @param duration  the beep duration.
	 */
	public static native void beep(int freq, int duration);
	
}
