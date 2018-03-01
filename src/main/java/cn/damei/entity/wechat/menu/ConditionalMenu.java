package cn.damei.entity.wechat.menu;
import java.util.List;
import cn.damei.entity.IdEntity;
import cn.damei.entity.wechat.tag.Tag;
public class ConditionalMenu extends IdEntity {
    private String name;
    private String description;
    private String oid;
    private Tag tag;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String clientPlatformType;
    private String language;
    private List<ConditionalMenuDetail> details;
    private static final long serialVersionUID = 1L;
    public String getName() {
        return name;
    }
    public ConditionalMenu setName(String name) {
        this.name = name;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public ConditionalMenu setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getOid() {
        return oid;
    }
    public ConditionalMenu setOid(String oid) {
        this.oid = oid;
        return this;
    }
    public Tag getTag() {
        return tag;
    }
    public ConditionalMenu setTag(Tag tag) {
        this.tag = tag;
        return this;
    }
    public String getSex() {
        return sex;
    }
    public ConditionalMenu setSex(String sex) {
        this.sex = sex;
        return this;
    }
    public String getCountry() {
        return country;
    }
    public ConditionalMenu setCountry(String country) {
        this.country = country;
        return this;
    }
    public String getProvince() {
        return province;
    }
    public ConditionalMenu setProvince(String province) {
        this.province = province;
        return this;
    }
    public String getCity() {
        return city;
    }
    public ConditionalMenu setCity(String city) {
        this.city = city;
        return this;
    }
    public String getClientPlatformType() {
        return clientPlatformType;
    }
    public ConditionalMenu setClientPlatformType(String clientPlatformType) {
        this.clientPlatformType = clientPlatformType;
        return this;
    }
    public String getLanguage() {
        return language;
    }
    public ConditionalMenu setLanguage(String language) {
        this.language = language;
        return this;
    }
    public List<ConditionalMenuDetail> getDetails() {
        return details;
    }
    public ConditionalMenu setDetails(List<ConditionalMenuDetail> details) {
        this.details = details;
        return this;
    }
}