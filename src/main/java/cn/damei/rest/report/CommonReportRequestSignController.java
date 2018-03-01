package cn.damei.rest.report;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rocoinfo.weixin.util.HttpUtils;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.utils.report.SignUtils;
@RestController
@RequestMapping("/api/reports")
public class CommonReportRequestSignController {
	@RequestMapping("/signReq")
	public Object signAndProxyRequest(HttpServletRequest request){
		try{
		String finalUrlWithParams = buildAimUrlWithParams(request);
		if(StringUtils.isEmpty(finalUrlWithParams)){
			return StatusDto.buildFailure("目标接口路径未知，无法发起请求");
		}
		else{
			String result = HttpUtils.get(finalUrlWithParams);
			JSONObject rtJsonObj = new JSONObject(result);
			if(rtJsonObj.getInt("code") == 1){
				return StatusDto.buildSuccess("成功",rtJsonObj.getString("data"));
			}
			else{
				return StatusDto.buildFailure("未返回数据");
			}
		}
		}
		catch(Exception exp){
			return StatusDto.buildFailure("发生异常，请求失败");
		}
	}
	private String buildAimUrlWithParams(HttpServletRequest request){
		String finalUrl = "";
		try{
		StringBuilder paramSignSb = new StringBuilder();
		StringBuilder paramSb = new StringBuilder();
		String aimUrl = request.getParameter("aimUrl");
		if(StringUtils.isBlank(aimUrl)){
			return "";
		}
		aimUrl += "?1=1";
		Map<String, String[]> reqParams = request.getParameterMap();
		//过滤一下，将aimUrl过滤掉，不能直接reqParam.remove()，应为reqParam被上锁了
		Map<String, Object> requestParam = new HashMap<String, Object>();
		for(String reqKey : reqParams.keySet()){
			if(reqKey.equals("aimUrl") || reqKey.equals("_")){
				continue;
			}
			requestParam.put(reqKey, reqParams.get(reqKey));
		}
		List<String> paramNames = new ArrayList<String>(requestParam.size());
		paramNames.addAll(requestParam.keySet());
		//对所有的参数Key按字母进行排序
		Collections.sort(paramNames);
		for (String paramName : paramNames) {
			String paramStr = StringUtils.isEmpty(request.getParameter(paramName))?"":request.getParameter(paramName);
			// 按(secretkey1value1key2value2…. secret)进行被加密的参数的拼接
			paramSignSb.append(paramName).append(paramStr);
			//参数
			paramSb.append("&").append(paramName).append("=").append(paramStr);
		}
		//将签名加入参数中
		paramSb.append("&sign=").append(SignUtils.generateSign(PropertyHolder.getRepotSignAppId(), PropertyHolder.getRepotSignSecret(), paramSignSb.toString()));
		finalUrl = aimUrl + paramSb.toString();
		return finalUrl;
		}
		catch(Exception exp){
			return "";
		}
	}
}
