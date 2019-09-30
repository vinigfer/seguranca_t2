/*
Programa usado para decifrar uma mensagem criptografada com AES
e modo de operacao CBC ou CTR. Valores de entrada configurados
diretamente nas primeiras linhas da funcao "main".
Autor: Vinicius Ferreira
Data: 3/Outubro/2019
*/

package crackAvelino;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class crackAvelino {

	//Funcao para converter um array de bytes para uma String em hexadecimal
    public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}
	
    //Funcao para converter de uma String em hexadecimal para um array de bytes
	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
	//Gera uma chave a partir de uma String. Converte a mesma para array de bytes,
	//usando apenas os primeiros 16 bytes (como usamos AES 128)
	public static SecretKeySpec getSecretKey(String passwd) throws Exception {
        byte[] dataBytes = toByteArray(passwd);
        dataBytes = Arrays.copyOfRange(dataBytes, 0, 16);
        return new SecretKeySpec(dataBytes, "AES");
	}
	
	public static void main(String[] args) throws Exception {
		//Informacoes de entrada para descriptografar. Substituir blockMode por
		//"AES/CTR/NoPadding" ou "AES/CBC/PKCS5Padding" conforme o modo de operacao
		String blockMode = "AES/CTR/NoPadding";
		String keyAsString = "C27A59A6AEB79853D159DD2F5486CA74";
		String cipheredText = "656E6372797074696F6E496E6956656346D6516BBEEC5DDA5FCFD5B90FEA3F65EB68FE09CCC2B6672B1E9BC697FEC38DF6F5160BAECA755F4AE60037C923E0403DDD2FA52FCAED0DA90B";
		
		//Remove o IV do texto cifrado
        String initializationVectorAsString = cipheredText.substring(0, 32);
        cipheredText = cipheredText.substring(32);
        
        //Converte IV e chave para tipos de dados esperados pelos metodos da biblioteca
        byte[] ivAsBytes = toByteArray(initializationVectorAsString);
        IvParameterSpec ivParameter = new IvParameterSpec(ivAsBytes);
        SecretKeySpec secretKeyParameter = getSecretKey(keyAsString);
        
        //Configuracoes para o tipo de operacao que sera realizada
        Cipher cipher = Cipher.getInstance(blockMode);
        cipher.init(Cipher.DECRYPT_MODE, secretKeyParameter, ivParameter);

        //Descriptografa a mensagem, imprimindo a mesma no console
        byte[] deciphered = cipher.doFinal(toByteArray(cipheredText));
        System.out.println("Mensagem decifrada: " + new String(deciphered));
	}

}
