package cn.damei.rest.wechat;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import com.rocoinfo.weixin.api.TagApi;
import com.rocoinfo.weixin.api.UserApi;
import com.rocoinfo.weixin.model.ApiResult;
import com.rocoinfo.weixin.msg.req.type.EventType;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.entity.wechat.tag.Tag;
import cn.damei.entity.wechat.tag.TagEmployee;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.service.wechat.tag.TagEmployeeService;
import cn.damei.service.wechat.tag.TagService;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.XmlUtils;
@RestController
@RequestMapping(value = "/wx")
public class MessageController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(MessageController.class);//日志
	private static final String PARAMS_EVENT = "Event";
	private static final String PARAMS_EVENT_KEY = "EventKey";
	private static final String PARAMS_MSG_TYPE = "MsgType";//消息类型
	private static final String PARAM_TO_USER_NAME = "ToUserName";
	private static final String PARAM_FROM_USER_NAME = "FromUserName";//发送方帐号（一个OpenID）
	private static final String PARAM_TICKET = "Ticket";
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private TagService tagService;
	@Autowired
	private TagEmployeeService tagEmployeeService;
    @RequestMapping(method = RequestMethod.POST)
    public Object handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is = request.getInputStream();
        Map<String, String> params = XmlUtils.xml2Map(is);
        if (MapUtils.isNotEmpty(params)) {
            String fromUserName = params.get(PARAM_FROM_USER_NAME);
            String toUserName = params.get(PARAM_TO_USER_NAME);
            String msgType = params.get(PARAMS_MSG_TYPE);
            if ("event".equals(msgType)) {
                if (EventType.SCAN.equals(params.get(PARAMS_EVENT))) {
                } else if(EventType.SUBSCRIBE.equals(params.get(PARAMS_EVENT))){
                	WechatUser wechatUser = wechatUserService.getByOpenid(fromUserName);
                	ApiResult res = UserApi.info(fromUserName);
                    WechatUser user = JsonUtils.fromJson(res.getRawJson(),WechatUser.class);
                	user.setType(Constants.WECHAT_USER_TYPE_OUTSIDE);
                	if(wechatUser == null){
                        if (!res.isSuccess()){
                        	return StatusDto.buildFailure("查询用户信息失败");
                        }
                		wechatUserService.insert(user);
                	}else{
                    	wechatUserService.updateByOpenId(user);
                	}
                	if(params.get(PARAM_TICKET) != null && params.get(PARAMS_EVENT_KEY) != null){//如果是扫描带参数二维码关注的用户
                		String eventKeyParam = params.get(PARAMS_EVENT_KEY);
                    	try {
                    		String[] keyParam = eventKeyParam.split("_");
                    		String oid = keyParam[1];
//                            TagApi.batchTag(Constants.OUTSIDE_USER_GROUP_ID, Lists.newArrayList(fromUserName));
                    		ApiResult tagMessage = TagApi.batchTag(Integer.parseInt(oid), Lists.newArrayList(fromUserName));
                    		Tag tag = tagService.getTagByOid(Long.valueOf(oid));
                    		if(tag != null){
                    			this.tagEmployeeService.batchInsert(Lists.newArrayList(new TagEmployee(tag,new Employee(0L),fromUserName)));//打标签后需要将用户加入到标签用户管理表中.
                    		}else{
                    			logger.error("【用户打标签失败】,Tag is null :【" + oid + "】");
                    		}
                        }catch (Exception e){
                    	    e.printStackTrace();
                    	    logger.error("【用户打标签失败】:"+e.getMessage());
                        }
                	}
                } else if(EventType.UNSUBSCRIBE.equals(params.get(PARAMS_EVENT))){
                	WechatUser user = new WechatUser();
                	user.setOpenid(fromUserName);
                	user.setSubscribe(0);
                	user.setType(Constants.WECHAT_USER_TYPE_OUTSIDE);
                	wechatUserService.updateByOpenId(user);
                	tagEmployeeService.deleteByOpenids(Lists.newArrayList(fromUserName));
                }
            }
        }
        return null;
    }
}
