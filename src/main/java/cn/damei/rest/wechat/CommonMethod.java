package cn.damei.rest.wechat;
import java.util.HashMap;
import java.util.Map;
import cn.damei.Constants;
public class CommonMethod {
    public static Map<String,Object> retMessage(Long code,String message){
    	Map<String,Object> retMessage = new HashMap<String,Object>();
    	retMessage.put(Constants.QRCODE_OPERATION_CODE, code);
    	retMessage.put(Constants.QRCODE_OPERATION_MESSAGE, message);
    	return retMessage;
    }
}
