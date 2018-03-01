package cn.damei.repository.employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.employee.EmployeeWork;
import java.util.List;
@Repository
public interface EmployeeWorkDao extends CrudDao<EmployeeWork> {
    void batchInsert(@Param("works") List<EmployeeWork> works);
    List<EmployeeWork> findByEmpId(@Param("empId") Long empId);
    int deleteByEmpId(@Param("empId") Long empId);
}
