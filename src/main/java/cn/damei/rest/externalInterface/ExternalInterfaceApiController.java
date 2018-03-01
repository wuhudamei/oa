package cn.damei.rest.externalInterface;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.entity.employee.Employee;
import cn.damei.service.employee.EmployeeService;
import cn.damei.utils.JsonUtils;
@RestController
@RequestMapping(value = "/api/interface")
public class ExternalInterfaceApiController extends BaseController {
	private static final String INTERFACE_SOURCE = "source";
	private static final String INTERFACE_ACCOUNT = "account";
	private static final String INTERFACE_NAME = "name";
	private static final String INTERFACE_MOBILE = "mobile";
	private static final String CHECK_CODE = "1";
    private static String[] SCM_SOURCE = {"SCM"};//允许进入的接口
	@Autowired
	private EmployeeService employeeService;
	@RequestMapping(value = "createAccount", method = RequestMethod.POST)
	public String createAccount(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			Map<String, String> jsonMap = JsonUtils.fromJson(messageParam, Map.class);
			String checkMessage = checkParams(jsonMap);
			if (!CHECK_CODE.equals(checkMessage)) {
				return checkMessage;
			}
			String accountSource = jsonMap.get(INTERFACE_SOURCE);
			String account = jsonMap.get(INTERFACE_ACCOUNT);
			String name = jsonMap.get(INTERFACE_NAME);
			String mobile = jsonMap.get(INTERFACE_MOBILE);
			List<String> list=Arrays.asList(SCM_SOURCE);
			if (!list.contains(accountSource)) {
				return responseJsonStr("0", "Interface without authority");
			}
			Employee employee = new Employee();
			employee.setJobNum(account);
			employee.setName(name);
			employee.setMobile(mobile);
			employee.setAccountSource(accountSource);
			employee.setCreateUser(-1L);
			employeeService.insert(employee);
			return responseJsonStr("1", "success!");
		} else {
			return responseJsonStr("0", "Parameter Not Empty!");
		}
	}
	private String checkParams(Map<String, String> jsonMap) {
		if (jsonMap != null) {
			if (jsonMap.get(INTERFACE_SOURCE) == null || jsonMap.get(INTERFACE_ACCOUNT) == null
					|| jsonMap.get(INTERFACE_NAME) == null || jsonMap.get(INTERFACE_MOBILE) == null) {
				return responseJsonStr("0", "Request Parameter Incomplete!");
			}
			if (jsonMap.get(INTERFACE_ACCOUNT) == null || StringUtils.isBlank(jsonMap.get(INTERFACE_ACCOUNT))) {
				return responseJsonStr("0", "Parameter [account] Not Null !");
			}
			if (jsonMap.get(INTERFACE_NAME) == null || StringUtils.isBlank(jsonMap.get(INTERFACE_NAME))) {
				return responseJsonStr("0", "Parameter [name] Not Null!");
			}
			if (jsonMap.get(INTERFACE_MOBILE) == null || StringUtils.isBlank(jsonMap.get(INTERFACE_MOBILE))) {
				return responseJsonStr("0", "Parameter [mobile] Not Null!");
			}
			if (jsonMap.get(INTERFACE_SOURCE) == null || StringUtils.isBlank(jsonMap.get(INTERFACE_SOURCE))) {
				return responseJsonStr("0", "Parameter [source] Not Null!");
			}
		} else {
			return responseJsonStr("0", "Request Parameter Error!");
		}
		return CHECK_CODE;
	}
	private String responseJsonStr(String code, String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("message", message);
		return JsonUtils.toJson(map);
	}
}
