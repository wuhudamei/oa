package cn.damei.dto.budget;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class FirstTypeDto<T extends BaseSecondTypeDto> implements Serializable {
    private static final long serialVersionUID = -2458812990004977035L;
    private Long id;
    private String name;
    private List<T> secondTypes;
    public double getTotalMoney() {
        double totalMoney = 0.0;
        for (BaseSecondTypeDto baseSecondTypeDto : this.secondTypes) {
            totalMoney += baseSecondTypeDto.getMoney();
        }
        return totalMoney;
    }
    public FirstTypeDto() {
        secondTypes = new ArrayList<>();
    }
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
    public List<T> getSecondTypes() {
        return secondTypes;
    }
    public void setSecondTypes(List<T> secondTypes) {
        this.secondTypes = secondTypes;
    }
}
