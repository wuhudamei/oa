package cn.damei.entity.wechat.menu;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.wechat.MenuType;
public class Menu extends IdEntity {
    private static final long serialVersionUID = 1L;
    public static final Long ROOT_PID = 0L;
    public static final String LEVEL_1 = "1";
    public static final String LEVEL_2 = "2";
    private String name;
    private MenuType type;
    private String level;
    private String clickKey;
    private String url;
    private Long mediaId;
    private Long pid;
    private Long sort;
    public String getName() {
        return name;
    }
    public Menu setName(String name) {
        this.name = name;
        return this;
    }
    public MenuType getType() {
        return type;
    }
    public void setType(MenuType type) {
        this.type = type;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getClickKey() {
        return clickKey;
    }
    public Menu setClickKey(String clickKey) {
        this.clickKey = clickKey;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Long getMediaId() {
        return mediaId;
    }
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }
    public Long getPid() {
        return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public Long getSort() {
        return sort;
    }
    public Menu setSort(Long sort) {
        this.sort = sort;
        return this;
    }
}