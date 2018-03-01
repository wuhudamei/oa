package cn.damei.entity.oa;
import cn.damei.entity.IdEntity;
public class ApplySequence extends IdEntity {
    private String applyDate;
    private String applyType;
    private Integer currentSequence;
    public String getApplyDate() {
        return applyDate;
    }
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }
    public String getApplyType() {
        return applyType;
    }
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
    public Integer getCurrentSequence() {
        return currentSequence;
    }
    public void setCurrentSequence(Integer currentSequence) {
        this.currentSequence = currentSequence;
    }
}