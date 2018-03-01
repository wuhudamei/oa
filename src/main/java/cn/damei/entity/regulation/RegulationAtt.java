package cn.damei.entity.regulation;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class RegulationAtt extends IdEntity implements Serializable {
    private static final long serialVersionUID = 7935683619242225437L;
    private Long regulationId;
    private String path;
    private String name;
    private String size;
    public Long getRegulationId() {
        return regulationId;
    }
    public void setRegulationId(Long regulationId) {
        this.regulationId = regulationId;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
}