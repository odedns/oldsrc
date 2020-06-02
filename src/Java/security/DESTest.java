
import java.io.*;
import javax.crypto.*;
import java.security.*;

public class DESTest {

	public static void main(String argv[])
	{
		String s = "oded";
		try {
			// list providers.
			Provider p[] = Security.getProviders();
			for(int i=0; i<p.length; ++i) {
				System.out.println("Provider : " + p[i].getName());
			}

			// generate a DES key.
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			
			Key pk = kg.generateKey();
			Cipher c = Cipher.getInstance("DES");
			c.init(Cipher.ENCRYPT_MODE,pk);
			byte enc[] = c.doFinal(s.getBytes());

			// store the key.
			KeyStore ks = KeyStore.getInstance ("JCEKS","SunJCE");
			char pass[] = { 'm','y','k','e','y' };
			ks.load(null,pass);
			ks.setKeyEntry("mykey",pk,pass,null);
			ks.store(new FileOutputStream("mykey.sec"),pass);
			// now load the key from the file.
			ks.load(new FileInputStream("mykey.sec"),pass);
			Key pk2 = ks.getKey("mykey",pass);
			// decrypt using the key.
			c.init(Cipher.DECRYPT_MODE,pk2);
			byte dec[] = c.doFinal(enc);
			String s2 = new String(dec);
			System.out.println("Encrypt = " + s);
			System.out.println("DEncrypt = " + s2);
		} catch(Exception e) {
			e.printStackTrace();
		}


	}
}
