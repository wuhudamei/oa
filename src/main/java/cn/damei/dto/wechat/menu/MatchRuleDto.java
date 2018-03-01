package cn.damei.dto.wechat.menu;
import com.fasterxml.jackson.annotation.JsonProperty;
public class MatchRuleDto {
    public static MatchRuleDto fromTagId(String tagId) {
        return new MatchRuleDto().setTagId(tagId);
    }
    @JsonProperty("tag_id")
    private String tagId;
    private String sex;
    private String country;
    private String province;
    private String city;
    @JsonProperty("client_platform_type")
    private String clientPlatformType;
    private String language;
    public String getTagId() {
        return tagId;
    }
    public MatchRuleDto setTagId(String tagId) {
        this.tagId = tagId;
        return this;
    }
    public String getSex() {
        return sex;
    }
    public MatchRuleDto setSex(String sex) {
        this.sex = sex;
        return this;
    }
    public String getCountry() {
        return country;
    }
    public MatchRuleDto setCountry(String country) {
        this.country = country;
        return this;
    }
    public String getProvince() {
        return province;
    }
    public MatchRuleDto setProvince(String province) {
        this.province = province;
        return this;
    }
    public String getCity() {
        return city;
    }
    public MatchRuleDto setCity(String city) {
        this.city = city;
        return this;
    }
    public String getClientPlatformType() {
        return clientPlatformType;
    }
    public MatchRuleDto setClientPlatformType(String clientPlatformType) {
        this.clientPlatformType = clientPlatformType;
        return this;
    }
    public String getLanguage() {
        return language;
    }
    public MatchRuleDto setLanguage(String language) {
        this.language = language;
        return this;
    }
}
