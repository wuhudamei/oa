package cn.damei;
import com.rocoinfo.weixin.WeChatInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
@Component
@Lazy(false)
public class WeChatInitialize {
    Logger logger = LoggerFactory.getLogger(WeChatInitialize.class);
    @PostConstruct
    public void init() {
        logger.info("start init wechat configuration......");
        WeChatInitializer init = new WeChatInitializer();
        init.init();
        logger.info("end init wechat configuration......");
    }
}
