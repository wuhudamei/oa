package cn.damei.entity.regulation;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.Status;
import java.io.Serializable;
import java.util.Date;
public class Regulation extends IdEntity implements Serializable {
    private static final long serialVersionUID = 6755939803660719213L;
    private String title;
    private Long type;
    private String typeName;
    private Status status;
    private Integer sort;
    private String fileUrl;
    private String fileName;
    private Long createUser;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
    private Date createTime;
    private String content;
    private String orgFamilyCode;
    private String orgName;
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getOrgFamilyCode() {
        return orgFamilyCode;
    }
    public void setOrgFamilyCode(String orgFamilyCode) {
        this.orgFamilyCode = orgFamilyCode;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    private String orgId;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getType() {
        return type;
    }
    public Regulation setType(Long type) {
        this.type = type;
        return this;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public Regulation setCreateUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }
    public Status getStatus() {
        return status;
    }
    public Regulation setStatus(Status status) {
        this.status = status;
        return this;
    }
    public Integer getSort() {
        return sort;
    }
    public Regulation setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getContent() {
        return content;
    }
    public Regulation setContent(String content) {
        this.content = content;
        return this;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}