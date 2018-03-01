package cn.damei.entity.stylist;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class Rule extends IdEntity implements Serializable {
    private static final long serialVersionUID = -1213372734223953765L;
    private String code;
    private String name;
    private Double ratio1;
    private Double ratio2;
    private Type type;
    public String getCode() {
        return code;
    }
    public Rule setCode(String code) {
        this.code = code;
        return this;
    }
    public String getName() {
        return name;
    }
    public Rule setName(String name) {
        this.name = name;
        return this;
    }
    public Double getRatio1() {
        return ratio1;
    }
    public Rule setRatio1(Double ratio1) {
        this.ratio1 = ratio1;
        return this;
    }
    public Double getRatio2() {
        return ratio2;
    }
    public Rule setRatio2(Double ratio2) {
        this.ratio2 = ratio2;
        return this;
    }
    public Type getType() {
        return type;
    }
    public Rule setType(Type type) {
        this.type = type;
        return this;
    }
    public enum Type {
        LEVEL("设计师等级"), INDIRECT("间接费用"), GRANT("发放比例");
        private String label;
        Type(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}
