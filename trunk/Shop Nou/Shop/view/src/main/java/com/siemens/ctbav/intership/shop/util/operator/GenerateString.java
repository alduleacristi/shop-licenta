package com.siemens.ctbav.intership.shop.util.operator;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateString {

	public static String randomPassword() {
		char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
				.toCharArray();
		Random random = new SecureRandom();
		char[] result = new char[10];
		for (int i = 0; i < result.length; i++) {
			int randomCharIndex = random.nextInt(characterSet.length);
			result[i] = characterSet[randomCharIndex];
		}
		return new String(result);
	}

}
