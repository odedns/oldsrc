package test.uuid;

import org.doomdark.uuid.EthernetAddress;
import org.doomdark.uuid.NativeInterfaces;


public class TestMac {

	public static void main(String[] args) {
		EthernetAddress primary = NativeInterfaces.getPrimaryInterface();
		System.out.println(primary.toString());
	}
}
