package cn.damei.dto.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.budget.Budget;
import java.util.Date;
import java.util.List;
public class BudgetDto {
    private String title;
    private String code;
    private String company;
    private String yearMonth;
    private Double totalMoney;
    private String attachment;
    private String submitter;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    private String remark;
    public String getCode() {
        return code;
    }
    public BudgetDto setCode(String code) {
        this.code = code;
        return this;
    }
    private List<BudgetDetailDto> details;
    public String getTitle() {
        return title;
    }
    public BudgetDto setTitle(String title) {
        this.title = title;
        return this;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public String getAttachment() {
        return attachment;
    }
    public BudgetDto setAttachment(String attachment) {
        this.attachment = attachment;
        return this;
    }
    public BudgetDto setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }
    public String getCompany() {
        return company;
    }
    public BudgetDto setCompany(String company) {
        this.company = company;
        return this;
    }
    public String getYearMonth() {
        return yearMonth;
    }
    public BudgetDto setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }
    public String getSubmitter() {
        return submitter;
    }
    public BudgetDto setSubmitter(String submitter) {
        this.submitter = submitter;
        return this;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public BudgetDto setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
        return this;
    }
    public String getRemark() {
        return remark;
    }
    public BudgetDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public List<BudgetDetailDto> getDetails() {
        return details;
    }
    public BudgetDto setDetails(List<BudgetDetailDto> details) {
        this.details = details;
        return this;
    }
    public static BudgetDto fromBudget(Budget budget) {
        BudgetDto dto = new BudgetDto();
        if (budget != null) {
            dto.setCompany(budget.getCompany().getOrgName())
                    .setCode(budget.getApplyCode())
                    .setYearMonth(budget.getBudgetDate())
                    .setSubmitter(budget.getCreateUser().getName())
                    .setApplyTime(budget.getCreateDate())
                    .setTitle(budget.getApplyTitle())
                    .setTotalMoney(budget.getTotalMoney())
                    .setRemark(budget.getRemark())
                    .setAttachment(budget.getAttachment());
        }
        return dto;
    }
}
