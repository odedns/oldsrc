package composer.utils;

import com.ibm.dse.base.*;
import java.io.*;

/**
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ContextSerializer {

	/**
	 * Constructor for CtxSerializer.
	 */
	private ContextSerializer() {
		super();
	}

	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctx the Context object to serialize.
	 * @return String the seralized Context as a String.
	 * @throws Exception in case of error.
	 */
	public static String serialize(Context ctx) throws Exception
	{
		String ctxData = null;
		byte b[] = serializeBytes(ctx);
		b = Base64.encode(b);
		ctxData = new String(b);		
		return(ctxData);		
	}

	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctx the Context object to serialize.
	 * @return String the seralized Context as a String.
	 * @throws Exception in case of error.
	 */
	public static byte[] serializeBytes(Context ctx) throws Exception
	{
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		ctx.writeToStream(os);
		os.flush();
		os.close();	
		return(bos.toByteArray());		
	}

	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctxData a String representing the serialized
	 * Context.
	 * @return Context the deserialized Context.
	 * @throws Exception in case of error.
	 */	
	public static Context deserialize(String data) throws Exception
	{
		byte b[] = data.getBytes();
		b = Base64.decode(b);
		Context ctx = deserialize(b);		
		return(ctx);
		
	}
	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctxData a String representing the serialized
	 * Context.
	 * @return Context the deserialized Context.
	 * @throws Exception in case of error.
	 */	
	public static Context deserialize(byte[] b) throws Exception
	{	
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(bis);		
		Context ctx =  new Context();
		ctx.readFromStream(is);
		
		return(ctx);
		
	}
	
	
}
