package cn.damei.entity.report;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class ReportAuthority extends IdEntity {
    private static final long serialVersionUID = -8655436155937412544L;
    private String name;
    private String oid;
    private String remark;
    private Date createTime;
    private Date updateTime;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
