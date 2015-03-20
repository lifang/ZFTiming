package com.comdosoft.financial.manage.joint.hanxin;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public abstract class RSACoder {
	
	public static final Logger LOG = LoggerFactory.getLogger(RSACoder.class);
	
	public static final String KEY_ALGORITHM = "RSA";

	// 公钥加密
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	// 公钥加密2
	public static String encryptByPublicKey(String data, String key) throws Exception {
		byte[] signByte=encryptByPublicKey(data.getBytes(), Base64.decodeBase64(key));
		return Base64.encodeBase64String(signByte);
	}

}
