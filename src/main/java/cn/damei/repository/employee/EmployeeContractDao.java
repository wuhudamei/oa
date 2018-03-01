package cn.damei.repository.employee;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.employee.EmployeeContract;
@Repository
public interface EmployeeContractDao extends CrudDao<EmployeeContract> {
}
