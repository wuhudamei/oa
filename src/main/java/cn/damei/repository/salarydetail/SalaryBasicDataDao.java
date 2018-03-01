package cn.damei.repository.salarydetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.salarydetail.SalaryBasicData;
import cn.damei.entity.salarydetail.SalaryDetail;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface SalaryBasicDataDao extends CrudDao<SalaryBasicData>{
    List<SalaryDetail> getSalaryDetail(Map<String, Object> map);
    void insertSalary(@Param("params") Map<String, Object> params);
    List<SalaryDetail> find();
    List<SalaryBasicData> findAllSalaryBasicData(@Param("empId")Long empId);
    List<SalaryBasicData> findAllByUpMonth(@Param("upMonth") Date upMonth, @Param("empId") Long empId);
    List<Long> findAllZZEmp();
    void updateById(SalaryBasicData salaryBasicData);
    int findAllDate(Map map);
    List<SalaryBasicData> findListByEmpId(Long empId);
    Double findShouldWorkDays(@Param("empId") Long empId);
}
