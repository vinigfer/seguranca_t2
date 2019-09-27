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
        //byte[] dataBytes = passwd.getBytes();
        byte[] dataBytes = toByteArray(passwd);

        // MessageDigest md = MessageDigest.getInstance("SHA-256");
        // md.update(dataBytes, 0, passwd.length());
        // byte[] mdbytes = md.digest();

        //return new SecretKeySpec(Arrays.copyOfRange(mdbytes, 0, 16), "AES");
        return new SecretKeySpec(dataBytes, "AES");
	}
	
	public static void main(String[] args) throws Exception {
		String chave = "36f18357be4dbd77f050515c73fcf9f2";
		String textoCifrado = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
		
//		SecretKeySpec skeySpec = getSecretKey(chave);
//	    Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
//	    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//	    byte[] deciphered = cipher.doFinal(toByteArray(textoCifrado));
//	    System.out.println("Mensagem decifrada: " + (new String(deciphered)));
	    
	    
        SecretKeySpec skeySpec = getSecretKey(chave);

        String input = textoCifrado;
        input = input.substring(0, 32);
        byte[] iv = toByteArray(input);
        
        IvParameterSpec ivp = new IvParameterSpec(iv);

        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivp);

        byte[] deciphered = cipher.doFinal(toByteArray(textoCifrado.substring(32)));
        System.out.println("Mensagem decifrada: " + new String(deciphered));
	}

}
