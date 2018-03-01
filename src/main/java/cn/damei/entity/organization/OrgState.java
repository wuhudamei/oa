package cn.damei.entity.organization;
public class OrgState
{
    public OrgState(Boolean flag){
        this.opened=flag;
    }
    private Boolean opened;
    public Boolean getOpened() {
        return opened;
    }
    public void setOpened(Boolean opened) {
        this.opened = opened;
    }
}
