package cn.damei.dto.budget;
import java.io.Serializable;
public class SecondTypeDto extends BaseSecondTypeDto implements Serializable {
    private static final long serialVersionUID = 4801031696163280195L;
    private Long id;
    private String name;
    private Double money;
    private String remark;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
