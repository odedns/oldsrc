/**
 * Created By:	Gil Tabakman
 * Date:		May 05, 2005
 * Time:		5:12:09 PM
 */
package com.ness.fw.persistence.uuid;

import java.math.BigInteger;
import java.util.ResourceBundle;

import org.doomdark.uuid.EthernetAddress;
import org.doomdark.uuid.NativeInterfaces;
import org.doomdark.uuid.UUID;
import org.doomdark.uuid.UUIDGenerator;

/**
 * A static service class that provide UUID generation services to the FWUUID class.
 */
public class UUIDProvider {

    /**
     * Field kHexChars
     * (value is ""0123456789abcdefABCDEF"")
     */
    private static final String kHexChars = "0123456789abcdefABCDEF";

    /**
     * Field UUID_PROPS_FILE
     * (value is ""uuid"")
     */
    private static final String UUID_PROPS_FILE = "uuid";
	/**
	 * Field MAC_ADDRESS_TYPE_PROPERTY_NAME
	 * (value is ""macAddressType"")
	 */
	private static final String MAC_ADDRESS_TYPE_PROPERTY_NAME = "macAddressType";
	/**
	 * Field MAC_ADDRESS_TYPE_JNI
	 * (value is 1)
	 */
	private static final int MAC_ADDRESS_TYPE_JNI = 1;
	/**
	 * Field MAC_ADDRESS_TYPE_DEFAULT
	 * (value is 2)
	 */
	private static final int MAC_ADDRESS_TYPE_DEFAULT = 2;
	/**
	 * Field MAC_ADDRESS_DEFAULT_PROPERTY_NAME
	 * (value is ""macAddressDefault"")
	 */
	private static final String MAC_ADDRESS_DEFAULT_PROPERTY_NAME = "macAddressDefault";
	/**
	 * Field UUID_TYPE_PROPERTY_NAME
	 * (value is ""uuidType"")
	 */
	private static final String UUID_TYPE_PROPERTY_NAME = "uuidType";

	/**
	 * Field UUID_TYPE_BYTE_ARRAY
	 * (value is 1)
	 */
	public static final int UUID_TYPE_BYTE_ARRAY = 1;
	/**
	 * Field UUID_TYPE_STRING
	 * (value is 2)
	 */
	public static final int UUID_TYPE_STRING = 2;
    
	/**
	 * Field uuidType
	 */
	private static int uuidType;
	/**
	 * Field addr
	 */
	private static EthernetAddress addr;
	/**
	 * Field uuidGenerator
	 */
	private static UUIDGenerator uuidGenerator;

	static {
		ResourceBundle rb = ResourceBundle.getBundle(UUID_PROPS_FILE);
		uuidType = Integer.parseInt(ResourceBundle.getBundle(UUID_PROPS_FILE).getString(UUID_TYPE_PROPERTY_NAME));
		int macAddressType = Integer.parseInt(rb.getString(MAC_ADDRESS_TYPE_PROPERTY_NAME));
		if(macAddressType == MAC_ADDRESS_TYPE_JNI){
			addr = NativeInterfaces.getPrimaryInterface();
		} else {
			addr = EthernetAddress.valueOf(rb.getString(MAC_ADDRESS_DEFAULT_PROPERTY_NAME));
		}
		uuidGenerator = UUIDGenerator.getInstance();
		uuidGenerator.generateTimeBasedUUID(addr);
	}
	
	/**
	 * Method generateUUID
	 * @return String
	 */
	public static String generateUUID(){
		UUID uuid = uuidGenerator.generateTimeBasedUUID(addr);
		return uuid.toString();
	}

	/**
	 * Method generateUUIDasBytes
	 * @return byte[]
	 */
	public static byte[] generateUUIDasBytes(){
		UUID uuid = uuidGenerator.generateTimeBasedUUID(addr);
		return uuid.asByteArray();
	}

	/**
	 * Method generateUUIDasHex
	 * @return String
	 */
	public static String generateUUIDasHex(){
		UUID uuid = uuidGenerator.generateTimeBasedUUID(addr);
		return translateBytesToString(uuid.asByteArray());
	}

	/**
	 * Method translateBytesToString
	 * @param bytes byte[]
	 * @return String
	 */
	public static String translateBytesToString(byte[] bytes){
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < 16; i++) {
			int hex = bytes[i] & 0xFF;
			sb.append(kHexChars.charAt(hex >> 4));
			sb.append(kHexChars.charAt(hex & 0x0f));
		}
		return sb.toString(); 
	}
	
	/**
	 * Method translateStringToBytes
	 * @param string String
	 * @return byte[]
	 */
	public static byte[] translateStringToBytes(String string){
		BigInteger bi = new BigInteger(string, 16);
		return bi.toByteArray();
	}
	
	/**
	 * Method getUuidType
	 * @return int
	 */
	public static int getUuidType() {
		return uuidType;
	}

	/**
	 * Method isTypeByteArray
	 * @return boolean
	 */
	public static boolean isTypeByteArray(){
		return uuidType == UUID_TYPE_BYTE_ARRAY;
	}

	/**
	 * Method isTypeString
	 * @return boolean
	 */
	public static boolean isTypeString(){
		return uuidType == UUID_TYPE_STRING;
	}
}
