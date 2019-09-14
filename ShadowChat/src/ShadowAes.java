import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ShadowAes {
	static String IV			= "AAAAAAAAAAAAAAAA";
	static String plaintext		= "test text 123\000\000\000";
	static String encryptionKey	= "0123456789abcdef";

	public byte[] encrypt(String plainText, String encryptionKey)
			throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"),
				"AES");
		cipher.init(1, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public String decrypt(byte[] cipherText, String encryptionKey)
			throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"),
				"AES");
		cipher.init(2, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(cipherText), "UTF-8");
	}

	public String decryptMessage(String str) throws Exception
	{
		byte[] temp = hexStringToByteArray(str);
		return decrypt(temp, ShadowServer.login.key).trim();
	}

	public String padRight(String s, int n)
	{
		return String.format("%1$-" + n + "s", new Object[] { s });
	}

	public byte[] hexStringToByteArray(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	public String bytesToHex(byte[] bytes)
	{
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0xF];
		}
		return new String(hexChars);
	}
}
