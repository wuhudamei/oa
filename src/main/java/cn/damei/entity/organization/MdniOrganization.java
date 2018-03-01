package cn.damei.entity.organization;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.dto.OrganizationTreeDto;
import cn.damei.entity.IdEntity;
import org.apache.commons.collections.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MdniOrganization extends IdEntity {
    public MdniOrganization() {
    }
    public MdniOrganization(Long id) {
        this.id = id;
    }
    private static final long serialVersionUID = 1L;
//            北京 bj
//            上海:sh
//            广州:gz 等 mdni_organization.org_code
    private String orgCode;
    private String orgName;
    private Integer sort;
    private Long parentId;
    private String familyCode;
//            1:正常 mdni_organization.status
    private Integer status = 1;
    private String createDate;
    private Long createUser;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date signTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date signOutTime;
    private Long tmpId;
    private Integer remindMessageType;//打卡前发消息的状态，0为未发，1为已发
    private Integer outRemindMessageType;//打卡前发消息的状态，0为未发，1为已发
    private MdniOrganization parent;
    private Boolean storeFlag;
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getFamilyCode() {
        return familyCode;
    }
    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode == null ? null : familyCode.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Date getSignTime() {
        return signTime;
    }
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    public Date getSignOutTime() {
        return signOutTime;
    }
    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }
    public Long getTmpId() {
        return tmpId;
    }
    public void setTmpId(Long tmpId) {
        this.tmpId = tmpId;
    }
    public MdniOrganization getParent() {
        return parent;
    }
    public void setParent(MdniOrganization parent) {
        this.parent = parent;
    }
    public Integer getRemindMessageType() {
        return remindMessageType;
    }
    public void setRemindMessageType(Integer remindMessageType) {
        this.remindMessageType = remindMessageType;
    }
    public Integer getOutRemindMessageType() {
        return outRemindMessageType;
    }
    public void setOutRemindMessageType(Integer outRemindMessageType) {
        this.outRemindMessageType = outRemindMessageType;
    }
    public Boolean getStoreFlag() {
        return storeFlag;
    }
    public void setStoreFlag(Boolean storeFlag) {
        this.storeFlag = storeFlag;
    }
    public static OrganizationTreeDto buildOrganizationTree(OrganizationTreeDto curCode, List<MdniOrganization> orgList) {
        if (CollectionUtils.isEmpty(orgList) || curCode == null) {
            return curCode;
        }
        List<OrganizationTreeDto> childNodeList = new ArrayList<>();
        MdniOrganization org = null;
        for (int index = 0; index < orgList.size(); index++) {
            org = orgList.get(index);
            if (org == null) {
                continue;
            }
            if (curCode.getId().equals(org.getParentId())) {
                OrganizationTreeDto childNode = new OrganizationTreeDto();
                childNode.setId(org.getId());
                childNode.setText(org.getOrgName());
                childNode.setState(new OrgState(false));
                childNode.setType(org.getType());
                childNode.setSort(org.getSort());
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            curCode.setChildren(childNodeList);
        }
        for (OrganizationTreeDto dto : childNodeList) {
            buildOrganizationTree(dto, orgList);
        }
        return curCode;
    }
}