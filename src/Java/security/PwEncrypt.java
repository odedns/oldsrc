
import uk.co.demon.windsong.crypt.mda.*;

class PwEncrypt {


	/**
	 * create a digested password, to pass to the authenticateUser method.
	 * @param clearPassword  the clear text password
	 * @return a digested password
	 * @exception InfraException    
	 * when a NoSuchAlgorithmException is thrown
	 */
	/*
     	public static String createPassword(String clearPassword) 
	{
         try {
             MessageDigest md = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
             md.update(clearPassword.getBytes());
             byte[] digestedPassword = md.digest();
             BASE64Encoder enc = new BASE64Encoder();
             String resultingPassword = "{SHA}"+enc.encode(digestedPassword);
             return resultingPassword;
         } catch (NoSuchAlgorithmException e) {
		 e.printStackTrace();
         }

     	}
	*/

	public static void main(String argv[])
	{
		if(argv.length < 1) {
			System.out.println("usage PwEncrypt <password>");
			System.exit(1);
		}
		String pass = argv[0];
		byte b[] = pass.getBytes();
		SHA1 s = new SHA1(true);
		s.update(b,0, b.length);
		b = s.digest();
		System.out.println("encrypted = ");
		for(int i=0; i < b.length; ++i) {
			System.out.print(b[i]);
		}
		System.out.print('\n');

	}
}
