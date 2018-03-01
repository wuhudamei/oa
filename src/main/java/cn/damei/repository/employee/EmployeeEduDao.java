package cn.damei.repository.employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.employee.EmployeeEdu;
import java.util.List;
@Repository
public interface EmployeeEduDao extends CrudDao<EmployeeEdu> {
    void batchInsert(@Param("edus") List<EmployeeEdu> edus);
    List<EmployeeEdu> findByEmpId(@Param("empId") Long empId);
    int deleteByEmpId(@Param("empId") Long empId);
}
