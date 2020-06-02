/**
 * Created By:	Gil Tabakman
 * Date:		May 05, 2005
 * Time:		5:12:09 PM
 */
package com.ness.fw.persistence.uuid;

/**
 * A class that holds a UUID generated with the JUG UUID generator. A UUID is
 * held as either a String of 32 characters or as a byte array of 16 bytes. The
 * class holds its value as an Object that can hold either of them. The storage
 * type is determined by the static UUIDProvider class.
 */
public class FWUUID {

	/**
	 * Field uuidObject
	 */
	private Object uuidObject = null;

	/**
	 * Field hashCode
	 */
	private int hashCode = 0;
	
	/**
	 * Constructor for FWUUID
	 */
	public FWUUID() {
	}

	/**
	 * Constructor for FWUUID
	 * 
	 * @param uuidBytes
	 *            byte[]
	 */
	public FWUUID(byte[] uuidBytes) {
		setUUID(uuidBytes);
	}

	/**
	 * Constructor for FWUUID
	 * 
	 * @param uuidString
	 *            String
	 */
	public FWUUID(String uuidString) {
		setUUID(uuidString);
	}

	/**
	 * Constructor for FWUUID
	 * 
	 * @param uuidObject
	 *            Object
	 */
	public FWUUID(Object uuidObject) {
		setUUID(uuidObject);
	}

	/**
	 * Method getType
	 * 
	 * @return int
	 */
	public static int getType() {
		return UUIDProvider.getUuidType();
	}

	/**
	 * Method setUUID
	 * 
	 * @param uuidBytes
	 *            byte[]
	 */
	public void setUUID(byte[] uuidBytes) {
		setUUID((Object) uuidBytes);
	}

	/**
	 * Method setUUID
	 * 
	 * @param uuidString
	 *            String
	 */
	public void setUUID(String uuidString) {
		setUUID((Object) uuidString);
	}

	/**
	 * Method setUUID
	 * 
	 * @param uuidObjectToSet
	 *            Object
	 */
	public void setUUID(Object uuidObjectToSet) {
		if (uuidObjectToSet != null) {
			if (UUIDProvider.isTypeByteArray()) {
				if (uuidObjectToSet instanceof byte[]) {
					uuidObject = uuidObjectToSet;
				} else {
					uuidObject = UUIDProvider
							.translateStringToBytes((String) uuidObject);
				}
			} else {
				if (uuidObjectToSet instanceof byte[]) {
					uuidObject = UUIDProvider
							.translateBytesToString((byte[]) uuidObjectToSet);
				} else {
					uuidObject = uuidObjectToSet;
				}
			}
		} else {
			uuidObject = null;
		}
		hashCode = 0;
	}

	/**
	 * Method getUUIDAsBytes
	 * 
	 * @return byte[]
	 */
	public byte[] getUUIDAsBytes() {
		return (byte[]) getUUID(UUIDProvider.UUID_TYPE_BYTE_ARRAY);
	}

	/**
	 * Method getUUIDAsString
	 * 
	 * @return String
	 */
	public String getUUIDAsString() {
		return (String) getUUID(UUIDProvider.UUID_TYPE_STRING);
	}

	/**
	 * Method getUUID
	 * 
	 * @return Object
	 */
	public Object getUUID() {
		return getUUID(UUIDProvider.getUuidType());
	}

	/**
	 * Method getUUID
	 * 
	 * @param requestedType
	 *            int
	 * @return Object
	 */
	public Object getUUID(int requestedType) {
		if (uuidObject != null) {
			if (requestedType == UUIDProvider.UUID_TYPE_BYTE_ARRAY) {
				if (UUIDProvider.isTypeByteArray()) {
					return uuidObject;
				} else {
					return UUIDProvider
							.translateStringToBytes((String) uuidObject);
				}
			} else {
				if (UUIDProvider.isTypeByteArray()) {
					return UUIDProvider
							.translateBytesToString((byte[]) uuidObject);
				} else {
					return uuidObject;
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * Method generate
	 */
	public void generate() {
		setUUID(UUIDProvider.generateUUIDasBytes());
	}

	/**
	 * Method equals
	 * 
	 * @param uuid
	 *            FWUUID
	 * @return boolean
	 */
	public boolean equals(FWUUID uuid) {
		if (UUIDProvider.isTypeByteArray()) {
			byte[] src = (byte[])uuidObject;
			byte[] dst = uuid.getUUIDAsBytes();
			boolean equality = true;
			for(int i = 0; i < 16 && equality; i++){
				equality = (src[i] == dst[i]);
			}
			return equality;
		} else {
			return ((String) uuidObject).equals((String) uuid.getUUID());
		}
	}

	/**
	 * Method hashCode
	 * 
	 * @return int
	 */
	public int hashCode() {
		if(hashCode == 0){
			byte[] b = getUUIDAsBytes();
			for(int i = 0; i < 16; i++){
				hashCode = hashCode * 31 + b[i];
			}
		}
		return hashCode;
	}
}
