package cn.damei.service.oa;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.oa.ApplyBusinessAway;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.oa.ApplyBusinessAwayDao;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
@SuppressWarnings("all")
@Service
public class ApplyBusinessAwayService  extends CrudService<ApplyBusinessAwayDao,ApplyBusinessAway> {
    @Autowired
    ApplyBusinessAwayDao businessAwayDao;
    @Autowired
    ApplySequenceService applySequenceService;
    @Autowired
    WfProcessService wfProcessService;
    @Transactional
    public int insert(ApplyBusinessAway businessAway,boolean isSubmit) {
        try{
            ShiroUser loginUser = WebUtils.getLoggedUser();
            Date date = new Date();
            String applyTitle = DateUtils.format(date, "yyyy年MM月dd日") + " 出差申请";
            String applyCode = applySequenceService.getSequence(WfApplyTypeEnum.BUSINESS);
            businessAway.setApplyUserId(loginUser.getId());
            businessAway.setApplyUserName(loginUser.getName());
            businessAway.setApplyTitle(applyTitle);
            businessAway.setApplyCode(applyCode);
            businessAway.setCreateUser(Integer.parseInt(loginUser.getId().toString()));
            businessAway.setCreateTime(date);
            if( isSubmit ){
                businessAway.setStatus(ApplyStatus.APPROVALING);
            }else{
                businessAway.setStatus(ApplyStatus.DRAFT);
            }
            int result = businessAwayDao.insert(businessAway);
            if (result > 0 && isSubmit) {
                createWf(businessAway,loginUser);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Transactional
    public int update(ApplyBusinessAway businessAway,boolean isSubmit) {
        try{
            ShiroUser loginUser = WebUtils.getLoggedUser();
            businessAway.setCreateTime(new Date());
            if( isSubmit ){
                businessAway.setStatus(ApplyStatus.APPROVALING);
            }
            int result = businessAwayDao.update(businessAway);
            if (result > 0 && isSubmit) {
                createWf(businessAway,loginUser);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Transactional
    public int submit(Long id) {
        try{
            ShiroUser loginUser = WebUtils.getLoggedUser();
            ApplyBusinessAway businessAway = businessAwayDao.getById(id);
            businessAway.setStatus(ApplyStatus.APPROVALING);
            businessAway.setCreateTime(new Date());
            int result = businessAwayDao.update(businessAway);
            if (result > 0 ) {
                createWf(businessAway,loginUser);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    private int createWf(ApplyBusinessAway businessAway,ShiroUser loginUser) throws Exception{
        WfProcessParam param = new WfProcessParam();
        param.setFormId(businessAway.getId());
        param.setApplyUserId(loginUser.getId());
        param.setApplyTitle(businessAway.getApplyTitle());
        param.setApplyCode(businessAway.getApplyCode());
        param.setCompanyId(loginUser.getCompanyId());
        param.setDepartmentId(loginUser.getDepartmentId());
        param.setOrgId(loginUser.getOrgId());
        param.setApplyType(WfApplyTypeEnum.BUSINESS.name());
		param.setGenertaeWfProcessType(0L);
       return wfProcessService.createWf(param);
    }
}