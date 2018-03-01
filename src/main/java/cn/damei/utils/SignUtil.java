package cn.damei.utils;
import com.alibaba.fastjson.JSONObject;
import cn.damei.common.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
@SuppressWarnings("all")
public class SignUtil {
	private static Logger log = LoggerFactory.getLogger(SignUtil.class);
	public static boolean isNull(String str) {
		if (null == str || str.equalsIgnoreCase("null") || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	public static String getKey(String[] paramArr,String appoIntKey){
		Arrays.sort(paramArr);
		String s="";
		for (String str : paramArr) {
			String[] split = str.split("=");
			s+=split[1];
		}
		String paramString=s+appoIntKey;
		String key2="";
		key2 = MD5Util.getMD5Code(paramString).toUpperCase();
		return key2;
	}
	public static String getMD5(String plainText) throws UnsupportedEncodingException {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes("UTF-8"));//¶Ô×Ö·û´®½øÐÐ±àÂë
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Ã»ÓÐmd5Õâ¸öËã·¨£¡");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16½øÖÆÊý×Ö
		Integer md5Length =md5code.length();
		// Èç¹ûÉú³ÉÊý×ÖÎ´Âú32Î»£¬ÐèÒªÇ°Ãæ²¹0
		for (int i = 0; i < 32 -md5Length ; i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
	public static String genSignData(JSONObject jsonObject) {
		if (jsonObject == null) {
			return "";
		}
		StringBuffer content = new StringBuffer();
		// 按照key做首字母升序排列
		@SuppressWarnings("unchecked")
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if ("key".equalsIgnoreCase(key)) {
				continue;
			}
			Object object = jsonObject.get(key);
			if (object == null || object instanceof Collection) {
				continue;
			} else {
				String value = jsonObject.getString(key);
				// 空串不参与签名
				if (isNull(value)) {
					continue;
				}
				content.append((i == 0 ? "" : "") + value);
			}
		}
		String signSrc = content.toString();
		return signSrc;
	}
	public static String addSignMD5(String sign_src) {
		log.info("加签原串为：" + sign_src);
		try {
			String md5String = MD5Util.getMD5Code(sign_src);
			log.info("MD5加密以后为：" + md5String);
			return md5String;
		} catch (Exception e) {
			log.error("MD5加签名异常" + e.getMessage());
			return "";
		}
	}
	public static boolean checkSignMD5AndKey(JSONObject reqObj,String[] parameterArr, String md5_key,String nativeKey) {
		if (reqObj == null) {
			return false;
		}
		String sign = reqObj.getString("key");
		// 生成待签名串
		String sign_src = genSignData(reqObj);
		log.info("接口调用方[{}]的待签名原加密串为{}" , sign_src);
		log.info("接口调用方[{}]新生成的加密串为{}" , sign);
		sign_src +=  md5_key;
		String key=getKey(parameterArr,nativeKey);
//		String sign_new = MD5Util.getMD5Code(sign_src);
		log.info("本系统生成的原串为{}", sign_src);
		log.info("本系统生成的加密串为{}", key);
		if (sign.equalsIgnoreCase(key)) {
			log.info("MD5签名验证通过");
			return true;
		} else {
			log.info("MD5签名验证未通过");
			return false;
		}
	}
}
