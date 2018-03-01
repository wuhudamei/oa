package cn.damei.utils.des;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5 {
	public static String encryptKey(String src) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		byte[] b = md.digest(src.getBytes());
		StringBuffer buf = new StringBuffer("");
		int i = 0;
		for (int offset = 0, l = b.length; offset < l; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
	public static String hash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(data.getBytes("utf-8"));
		return encodeHex(digest.digest());
	}
	public static String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16)
				buf.append("0");
			buf.append(Integer.toHexString(bytes[i] & 0xff));
		}
		return buf.toString();
	}
	//该方法将你输入的字符串，通过md5加密，返回一个加密後的字符串
	public static String MD5Encrypt(String inStr) {  
		MessageDigest md = null;  
	    String outStr = null;  
	    try {   
	    	//可以选中其他的算法如SHA 
	    	md = MessageDigest.getInstance("MD5");           
	    	byte[] digest = md.digest(inStr.getBytes());       
	    	//返回的是byet[]，要转化为String存储比较方便  
	    	outStr = bytetoString(digest);  
	    }catch (NoSuchAlgorithmException nsae) {   
	    	nsae.printStackTrace();  
	    }  
	    return outStr;
	} 
	public static String bytetoString(byte[] digest) {  
		String str = "";  
		String tempStr = "";  
		for (int i = 1; i < digest.length; i++) {   
			tempStr = (Integer.toHexString(digest[i] & 0xff));   
			if (tempStr.length() == 1) {    
				str = str + "0" + tempStr;   
			}
			else {    
				str = str + tempStr;   
			}  
		}  
		return str.toLowerCase();
	}
	public static void main(String[] args) {
		//c615d2b662a6ca4e9eba3952c046aa0d
		System.err.println(encryptKey("9998545boLJFSIOULKJfdlkfsu"));
		//		System.err.println(encryptKey("9998545boLJFSIOULKJfdlkfsu" + System.currentTimeMillis()));
	}
}
