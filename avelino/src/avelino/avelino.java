/*
Programa usado para cifrar uma mensagem com criptografia AES
e modo de operacao CBC ou CTR. Valores de entrada configurados
diretamente nas primeiras linhas da funcao "main".
Autor: Vinicius Ferreira
Data: 3/Outubro/2019
*/

package avelino;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class avelino {

	//Funcao para converter um array de bytes para uma String em hexadecimal
	public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
	}

	//Funcao para converter de uma String em hexadecimal para um array de bytes
	public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
	}

	//Gera uma chave a partir de uma String
	//Retorna a chave secreta a partir dos 16 bytes da funcao hash aplicada sobre a string
	public static SecretKeySpec getSecretKey (String passwd) throws Exception {
        byte[] dataBytes = passwd.getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes, 0, passwd.length());
        byte[] mdbytes = md.digest();

        System.out.println("Chave usada: " + toHexString(Arrays.copyOfRange(mdbytes, 0, 16)));
        return new SecretKeySpec(Arrays.copyOfRange(mdbytes, 0, 16), "AES");
	}

	public static void main(String[] args) throws Exception {
		//Informacoes de entrada para criptografar. Substituir blockMode por
		//"AES/CTR/NoPadding" ou "AES/CBC/PKCS5Padding" conforme o modo de operacao
		String blockMode = "AES/CTR/NoPadding";
		String keyAsString = "MinhaChaveQualquer";
		String clearText = "This is a sentence to be encrypted using AES and CTR mode.";
		String initializationVectorAsString = "encryptionIniVec";
		
		//Converte IV e chave para tipos de dados esperados pelos metodos da biblioteca
		byte[] ivAsBytes = initializationVectorAsString.getBytes("UTF-8");
		IvParameterSpec ivParameter = new IvParameterSpec(ivAsBytes);
		SecretKeySpec secretKeyParameter = getSecretKey(keyAsString);

		//Configuracoes para o tipo de operacao que sera realizada
        Cipher cipher = Cipher.getInstance(blockMode);        
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyParameter, ivParameter);
        
        //Criptografa a mensagem, imprimindo a mesma no console com o IV como prefixo
        byte[] encrypted = cipher.doFinal(clearText.getBytes());
        System.out.println("Mensagem cifrada: " + toHexString(ivAsBytes) + toHexString(encrypted));
	}
}
