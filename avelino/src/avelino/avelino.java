package avelino;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class avelino {

	private static final String initVector = "encryptionIntVec";
	
	public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
	}    

	public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
	}

	public static SecretKeySpec getSecretKey (String passwd) throws Exception {
        byte[] dataBytes = passwd.getBytes();
    
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes, 0, passwd.length());
        byte[] mdbytes = md.digest();

        return new SecretKeySpec(Arrays.copyOfRange(mdbytes, 0, 16), "AES");
	}


	public static void main(String[] args) throws Exception {
		String chave = "abcdefghi";
		String textoClaro = "MeuTextoClaroQueSeraCifrado.Elediz:AvelinoEhUmBundao";
		
        SecretKeySpec skeySpec = getSecretKey(chave);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(textoClaro.getBytes());
        System.out.println("Mensagem cifrada: " + toHexString(encrypted));
	}
}
