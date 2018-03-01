package cn.damei.repository.employee;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.organization.MdniOrganization;
@Repository
public interface EmployeeOrgDao extends CrudDao<EmployeeOrg> {
    int batchInsert(@Param("employeeOrgs") List<EmployeeOrg> employeeOrgs);
    List<EmployeeOrg> getByCondition(Map<String, Object> paramMap);
    Map<String, Double> getPrincipalByOrgId(Map<String, Long> paramMap);
    EmployeeOrg getDirectByEmpId(@Param("empId") Long empId);
    int deleteDirectByEmpId(@Param("empId") Long empId);
    List<EmployeeOrg> findPartTimeByEmpId(@Param("empId") Long empId);
    int deletePartTimeByEmpId(@Param("empId") Long empId);
    void removeDepartmentPrincipal(@Param("orgId") Long orgId);
    void setDepartmentPrincipal(@Param("empId") Long empId, @Param("orgId") Long orgId);
    void removeCompanyPrincipal(@Param("orgId") Long orgId);
    void setCompanyPrincipal(@Param("empId") Long empId, @Param("orgId") Long orgId);
    EmployeeOrg findDepartmentId(@Param("employeeId") Long employeeId);
    Long getAllCount();
    List<MdniOrganization> findIdAndSIgnTime();
    List<Long> findEmpIdByDepId(@Param("depId") Long depId);
    List<Long> findEmpIdByComId(@Param("depId") Long id);
    List<EmployeeOrg> findComIdAndSignTime();
    List<EmployeeOrg> findAllZZEmp(@Param("lastMonth") Date lastMonth, @Param("thisMonth")Date thisMonth);
    List<EmployeeOrg> findProductDep();
    List<EmployeeOrg> findEmpZZCP();
    List<EmployeeOrg> findEmpZZ();
    List<EmployeeOrg> findNoSignEmp();
}
