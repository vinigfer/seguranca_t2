package crackAvelino;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class crackAvelino {

    public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}
	
	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
	public static SecretKeySpec getSecretKey(String passwd) throws Exception {
	    byte[] dataBytes = passwd.getBytes();
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(dataBytes, 0, passwd.length());
	    byte[] mdbytes = md.digest();
	    return new SecretKeySpec(Arrays.copyOfRange(mdbytes, 0, 16), "AES");
	}
	
	
	public static void main(String[] args) throws Exception {
		String chave = "abcdefghi";
		String textoCifrado = "4615339DFC32CE316740B9459B87C8E4215006A0EC05EF68492BBCAFBD2935E38636E744C60B113531BDA0394AC0B74B442FCD20588D488CA8A51A64BFB69D5E";
		
		SecretKeySpec skeySpec = getSecretKey(chave);
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] deciphered = cipher.doFinal(toByteArray(textoCifrado));
	    System.out.println("Mensagem cifrada: " + (new String(deciphered)));
	}

}
