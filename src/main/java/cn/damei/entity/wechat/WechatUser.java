package cn.damei.entity.wechat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cn.damei.entity.IdEntity;
import java.util.List;
public class WechatUser extends IdEntity {
    public WechatUser() {
    }
    private WechatUser(Long id) {
        this.id = id;
    }
    public static WechatUser fromId(Long id) {
        return new WechatUser(id);
    }
    private Integer subscribe;
    private String openid;
    private Integer sex;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
    @JsonProperty(value = "subscribe_time")
    private String subscribeTime;
    private String unionid;
    private String remark;
    private Long groupid;
    private String type;
    private Long userId;
    private String jobNum;
    private String tags;
    private String nickname;
    private String errcode;
    @JsonProperty("tagid_list")
    private List<String> tagidList;
    private static final long serialVersionUID = 1L;
    public Integer getSubscribe() {
        return subscribe;
    }
    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public String getSubscribeTime() {
        return subscribeTime;
    }
    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Long getGroupid() {
        return groupid;
    }
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public List<String> getTagidList() {
        return tagidList;
    }
    public void setTagidList(List<String> tagidList) {
        this.tagidList = tagidList;
    }
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
}