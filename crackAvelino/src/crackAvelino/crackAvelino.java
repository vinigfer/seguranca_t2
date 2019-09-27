package crackAvelino;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class crackAvelino {
	
	private static final String initVector = "encryptionIntVec";

    public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}
	
	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
	public static SecretKeySpec getSecretKey(String passwd) throws Exception {
//	    byte[] dataBytes = passwd.getBytes();
//	    MessageDigest md = MessageDigest.getInstance("SHA-256");
//	    md.update(dataBytes, 0, passwd.length());
//	    byte[] mdbytes = md.digest();
		byte[] mdbytes = toByteArray("36f18357be4dbd77f050515c73fcf9f2");
	    return new SecretKeySpec(Arrays.copyOfRange(mdbytes, 0, 16), "AES");
	}
	
	public static void main(String[] args) throws Exception {
		String chave = "abcdefghi";
		String textoCifrado = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
		
		SecretKeySpec skeySpec = getSecretKey(chave);
	    Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
	    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	    byte[] deciphered = cipher.doFinal(toByteArray(textoCifrado));
	    System.out.println("Mensagem decifrada: " + (new String(deciphered)));
	}

}
