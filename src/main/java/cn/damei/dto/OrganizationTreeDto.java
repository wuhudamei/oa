package cn.damei.dto;
import java.util.List;
import cn.damei.entity.organization.OrgState;
import cn.damei.entity.organization.State;
public class OrganizationTreeDto {
    public OrganizationTreeDto() {
        super();
    }
    public OrganizationTreeDto(Long id, String text,String type) {
        super();
        this.id = id;
        this.text = text;
        this.type=type;
    }
    private Long id;
    private List<OrganizationTreeDto> children;
    private String text;
    private OrgState state;
    private String type;
    private Integer sort;
    public OrganizationTreeDto(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<OrganizationTreeDto> getChildren() {
        return children;
    }
    public void setChildren(List<OrganizationTreeDto> children) {
        this.children = children;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    public OrgState getState() {
        return state;
    }
    public void setState(OrgState state) {
        this.state = state;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
