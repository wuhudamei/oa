package cn.damei.service.report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.report.ReportAuthority;
import cn.damei.repository.report.ReportAuthorityDao;
import java.util.List;
@Service
public class ReportAuthorityService {
    @Autowired
    private ReportAuthorityDao reportAuthorityDao;
    public List<ReportAuthority> getByOpenId(String openId) {
        return reportAuthorityDao.getByOid(openId);
    }
    public void injectSql(String sql) {
        reportAuthorityDao.injectSql(sql);
    }
    public void insert(ReportAuthority reportAuthority) {
        reportAuthorityDao.insert(reportAuthority);
    }
}
