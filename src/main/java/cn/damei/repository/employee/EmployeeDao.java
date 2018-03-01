package cn.damei.repository.employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.employee.Employee;
import java.util.List;
import java.util.Map;
@Repository
public interface EmployeeDao extends CrudDao<Employee> {
    Employee getByUsername(@Param("username") String username);
    Employee getByOrgCode(@Param("orgCode") String orgCode);
    List<Employee> searchWithOpenid(Map<String, Object> params);
    List<Long> getId();
    List<Employee> getByIds(@Param("ids") List<Long> ids);
    String getMaxNextOrgCode();
    Long getAllCount();
    List<Employee> findOutEmployee(@Param("id") Long id);
    List<Employee> findEmployee(@Param("id") Long id);
    List<Employee> getByUserJobNumForIDS(@Param("ids") List<String> ids);
    List<Employee> findOrgCodeByMobil(@Param("mobile") String mobile);
    List<Employee> findOrgCodeByName(@Param("name") String name);
    List<Employee> getAuditByOrgCode(@Param("storeCode")String storeCode);
}
