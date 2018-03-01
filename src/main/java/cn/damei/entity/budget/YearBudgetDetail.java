package cn.damei.entity.budget;
import java.util.List;
import cn.damei.entity.IdEntity;
public class YearBudgetDetail extends IdEntity {
    private Long yearBudgetId;
    private Integer subjectCode;
    private String subjectName;
    private Integer january;
    private Integer february;
    private Integer march;
    private Integer april;
    private Integer may;
    private Integer june;
    private Integer july;
    private Integer august;
    private Integer september;
    private Integer october;
    private Integer november;
    private Integer december;
    private Integer budgetYear;
    public Long getYearBudgetId() {
        return yearBudgetId;
    }
    public void setYearBudgetId(Long yearBudgetId) {
        this.yearBudgetId = yearBudgetId;
    }
    public Integer getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }
    public Integer getJanuary() {
        return january;
    }
    public void setJanuary(Integer january) {
        this.january = january;
    }
    public Integer getFebruary() {
        return february;
    }
    public void setFebruary(Integer february) {
        this.february = february;
    }
    public Integer getMarch() {
        return march;
    }
    public void setMarch(Integer march) {
        this.march = march;
    }
    public Integer getApril() {
        return april;
    }
    public void setApril(Integer april) {
        this.april = april;
    }
    public Integer getMay() {
        return may;
    }
    public void setMay(Integer may) {
        this.may = may;
    }
    public Integer getJune() {
        return june;
    }
    public void setJune(Integer june) {
        this.june = june;
    }
    public Integer getJuly() {
        return july;
    }
    public void setJuly(Integer july) {
        this.july = july;
    }
    public Integer getAugust() {
        return august;
    }
    public void setAugust(Integer august) {
        this.august = august;
    }
    public Integer getSeptember() {
        return september;
    }
    public void setSeptember(Integer september) {
        this.september = september;
    }
    public Integer getOctober() {
        return october;
    }
    public void setOctober(Integer october) {
        this.october = october;
    }
    public Integer getNovember() {
        return november;
    }
    public void setNovember(Integer november) {
        this.november = november;
    }
    public Integer getDecember() {
        return december;
    }
    public void setDecember(Integer december) {
        this.december = december;
    }
    public Integer getBudgetYear() {
        return budgetYear;
    }
    public void setBudgetYear(Integer budgetYear) {
        this.budgetYear = budgetYear;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}