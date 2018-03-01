package cn.damei.entity.chairmanMailbox;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import java.util.Date;
public class ChairmanMailbox extends IdEntity {
    private String title;
    private String content;
    private Boolean readStatus;
    private Boolean anonymous;
    private Integer importantDegree;
    private String comment;
    private Long createUser;
    private String createUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    private String companyName;
    private Long companyId;
    private String departmentName;
    private Long departmentId;
    private String pictureUrls;
    private String voiceUrl;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Boolean getReadStatus() {
        return readStatus;
    }
    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }
    public Boolean getAnonymous() {
        return anonymous;
    }
    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }
    public Integer getImportantDegree() {
        return importantDegree;
    }
    public void setImportantDegree(Integer importantDegree) {
        this.importantDegree = importantDegree;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public String getCreateUserName() {
        return createUserName;
    }
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getPictureUrls() {
        return pictureUrls;
    }
    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }
    public String getVoiceUrl() {
        return voiceUrl;
    }
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }
    @Override
    public String toString() {
        return "ChairmanMailbox{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readStatus=" + readStatus +
                ", anonymous=" + anonymous +
                ", importantDegree=" + importantDegree +
                ", comment='" + comment + '\'' +
                ", createUser=" + createUser +
                ", createUserName='" + createUserName + '\'' +
                ", createTime=" + createTime +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentId=" + departmentId +
                ", pictureUrls='" + pictureUrls + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                '}';
    }
}
