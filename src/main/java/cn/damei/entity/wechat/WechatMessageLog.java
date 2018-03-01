package cn.damei.entity.wechat;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class WechatMessageLog extends IdEntity {
	private static final long serialVersionUID = -2066592832519505268L;
	private String url;
	private Long formId;
	private String wfType;
	private String approverr;
	private String opinion;
	private Long fromUserId;
	private String toUsers;
	private Date createTime;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getWfType() {
		return wfType;
	}
	public void setWfType(String wfType) {
		this.wfType = wfType;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUsers() {
		return toUsers;
	}
	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getApproverr() {
		return approverr;
	}
	public void setApproverr(String approverr) {
		this.approverr = approverr;
	}
}
