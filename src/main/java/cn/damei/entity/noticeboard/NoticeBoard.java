package cn.damei.entity.noticeboard;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import org.joda.time.DateTime;
import java.io.Serializable;
import java.util.Date;
public class NoticeBoard extends IdEntity {
    private Long id;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    private String orgFamilyCode;
    private String orgId;
    private String noticeStatus;
    private Long createNameId;
    private String createName;
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    public String getNoticeStatus() {
        return noticeStatus;
    }
    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
    public Long getCreateNameId() {
        return createNameId;
    }
    public void setCreateNameId(Long createNameId) {
        this.createNameId = createNameId;
    }
    public String getCreateName() {
        return createName;
    }
    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
