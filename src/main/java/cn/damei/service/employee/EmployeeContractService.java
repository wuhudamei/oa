package cn.damei.service.employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.employee.EmployeeContract;
import cn.damei.repository.employee.EmployeeContractDao;
@Service
@Transactional
public class EmployeeContractService extends CrudService<EmployeeContractDao, EmployeeContract> {
}