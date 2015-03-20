package com.comdosoft.financial.manage.utils;

import java.util.Random;

public abstract class StringUtils {
	
	private static final char[] chars = "1234567890abcdef".toCharArray();
	private static final Random random = new Random();
	
	public static String randomString(int length){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<length;++i){
			int pos = random.nextInt(chars.length);
			builder.append(chars[pos]);
		}
		return builder.toString();
	}

}
