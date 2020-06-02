package mataf.utils;

public class NativeUtils {
	static
    {
      System.loadLibrary("nativeutils");
    }  
	private NativeUtils()
	{
	}

	public static native void beep(int freq, int duration);




	public static void main(String argv[])
	{
		NativeUtils.beep(700,2000);
	}

}
