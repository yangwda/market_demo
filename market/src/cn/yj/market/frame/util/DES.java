package cn.yj.market.frame.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public final class DES {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tString = "helle" ;
		System.out.println(tString);
		String fString = SUtils.parseByte2HexStr(encrypt(tString, SUtils.P));
		System.out.println(fString);
		String eString = decrypt(fString, SUtils.P) ;
		System.out.println(eString);
	}

	/**
	 * 加密
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	public static byte[] encrypt(String src, String password) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			byte[] datasource = src.getBytes(SUtils.CHAR_ENCODE_STRING) ;
			DESKeySpec desKey = new DESKeySpec(password.getBytes(SUtils.CHAR_ENCODE_STRING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static String decrypt(String src, String password) {
		if (src == null) {
			return null ;
		}
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			DESKeySpec desKey = new DESKeySpec(password.getBytes(SUtils.CHAR_ENCODE_STRING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			byte[] result =  cipher.doFinal(SUtils.parseHexStr2Byte(src));
			return new String(result ,SUtils.CHAR_ENCODE_STRING) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

}
