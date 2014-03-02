package com.siemens.ctbav.intership.shop.util.operator;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.siemens.ctbav.intership.shop.binary.operator.Base64;

public class AES {

	
	static Log log = LogFactory.getLog(AES.class);
	
	private static byte[]key={0x74,0x68,0x69,0x49,0x73,0x41,0x53,0x65,0x63,0x72,0x55,0x74,0x4b,0x79,0x79,0x79};
	
	public static String encrypt(String strToEncrypt){
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String  encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
		//	String encrypted = new String(encryptedString,"UTF-8");
			return encryptedString;
		} catch (Exception e) {
			log.error("Error while encrypting", e);
		}
		
		return null;
	}
	
	public static String decrypt(String strToDecrypt){
		
		try {
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
			return decryptedString;
		//	final byte[] encryptedString = Base64.encodeBase64(cipher.doFinal(strToDecrypt.getBytes("UTF-8")));
		//	String encrypted = new String(encryptedString,"UTF-8");
		//	return encrypted;
		} catch (Exception e) {
			log.error("Error while encrypting", e);
		}
		return null;
	}
}
