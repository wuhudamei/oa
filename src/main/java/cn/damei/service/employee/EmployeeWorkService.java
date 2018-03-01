package cn.damei.service.employee;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.entity.employee.EmployeeWork;
import cn.damei.repository.employee.EmployeeWorkDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EmployeeWorkService extends CrudService<EmployeeWorkDao, EmployeeWork> {
    public void batchInsert(List<EmployeeWork> works) {
        if (CollectionUtils.isNotEmpty(works)) {
            this.entityDao.batchInsert(works);
        }
    }
    public List<EmployeeWork> findByEmpId(Long empId) {
        if (empId != null) {
            return this.entityDao.findByEmpId(empId);
        }
        return Lists.newArrayList();
    }
    public int deleteByEmpId(Long empId){
        if (empId != null) {
            return this.entityDao.deleteByEmpId(empId);
        }
        return 0;
    }
}
