package cn.damei.entity.sign;
import cn.damei.entity.IdEntity;
public class SignSite extends IdEntity {
    private String sitename;
    private String longitude;
    private String latitude;
    private String radii;
    public String getSitename() {
        return sitename;
    }
    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getRadii() {
        return radii;
    }
    public void setRadii(String radii) {
        this.radii = radii;
    }
}
