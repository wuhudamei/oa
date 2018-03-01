package cn.damei.service.vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.vacation.Vacation;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.vacation.VacationDao;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import java.util.Date;
@Service
public class VacationService extends CrudService<VacationDao, Vacation> {
    @Autowired
    private WfProcessService wfProcessService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ApplySequenceService applySequenceService;
    @Transactional
    public int insert(Vacation vacation,boolean isSubmit) {
        ShiroUser loginUser = WebUtils.getLoggedUser();
        Date date = new Date();
        String applyTitle = DateUtils.format(date,"yyyy年MM月dd日") +  " 请假申请";
        String applyCode = applySequenceService.getSequence( WfApplyTypeEnum.LEAVE );
        vacation.setApplyTitle( applyTitle );
        vacation.setApplyCode( applyCode );
        vacation.setEmployee(new Employee(loginUser.getId()));
        vacation.setCreateTime( date );
        if(isSubmit){
        	vacation.setStatus(ApplyStatus.APPROVALING);
        }else{
        	vacation.setStatus(ApplyStatus.DRAFT);
        }
        int i=0;
		try {
			i = super.insert(vacation);
			if (i > 0 && isSubmit) {
				//创建审批流程
			    createWf(vacation,loginUser);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return i;
    }
    @Transactional
    public int update(Vacation vacation,boolean isSubmit) {
        ShiroUser loginUser = WebUtils.getLoggedUser();
        vacation.setCreateTime(new Date());
        if( isSubmit ){
        	vacation.setStatus(ApplyStatus.APPROVALING);
        }
        int i=0;
		try {
			i = super.update(vacation);
			if (i > 0 && isSubmit) {
			    createWf(vacation,loginUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return i;
    }
    private int createWf(Vacation vacation,ShiroUser loginUser) throws Exception{
        WfProcessParam param = new WfProcessParam();
        param.setFormId( vacation.getId() );
        param.setApplyUserId( loginUser.getId());
        param.setApplyTitle( vacation.getApplyTitle() );
        param.setApplyCode( vacation.getApplyCode() );
        param.setCompanyId( loginUser.getCompanyId() );
        param.setDepartmentId( loginUser.getDepartmentId() );
        param.setOrgId(loginUser.getOrgId());
        param.setApplyType( WfApplyTypeEnum.LEAVE.name() );
        param.setGenertaeWfProcessType(0L);
       return wfProcessService.createWf(param);
    }
    @Transactional
    public int submit(Long id) {
        try{
            ShiroUser loginUser = WebUtils.getLoggedUser();
            Vacation vacation = super.getById(id);
            vacation.setCreateTime(new Date());
            vacation.setStatus(ApplyStatus.APPROVALING);
            int result = super.update(vacation);
            if (result > 0 ) {
                createWf(vacation,loginUser);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public Vacation getById(Long id) {
        Vacation vacation = super.getById(id);
        if (vacation != null) {
            Employee employee = this.employeeService.getById(vacation.getEmployee().getId());
            if (employee != null)
                vacation.setEmployee(employee);
        }
        return vacation;
    }
    @Override
    public int deleteById(Long id) {
        Vacation v = super.getById(id);
        if (v == null || !ApplyStatus.DRAFT.equals(v.getStatus()))
            return 0;
        return super.deleteById(id);
    }
}
