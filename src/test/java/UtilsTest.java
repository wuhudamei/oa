import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.rocoinfo.weixin.api.AccessTokenApi;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.api.TagApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.shiro.PasswordUtil;
import cn.damei.utils.DateUtils;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.cache.StringCache;
import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
public class UtilsTest {
    @Test
    public void testJackSon() {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setToUser("OPENID");
        templateMessage.setTemplateId("4354GGDSVDGDSD_DSFDG_sdgfdg");
        templateMessage.setUrl(null);
        String json = JsonUtils.toJson(templateMessage);
        System.out.println(json);
    }
    @Test
    public void testGuavaCache() throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            StringCache.put("" + i, "" + i);
            System.out.println(StringCache.size());
        }
    }
    @Test
    public void testImmutableMultimap() {
        ImmutableMultimap<String, String> map = ImmutableMultimap.of("name", "Kong", "age", "24", "name", "Lilys");
        ImmutableList<String> names = map.get("name").asList();
        String name = names.get(1);
        String jsonStr = JsonUtils.toJson(map);
        System.out.println(jsonStr);
    }
    @Test
    public void testHashCode() {
        String str1 = new String("asdfasgewrewtfd");
        String str2 = new String("asdfasgewrewtfd");
        System.out.println(str1.hashCode() == str2.hashCode());
    }
    @Test
    public void testSubtring() {
        System.out.println("2017-03".substring(0, 4));
        System.out.println("2017-03".substring(5));
    }
    @Test
    public void testDateUtils() {
        System.out.println(DateUtils.lastMonthString("2017-03", "yyyy-MM"));
    }
    @Test
    public void test() {
        Integer a = 1;
        Integer b = 2;
        Integer c = a + b;
        System.out.println("c=" + c);
        a = 3;
        System.out.println("c=" + c);
    }
    @Test
    public void testGetAccessToken() {
        System.out.println(AccessTokenApi.getString());
    }
    @Test
    public void testCreateMenu() {
//        String url1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx252f3c0b539832a0&redirect_uri=http%3A%2F%2Foatest.mdni.net.cn%2Fwx%2Fclick%2Flogin&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
//        String url2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx252f3c0b539832a0&redirect_uri=http%3A%2F%2Foatest.mdni.net.cn%2Fapi%2Freports&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
//        String menuJson = "{\"button\":[{\"type\":\"view\",\"name\":\"综管平台\",\"url\":\"" + url1 + "\"},{\"type\":\"view\",\"name\":\"报表\",\"url\":\"" + url2 + "\"}]}";
//        ApiResult res = MenuApi.create(menuJson);
//        System.out.println(res.getRawJson());
    }
    @Test
    public void testMapForeach() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
    @Test
    public void testTagged() {
        String openid = "oYq6cwJFMnuM0jIwYeBCvu17aHeM";
        ApiResult result = TagApi.batchTag(100L, Lists.newArrayList(openid));
        System.out.println(result.getRawJson());
    }
    @Test
    public void testCreateTag() {
        ApiResult result = TagApi.create("测试标签");
        System.out.println(result.getRawJson());
    }
    @Test
    public void testPasswordUtils() {
        String platPassword = "admin123!@#";
        String salt = "ecee276542caaa64";
        String password = PasswordUtil.entryptUserPassword(platPassword, salt);
        System.out.println(password);
    }
    @Test
    public void testEnum() {
        int a = (int) Math.floor(0.23);
        int b = (int) Math.ceil(0.45);
        int c = (int) Math.ceil(0.55);
        int d = (int) Math.round(1.45);
    }
    @Test
    public void generateUrl() {
        String url = OAuthApi.buildUrl("http://losemycat.natapp1.cc/wx/click/login", "/admin/orgList", OAuthApi.Type.BASE);
        System.out.println(url);
    }
}
