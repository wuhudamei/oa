package cn.damei.service.employee;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.entity.employee.EmployeeEdu;
import cn.damei.repository.employee.EmployeeEduDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EmployeeEduService extends CrudService<EmployeeEduDao, EmployeeEdu> {
    public void batchInsert(List<EmployeeEdu> edus) {
        if (CollectionUtils.isNotEmpty(edus)) {
            this.entityDao.batchInsert(edus);
        }
    }
    public List<EmployeeEdu> findByEmpId(Long empId) {
        if (empId != null)
            return this.entityDao.findByEmpId(empId);
        return Lists.newArrayList();
    }
    public int deleteByEmpId(Long empId) {
        if (empId != null)
            return this.entityDao.deleteByEmpId(empId);
        return 0;
    }
}
