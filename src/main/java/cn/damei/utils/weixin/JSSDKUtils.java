package cn.damei.utils.weixin;
import com.rocoinfo.weixin.config.ParamManager;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.UUID;
public class JSSDKUtils {
    public static HashMap<String, String> getConfig(String url, String jsapi_ticket){
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String  string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp  + "&url=" + url;
        MessageDigest crypt = null;
        HashMap<String, String> jssdk = new HashMap<String, String>();
        try{
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            String signature = byteToHex(crypt.digest());
            jssdk.put("appId", ParamManager.getAppid());
            jssdk.put("timestamp", timestamp);
            jssdk.put("nonceStr", nonce_str);
            jssdk.put("signature", signature);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jssdk;
    }
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
