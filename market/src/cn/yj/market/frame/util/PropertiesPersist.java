package cn.yj.market.frame.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.springframework.util.DefaultPropertiesPersister;

public class PropertiesPersist extends DefaultPropertiesPersister {
	public void load(Properties props, InputStream is) throws IOException {

		Properties properties = new Properties();
		properties.load(is);

		//if ((properties.get("password") != null)) {
			/* 这里通过解密算法，得到你的真实密码，然后写入到properties中 */
			// String password = getRealPassword( decrypter ,
			// properties.getProperty("password") );
			// properties.setProperty("password" , password);
		//}
		for(Object key : properties.keySet()){
			String sKey = (String) key ;
			String sValue = properties.getProperty(sKey) ;
			properties.setProperty(sKey, SUtils.da(sValue)) ;
		}
		
		OutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			properties.store(outputStream, "");
			is = outStream2InputStream(outputStream);
			super.load(props, is);
		} catch (IOException e) {
			throw e;
		} finally {
			outputStream.close();
		}
	}

	private InputStream outStream2InputStream(OutputStream out) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(
				bos.toByteArray());
		return swapStream;
	}
	
	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		Properties oProp = new Properties() ;
//		String filePath = "D:/workspace_wms/codespace/tms/resources_src/jdbc_src.properties" ;
//		String filePath = "D:/workspace_wms/codespace/tms/resources_src/jdbc_encrypt.properties" ;
//		String oFilePath = "D:/workspace_wms/codespace/tms/resources_src/jdbc_encrypt.properties" ;
		String filePath = "D:/workspace_wms/codespace/tms/resources_src/mail_src.properties" ;
//		String filePath = "D:/workspace_wms/codespace/tms/resources_src/jdbc_encrypt.properties" ;
		String oFilePath = "D:/workspace_wms/codespace/tms/resources_src/mail_encrypt.properties" ;
		InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		OutputStream fos = new FileOutputStream(oFilePath);
		prop.load(in);
		Set keyValue = prop.keySet();
		System.out.println("---------------->> ");
		for (Iterator it = keyValue.iterator(); it.hasNext();){
			String key = (String) it.next();
			String vString = prop.getProperty(key) ;
//			String vString = SUtils.da(prop.getProperty(key)) ;
			System.out.println(key + "=" + vString);
			oProp.setProperty(key, SUtils.ea(vString)) ;
		}
		oProp.store(fos, "encrypt resource..");
	}
}
