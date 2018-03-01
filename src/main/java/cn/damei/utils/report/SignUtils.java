package cn.damei.utils.report;
public class SignUtils {
    public static final String SEPARATOR = "_";
	public static String generateSign(String appid, String appSercer,String paramStr) {
		if (appid == null) {
			throw new SignException("The appid can't be null !");
		}
		if (appSercer == null) {
			throw new SignException("The appSercer can't be null !");
		}
		long timestamp = System.currentTimeMillis();
		String toSign = timestamp + paramStr + appSercer;
		return appid + SEPARATOR + timestamp + SEPARATOR + Md5Utils.generate(toSign).toUpperCase();
	}
}
