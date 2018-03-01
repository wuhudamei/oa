package cn.damei.entity.wechat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.wechat.QrcodeType;
public class Qrcode extends IdEntity {
    private Long sceneId;
    private String tagName;
    private String sceneStr;
    private QrcodeType type;
    private Long expireSeconds;
    private Boolean generated;
    private String ticket;
    private String url;
    private static final long serialVersionUID = 1L;
    public Long getSceneId() {
        return sceneId;
    }
    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }
    public String getSceneStr() {
        return sceneStr;
    }
    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }
    public QrcodeType getType() {
        return type;
    }
    public void setType(QrcodeType type) {
        this.type = type;
    }
    public Long getExpireSeconds() {
        return expireSeconds;
    }
    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
    public Boolean getGenerated() {
        return generated;
    }
    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}