package service;
import com.rocoinfo.weixin.api.TagApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.common.PropertyHolder;
import cn.damei.enumeration.UploadCategory;
import cn.damei.utils.HttpUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import java.util.Date;
public class Main {
    @Test
    public void test1() {
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Long ctm = System.currentTimeMillis();
        System.out.println(date + ctm);
        ApiResult ss = TagApi.getUserTags("o6PX0t_0jtNReD6XXLaBoCkTa3Vk");
        System.out.println(ss.get("tagid_list"));
//        ApiResult tagMessage = TagApi.batchTag(107, Lists.newArrayList("o6PX0t1dGJYyA6gHp6PLOAjVhues"));
//        System.out.println(tagMessage);
//        List<String> openid = new ArrayList<String>();
//    	openid.add("o6PX0t1dGJYyA6gHp6PLOAjVhues");
//        TagApi.batchUntag("", openid);
    }
//    @Test
    public void test(){
    	String param = " {\"account\":\"zzc00001\",\"name\":\"zzc\",\"mobile\":\"13666666666\",\"source\":\"SCM\"}";
    	String retJson = HttpUtil.post("http://oatest.mdni.net.cn/api/interface/createAccount", param, "json");
	    System.out.println(retJson);
    }
    public static void main(String[] args) {
//        ChairmanMailbox chairmanMailbox = new ChairmanMailbox();
//        chairmanMailbox.setVoiceUrl("Pflnlvza-M3bn25Ouv--MVO8C9wPD7ARyxxM62tiAez5teftr5vozCemL2Aa1RZS");
//        chairmanMailbox.setPictureUrls("DSTjk1Fwhke2-Nevmk2JGU5BkZ_wd5Snpz6ZezslsuFQ2GLpbzg5aRDvXEHlCSqG");
        String localPicUrl = "/home/tomcatuser/oatest_mdni/oatestnew/bin/uploads/chairmanMaibox/2017/09/22/eanwGI_VSWLIiA0bzW5tvCUgjInvg6VtckjHe3Yd90YhFwlBjVZ9c7VSmvkrYtBjpg";
        System.out.println(UploadCategory.CHAIRMAN_MAIBOX.getPath());
        System.out.println(PropertyHolder.getUploadBaseUrl() + localPicUrl.substring(localPicUrl.indexOf("/" + UploadCategory.CHAIRMAN_MAIBOX.getPath())));
    }
}
