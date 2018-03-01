package cn.damei.entity;
import java.io.Serializable;
public class IdEntity implements Serializable {
    private static final long serialVersionUID = -2716222356509348153L;
    protected Long id;
    public static final String ID_FIELD_NAME = "id";
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
