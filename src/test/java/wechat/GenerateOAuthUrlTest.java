package wechat;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.config.ParamManager;
import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class GenerateOAuthUrlTest {
    private static final String WX_APP_ID = "wxa3b36bda18546de8";
    private static final String SSO_APP_ID = "285a2fbf8f0b0969ad";
    private static final String SSO_URL = "http://sso.rocoinfo.cn";
    private static final String RECEVIE_CODE_URL = "http://oatest.mdni.net.cn/oauthCallBack";
    @Test
    public void testGenerateUrl() {
        String url = this.generateUrl("/admin/employee/list");
        System.out.println(url);
    }
    private static final String OAUTH_URL_FORMAT = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    private static final String REDIRECT_URL_FORMAT = "%s/oauth/menu/code?appid=%s&redirect_url=%s";
    private String generateUrl(String state) {
        String redirectUrl = String.format(REDIRECT_URL_FORMAT, SSO_URL, SSO_APP_ID, RECEVIE_CODE_URL);
        return String.format(OAUTH_URL_FORMAT, WX_APP_ID, URLEncode(redirectUrl), "snsapi_base", state);
    }
    private static String URLEncode(String s) {
        String res;
        try {
            res = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("url encoding failed!");
        }
        return res;
    }
}
