import java.security.MessageDigest; // http://www.javasoft.com/products/jdk/1.1/docs/api/java.security.MessageDigest.html
import javax.commerce.util.BASE64Encoder; // http://www.javasoft.com/products/commerce/ jecf.jar
import javax.commerce.util.BASE64Decoder;
// An alternative: import util.BASE64Encoder; // http://professionals.com/~cmcmanis/java/encoders/

class pwdSHA {

	public static void main (String[] args)
		throws  java.io.IOException,
				java.security.NoSuchAlgorithmException
	{
		if (args.length < 1) {
			String myName = "pwdSHA";
			System.out.print (
				"usage: " + myName + " [-v] [-s salt] password ...\n" +
				"   or: " + myName + " [-v] -c digest password ...\n" +
				"       salt is in hexadecimal notation.\n" +
				"       digest contains SHA-1 hash and salt, base64 encoded.\n");
			return;
		}
		int i = 0;
		boolean verbose = false;
		if (args[i].equals ("-v")) {
			++i;
			verbose = true;
		}
		MessageDigest sha = MessageDigest.getInstance ("SHA-1");

		if (args[i].equals ("-c")) { // check passwords against digest
			++i;
			String digest = args[i++];
			if (digest.regionMatches (true, 0, "{SHA}", 0, 5)) {
				digest = digest.substring (5); // ignore the label
			} else if (digest.regionMatches (true, 0, "{SSHA}", 0, 6)) {
				digest = digest.substring (6); // ignore the label
			}
			BASE64Decoder base64 = new BASE64Decoder();
			byte[][] hs = split (base64.decodeBuffer (digest), 20);
			byte[] hash = hs[0];
			byte[] salt = hs[1];
			if (verbose) System.out.println (toHex (hash) + " " +
											 toHex (salt));
			for (; i < args.length; ++i) {
				sha.reset();
				sha.update (args[i].getBytes());
				sha.update (salt);
				byte[] pwhash = sha.digest();
				if (verbose) System.out.println (toHex (pwhash));
				if (! sha.isEqual (hash, pwhash)) {
					System.out.println ("doesn't match: " + args[i]);
				}
			}

		} else { // generate digest from passwords
			byte[] salt = {};
			if (args[i].equals ("-s")) {
				++i;
				salt = fromHex (args[i++]);
				if (verbose) System.out.println (toHex (salt));
			}
			String label = (salt.length > 0) ? "{SSHA}" : "{SHA}";
			BASE64Encoder base64 = new BASE64Encoder();

			for (; i < args.length; ++i) {
				sha.reset();
				sha.update (args[i].getBytes());
				sha.update (salt);
				byte[] pwhash = sha.digest();
				if (verbose) System.out.print (toHex (pwhash) + " ");
				System.out.println (label + base64.encode (concatenate (pwhash, salt)));
			}
		}
	}

	private static byte[] concatenate (byte[] l, byte[] r) {
		byte[] b = new byte [l.length + r.length];
		System.arraycopy (l, 0, b, 0, l.length);
		System.arraycopy (r, 0, b, l.length, r.length);
		return b;
	}

	private static byte[][] split (byte[] src, int n) {
		byte[] l, r;
		if (src.length <= n) {
			l = src;
			r = new byte[0];
		} else {
			l = new byte[n];
			r = new byte[src.length - n];
			System.arraycopy (src, 0, l, 0, n);
			System.arraycopy (src, n, r, 0, r.length);
		}
		byte[][] lr = {l, r};
		return lr;
	}

	private static String hexits = "0123456789abcdef";

	private static String toHex( byte[] block ) {
		StringBuffer buf = new StringBuffer();
		for ( int i = 0; i < block.length; ++i ) {
			buf.append( hexits.charAt( ( block[i] >>> 4 ) & 0xf ) );
			buf.append( hexits.charAt( block[i] & 0xf ) );
		}
		return buf + "";
	}

	private static byte[] fromHex( String s ) {
		s = s.toLowerCase();
		byte[] b = new byte [(s.length() + 1) / 2];
		int j = 0;
		int h;
		int nybble = -1;
		for (int i = 0; i < s.length(); ++i) {
			h = hexits.indexOf (s.charAt(i));
			if (h >= 0) {
				if (nybble < 0) {
					nybble = h;
				} else {
					b[j++] = (byte)((nybble << 4) + h);
					nybble = -1;
				}
			}
		}
		if (nybble >= 0) {
			b[j++] = (byte)(nybble << 4);
		}
		if (j < b.length) {
			byte[] b2 = new byte [j];
			System.arraycopy (b, 0, b2, 0, j);
			b = b2;
		}
		return b;
	}
}