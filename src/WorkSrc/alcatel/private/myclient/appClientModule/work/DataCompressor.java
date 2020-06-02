package work;
import java.util.zip.*;
/**
 * @author Oded Nissan 16/07/2003
 * Compress Strings using the java.util.zip package.
 */
public class DataCompressor {

	/**
	 * cannot instantiate class.
	 */
	private DataCompressor()
	{
	}
	
	
	/**
	 * compress the String using the zip package.
	 * @param s the String to compress.
	 * @return String the compressed String.
	 */
	public static String compress(String s)
		throws Exception
	{
		byte in[] = s.getBytes("8859_1");
		byte out[] = new byte[in.length];
			
		Deflater def = new Deflater(Deflater.BEST_COMPRESSION);			
		def.setInput(in);
		def.finish();
		int len = def.deflate(out);
		def.end();
		String outStr= new String(out,0,len,"8859_1");	
		return(outStr);
	}
	
	/**
	 * uncompress the String using the zip package.
	 * @param s the String to uncompress.
	 * @return String the uncompressed String.
	 */
	
	public static String uncompress(String s)	
		throws Exception
	{
		byte in[] = s.getBytes("8859_1");	
		byte out[] = new byte[in.length];
		
		Inflater inf = new Inflater();
		inf.setInput(in);
		inf.inflate(out);		
		inf.end();
		String outStr = new String(out);
		return(outStr);				
	}
	
	
	/**
	 * main test program.
	 */
	public static void main(String args[]) 
	{
		
		String s = "Some fucking very very long String kjflkjlkjglkjfglkjkg gjg jgjgjjgjg gjgjgjgglgl jlfskjfsjkd fsjflksjfslkfjsdklfjsdklfj sfjklsdjfklsdfjskldf sjdf sjdfkljsdljfskldjfskldjf sdfj sdjf s fjslkdfjsdlkfjsdklfjsdklfjsklfjsfkl gjgjgjgj gjgjgjgjg";
		try {		
			System.out.println("input str = " + s + "\n input len = " + s.length());
			String compStr = DataCompressor.compress(s);
			System.out.println("after compress len = " + compStr.length());
//			String outStr = new String(out,"UTF-8");
			String decompStr  = DataCompressor.uncompress(compStr);
			System.out.println("after decompress len = " + decompStr.length());
			System.out.println(decompStr);

/*			
			 // Encode a String into bytes
			 String inputString = "blahblahblah??";
			 byte[] input = inputString.getBytes("8859_1");

			 // Compress the bytes
			 byte[] output = new byte[100];
			 Deflater compresser = new Deflater();
			 compresser.setInput(input);
			 compresser.finish();			
			 int compressedDataLength = compresser.deflate(output);
			 
			 s = new String(output,0, compressedDataLength,"8859_1");
			 System.out.println("s = " +s);
			 output = s.getBytes("8859_1");
			 System.out.println("output len = " + output.length + 
			 	" compress len = " + compressedDataLength);
			 	
			 // Decompress the bytes
			 Inflater decompresser = new Inflater();
			 decompresser.setInput(output, 0, compressedDataLength);
			 byte[] result = new byte[100];
			 int resultLength = decompresser.inflate(result);
			 decompresser.end();
			 System.out.println("result len = " + result.length + 
			 	" uncompress len = " + resultLength);

			 // Decode the bytes into a String
			 String outputString = new String(result, 0, resultLength);
			 System.out.println("output = " + outputString);
*/			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
