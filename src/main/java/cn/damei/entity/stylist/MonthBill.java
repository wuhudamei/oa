package cn.damei.entity.stylist;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.util.Date;
public class MonthBill extends IdEntity implements Serializable {
    private String title;
    private String month;
    private Double totalMoney;
    private Long createUser;
    private String createUserName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    private Date createTime;
    private Status status;
    private static final long serialVersionUID = 1L;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
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
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    @Alias(value = "billStatus")
    public enum Status {
        DRAFT("草稿"), NORMAL("正常");
        private String label;
        Status(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}