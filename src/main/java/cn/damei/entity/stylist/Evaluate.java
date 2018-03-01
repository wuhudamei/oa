package cn.damei.entity.stylist;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class Evaluate extends IdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jobNo;
    private String name;
    private String mobile;
    private String evaluateMonth;
    private Double score;
    private Long createUser;
    public String getJobNo() {
        return jobNo;
    }
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEvaluateMonth() {
        return evaluateMonth;
    }
    public void setEvaluateMonth(String evaluateMonth) {
        this.evaluateMonth = evaluateMonth;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}