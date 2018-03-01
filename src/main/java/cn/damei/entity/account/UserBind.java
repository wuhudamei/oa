package cn.damei.entity.account;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class UserBind extends IdEntity {
	private static final long serialVersionUID = 1L;
	public UserBind() {
        super();
    }
    public UserBind(User user, String oid, Platfrom platform, Date bindTime) {
        this.user = user;
        this.oid = oid;
        this.platform = platform;
        this.bindTime = bindTime;
    }
    private User user;
    private String oid;
    private Platfrom platform;
    private Date bindTime;
    public enum Platfrom {
        WECHAT("微信");
        private String label;
        Platfrom(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    public Platfrom getPlatform() {
        return platform;
    }
    public void setPlatform(Platfrom platform) {
        this.platform = platform;
    }
    public Date getBindTime() {
        return bindTime;
    }
    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }
}
