package cn.damei.service.employee;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.repository.employee.EmployeeOrgDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
@Service
public class EmployeeOrgService extends CrudService<EmployeeOrgDao, EmployeeOrg> {
    public EmployeeOrg getDirectByEmpId(Long empId) {
        if (empId != null) {
            return this.entityDao.getDirectByEmpId(empId);
        }
        return null;
    }
    public int deleteDirectByEmpId(Long empId) {
        if (empId != null) {
            return this.entityDao.deleteDirectByEmpId(empId);
        }
        return 0;
    }
    public List<EmployeeOrg> findPartTimeByEmpId(Long empId) {
        if (empId != null) {
            return this.entityDao.findPartTimeByEmpId(empId);
        }
        return Lists.newArrayList();
    }
    public int deletePartTimeByEmpId(Long empId) {
        if (empId != null) {
            return this.entityDao.deletePartTimeByEmpId(empId);
        }
        return 0;
    }
    public void batchInsert(List<EmployeeOrg> empOrgs) {
        if (CollectionUtils.isNotEmpty(empOrgs)) {
            this.entityDao.batchInsert(empOrgs);
        }
    }
    public void removeDepartmentPrincipal(Long departmentId) {
        if (departmentId != null) {
            this.entityDao.removeDepartmentPrincipal(departmentId);
        }
    }
    public void setDepartmentPrincipal(Long empId, Long departmentId) {
        if (empId != null && departmentId != null) {
            this.entityDao.setDepartmentPrincipal(empId, departmentId);
        }
    }
    public void removeCompanyPrincipal(Long companyId) {
        if (companyId != null) {
            this.entityDao.removeCompanyPrincipal(companyId);
        }
    }
    public void setCompanyPrincipal(Long empId, Long companyId) {
        if (empId != null && companyId != null) {
            this.entityDao.setCompanyPrincipal(empId, companyId);
        }
    }
    public EmployeeOrg findDepartmentId(Long employeeId) {
       return entityDao.findDepartmentId(employeeId);
    }
    public Long getAllCount() {
        return  entityDao.getAllCount();
    }
    public List<MdniOrganization> findIdAndSIgnTime() {
        return entityDao.findIdAndSIgnTime();
    }
    public List<Long> findEmpIdByDepId(Long depId) {
        return entityDao.findEmpIdByDepId(depId);
    }
    public List<Long> findEmpIdByComId(Long id) {
        return entityDao.findEmpIdByComId(id);
    }
    public List<EmployeeOrg> findComIdAndSignTime() {
        return entityDao.findComIdAndSignTime();
    }
    public List<EmployeeOrg> findAllZZEmp(Date lastMonth, Date thisMonth) {
        return this.entityDao.findAllZZEmp(lastMonth,thisMonth);
    }
    public List<EmployeeOrg> findProductDep() {
        return this.entityDao.findProductDep();
    }
    public List<EmployeeOrg> findEmpZZCP() {
        return this.entityDao.findEmpZZCP();
    }
    public List<EmployeeOrg> findEmpZZ() {
        return this.entityDao.findEmpZZ();
    }
    public List<EmployeeOrg> findNoSignEmp() {
        return this.entityDao.findNoSignEmp();
    }
}
