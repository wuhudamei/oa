package cn.damei.dto.dict;
@SuppressWarnings("all")
public class MdnidictionaryDto  {
    private Long id;
    private Integer pId;
    private String name;
    private Boolean open;
    private Boolean checked;
    public MdnidictionaryDto(Long id, Integer pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = false;
        this.checked = false;
    }
    public MdnidictionaryDto(Long id, Integer pId, String name, Boolean open, Boolean checked) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.checked = checked;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getpId() {
        return pId;
    }
    public void setpId(Integer pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getOpen() {
        return open;
    }
    public void setOpen(Boolean open) {
        this.open = open;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
