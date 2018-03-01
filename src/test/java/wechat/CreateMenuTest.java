package wechat;
import com.rocoinfo.weixin.WeChatInitializer;
import com.rocoinfo.weixin.api.MenuApi;
import com.rocoinfo.weixin.config.Configuration;
import com.rocoinfo.weixin.config.ParamHolder;
import com.rocoinfo.weixin.model.ApiResult;
import org.junit.Test;
public class CreateMenuTest {
    private static String PRODUCTION_ENV = "production";
    private static String TEST_ENV = "test";
    private static DefaultParamHolder paramHolder = null;
    private void init(String env) {
        if (PRODUCTION_ENV.equals(env)) {
            paramHolder = new DefaultParamHolder("wxf1d3c37befc6d7a8", "f0200cf7dc4edbb8df22c47a296b3172", "aASDF23sdOIPMCkkjDF1234asdf235");
        } else if (TEST_ENV.equals(env)) {
            paramHolder = new DefaultParamHolder("wxa3b36bda18546de8", "1de140002d409a4add971eb19c6d1108", "KUSDF97vhjasf87ASKDFJ8");
        } else {
            throw new RuntimeException();
        }
        Configuration conf = new Configuration();
        conf.registerParamHolder(paramHolder);
        WeChatInitializer init = new WeChatInitializer();
        init.init(conf);
    }
//    @Test
    public void createProductionMenu() {
        init(PRODUCTION_ENV);
        String url1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf1d3c37befc6d7a8&redirect_uri=http%3A%2F%2Fmm.mdni.net.cn%2Fwx%2Fclick%2Flogin&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
        String url2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf1d3c37befc6d7a8&redirect_uri=http%3A%2F%2Fmm.mdni.net.cn%2Fapi%2Freports&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
        String menuJson = "{\"button\":[{\"type\":\"view\",\"name\":\"综管平台\",\"url\":\"" + url1 + "\"},{\"type\":\"view\",\"name\":\"报表\",\"url\":\"" + url2 + "\"}]}";
        ApiResult res = MenuApi.create(menuJson);
        System.out.println(res.getRawJson());
    }
//    @Test
    public  void createTestMenu() {
        init(TEST_ENV);
        String url1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa3b36bda18546de8&redirect_uri=http%3A%2F%2Foatest.mdni.net.cn%2Fwx%2Fclick%2Flogin&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
        String url2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa3b36bda18546de8&redirect_uri=http%3A%2F%2Foatest.mdni.net.cn%2Fapi%2Freports&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
        String menuJson = "{\"button\":[{\"type\":\"view\",\"name\":\"综管平台\",\"url\":\"" + url1 + "\"},{\"type\":\"view\",\"name\":\"报表\",\"url\":\"" + url2 + "\"}]}";
        ApiResult res = MenuApi.create(menuJson);
        System.out.println(res.getRawJson());
    }
    private class DefaultParamHolder implements ParamHolder {
        private String appid;
        private String secret;
        private String token;
        public DefaultParamHolder(String appid, String secret, String token) {
            this.appid = appid;
            this.secret = secret;
            this.token = token;
        }
        @Override
        public String getAppid() {
            return this.appid;
        }
        @Override
        public String getSecret() {
            return this.secret;
        }
        @Override
        public String getToken() {
            return this.token;
        }
    }
}
