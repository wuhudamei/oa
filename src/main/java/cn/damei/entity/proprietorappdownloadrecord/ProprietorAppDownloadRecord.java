package cn.damei.entity.proprietorappdownloadrecord;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class ProprietorAppDownloadRecord extends IdEntity{
    private Date downloadTime;
    public Date getDownloadTime() {
        return downloadTime;
    }
    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
}
