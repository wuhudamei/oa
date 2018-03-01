package cn.damei;
public class Constants {
	private Constants() {
	}
	public static final String RESP_STATUS_CODE_SUCCESS = "1";
	public static final String RESP_STATUS_CODE_FAIL = "0";
	public static final String OAUTH_CENTER_CODE_URL = "/oauth/code";
	public static final String OAUTH_CENTER_TOKEN_URL = "/oauth/token";
	public static final String OAUTH_CALL_BACK = "/oauthCallBack";
	public static final String OAUTH_LOGOUT_URL = "/oauth/logout";
	public static final String OAUTH_PASSWORD_URL = "/oauth/password";
	public static final int LOGIN_SENCE_ID = 101;
	public static final String WECHAT_USER_TYPE_INSIDE = "INSIDE";
	public static final String WECHAT_USER_TYPE_OUTSIDE = "OUTSIDE";
	public static final String WECHAT_USER_SESSION_KEY = "wechat_user";
	public static final int INSIDE_USER_GROUP_ID = 101;
	public static final int OUTSIDE_USER_GROUP_ID = 102;
	public static final String QRCODE_OPERATION_CODE = "code";
	public static final String QRCODE_OPERATION_MESSAGE = "message";
	public static final String NO_WF_ERROR_MESSAGE = "申请人所在部门未配置审批流程,请配置后提交申请!";
	public static final String SALE_NISSIN_REPORT_URL = "/reportForCall/workReturn";
	public static final String SALE_DEP_URL = "/reportForCall/depList";
}
