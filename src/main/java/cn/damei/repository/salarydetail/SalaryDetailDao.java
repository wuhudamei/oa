package cn.damei.repository.salarydetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.salarydetail.SalaryDetail;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface SalaryDetailDao extends CrudDao<SalaryDetail>{
    List<SalaryDetail> getSalaryDetail(Map<String, Object> map);
    void insertSalary(@Param("params")Map<String ,Object> params);
    List<SalaryDetail> find();
    List<SalaryDetail> getSalaryDetailBySalaMonth(Map<String, Object> params);
    List<SalaryDetail> findAllSalaryDetail();
    List<SalaryDetail> findAllByUpMonth(@Param("upMonth") String upMonth,@Param("empId") Long empId);
    List<Long> findAllZZEmp();
    void updatePracticalWorkDays(@Param("empId") Long empId, @Param("signCount") double signCount, @Param("firstparse")Date firstparse);
    void deleteSalary(@Param("firstparse") Date firstparse);
}
