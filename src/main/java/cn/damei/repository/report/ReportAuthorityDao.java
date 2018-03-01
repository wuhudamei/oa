package cn.damei.repository.report;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.entity.report.ReportAuthority;
import java.util.List;
@Repository
public interface ReportAuthorityDao {
    List<ReportAuthority> getByOid(String oid);
    void injectSql(@Param(value = "sql") String sql);
    void insert(ReportAuthority reportAuthority);
}
