package cn.damei.entity.organization;
public class ZTreeOrg {
    private Long id;
    private Long pId;
    private String name;
    private String type;
    private String code;
    private String familyCode;
    private boolean open;
    private boolean checked;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getpId() {
        return pId;
    }
    public void setpId(Long pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getFamilyCode() {
        return familyCode;
    }
    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }
}
