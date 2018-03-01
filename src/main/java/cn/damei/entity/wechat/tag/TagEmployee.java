package cn.damei.entity.wechat.tag;
import cn.damei.entity.IdEntity;
import cn.damei.entity.employee.Employee;
public class TagEmployee extends IdEntity {
    public TagEmployee() {
    }
    public TagEmployee(Tag tag, Employee employee, String openid) {
        this.tag = tag;
        this.employee = employee;
        this.openid = openid;
    }
    private Tag tag;
    private Employee employee;
    private String openid;
    public Tag getTag() {
        return tag;
    }
    public TagEmployee setTag(Tag tag) {
        this.tag = tag;
        return this;
    }
    public Employee getEmployee() {
        return employee;
    }
    public TagEmployee setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }
    public String getOpenid() {
        return openid;
    }
    public TagEmployee setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
}
