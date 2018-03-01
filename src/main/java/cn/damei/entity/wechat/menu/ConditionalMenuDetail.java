package cn.damei.entity.wechat.menu;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.wechat.MenuType;
public class ConditionalMenuDetail extends Menu {
    private Long cid;
    private static final long serialVersionUID = 1L;
    public Long getCid() {
        return cid;
    }
    public ConditionalMenuDetail setCid(Long cid) {
        this.cid = cid;
        return this;
    }
}