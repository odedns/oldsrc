package composer.utils;
import com.ibm.dse.base.*;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KeyedColSerializer {
	/**
	 * Constructor for CtxSerializer.
	 */
	private KeyedColSerializer() {
		super();
	}

	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctx the Context object to serialize.
	 * @return String the seralized Context as a String.
	 * @throws Exception in case of error.
	 */
	public static String serialize(KeyedCollection kc) throws Exception
	{
		String ctxData = null;
		byte b[] = serializeBytes(kc);
//		b = Base64.encode(b);
		ctxData = new String(b,"8859_1");		
		return(ctxData);		
	}

	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctx the Context object to serialize.
	 * @return String the seralized Context as a String.
	 * @throws Exception in case of error.
	 */
	public static byte[] serializeBytes(KeyedCollection kc) throws Exception
	{
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		kc.writeExternal(os);
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
	public static KeyedCollection deserialize(String data) throws Exception
	{
		byte b[] = data.getBytes("8859_1");
		KeyedCollection kc = deserialize(b);		
		return(kc);
		
	}
	/**
	 * serialize a context into a byte array.
	 * Transform the byte array into a String and return  it.
	 * @param ctxData a String representing the serialized
	 * Context.
	 * @return Context the deserialized Context.
	 * @throws Exception in case of error.
	 */	
	public static KeyedCollection deserialize(byte[] b) throws Exception
	{	
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(bis);		
		KeyedCollection kc =  new KeyedCollection();
		kc.readExternal(is);
		return(kc);
		
	}
	
}
