package cn.damei.entity.subjectprocess;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class SubjectProcess extends IdEntity {
    private String wfType;
    private Long wfId;
    private String wfName;
    private Long processTypeId;
    private String processTypeName;
    private Long subjectId;
    private String subjectName;
    private Long createUser;
    private Integer status;
    private Date createTime;
    public String getWfType() {
        return wfType;
    }
    public void setWfType(String wfType) {
        this.wfType = wfType;
    }
    public Long getWfId() {
        return wfId;
    }
    public void setWfId(Long wfId) {
        this.wfId = wfId;
    }
    public Long getProcessTypeId() {
        return processTypeId;
    }
    public void setProcessTypeId(Long processTypeId) {
        this.processTypeId = processTypeId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getWfName() {
        return wfName;
    }
    public void setWfName(String wfName) {
        this.wfName = wfName;
    }
    public String getProcessTypeName() {
        return processTypeName;
    }
    public void setProcessTypeName(String processTypeName) {
        this.processTypeName = processTypeName;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
