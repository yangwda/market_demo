package cn.yj.market.frame.util;

import org.apache.commons.lang.math.RandomUtils;

public final class SUtils {
	
	public static final String P = "TMS00727" ;
	public static final String CHAR_ENCODE_STRING = "UTF-8" ;

	public static void main(String[] arg) throws Exception {
		String kString = randomKey() ;
		System.out.println("====<>>> key is :" + kString);
		System.out.println("====<>>> key length is :" + kString.length());
		
		String tString = "dd" ;
		System.out.println("==>>" + tString.getBytes().length);
		System.out.println("==>>" + (new String(tString.getBytes(),"utf-8")).getBytes().length);
		System.out.println("==>>" + (new String(tString.getBytes(),"gbk")).getBytes().length);
	}
	
	
	public static String randomKey() {
		Long c = System.currentTimeMillis() ;
		return formatKey(c.toString() ,128) ;
	}
	public static String formatKey(String key ,int keyLength) {
		if (org.apache.commons.lang.StringUtils.isEmpty(key)) {
			key = String.valueOf(System.currentTimeMillis()) ;
		}
		int kl = key.length() ;
		if (keyLength <= key.length()) {
			keyLength = (8 * kl) ;
		}
		StringBuilder r = new StringBuilder(key) ;
		for(int i=keyLength ;i > kl ;i--){
			r.append(RandomUtils.nextInt(10)) ;
		}
		return r.toString() ;
	}
	/**
	 * byte数组转16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转byte数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static final String ea(String str) {
		if (str == null) {
			return null ;
		}
		return parseByte2HexStr(DES.encrypt(str, P)) ;
	}
	public static final String da(String str) {
		if (str == null) {
			return null ;
		}
		return DES.decrypt(str, P) ;
	}
	public static final String MD5(String src) {
		if (src == null) {
			return null ;
		}
		try {
			return parseByte2HexStr(MD5.md5(src)) ;
		} catch (Exception e) {
			return null ;
		}
	}
}
