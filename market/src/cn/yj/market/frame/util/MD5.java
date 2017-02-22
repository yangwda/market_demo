package cn.yj.market.frame.util;

import java.security.MessageDigest;

public class MD5 {

	public static void main(String[] args) throws Exception {
		String s = new String("--");
		System.out.println("得得得" + s);
		System.out.println("MD5得得得" + SUtils.parseByte2HexStr(md5(s)));

	}

	public static byte[] md5(String strObj) throws Exception {
		if (strObj == null) {
			return null ;
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(strObj.getBytes());
    }

}
