package cn.damei.utils;
import java.util.UUID;
public class UUIDUtils {
	public static String generateJobNum() {
		return UUID.randomUUID().toString();
	}
}
