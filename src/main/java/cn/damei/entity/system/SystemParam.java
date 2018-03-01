package cn.damei.entity.system;
import cn.damei.entity.IdEntity;
public class SystemParam extends IdEntity{
	private static final long serialVersionUID = 5166682951637092L;
	private String paramKey;
	private String paramKeyName;
	private Boolean paramFlag;
	private String paramValue;
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamKeyName() {
		return paramKeyName;
	}
	public void setParamKeyName(String paramKeyName) {
		this.paramKeyName = paramKeyName;
	}
	public Boolean getParamFlag() {
		return paramFlag;
	}
	public void setParamFlag(Boolean paramFlag) {
		this.paramFlag = paramFlag;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
