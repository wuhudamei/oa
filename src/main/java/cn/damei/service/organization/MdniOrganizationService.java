package cn.damei.service.organization;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.User;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.organization.ZTreeOrg;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignRecord;
import cn.damei.repository.employee.EmployeeDao;
import cn.damei.repository.organization.MdniOrganizationDao;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.sign.SignRecordService;
import cn.damei.service.sign.SignService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import java.util.*;
@Service
public class MdniOrganizationService extends CrudService<MdniOrganizationDao, MdniOrganization> {
	@Autowired
	SignService signService;
	@Autowired
	private MdniOrganizationDao mdniOrganizationDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private SignRecordService signRecordService;
	public MdniOrganization getByCode(@Param("orgCode") String orgCode) {
		return this.entityDao.getByCode(orgCode);
	}
	public boolean validateCodeAvailable(MdniOrganization mdniOrganization) {
		boolean flag = true;
		String orgCode = mdniOrganization.getOrgCode();
		MdniOrganization mdniOrganization1 = this.entityDao.getByCode(orgCode);
		if (mdniOrganization1 == null) {
			flag = false;
		} else {
			// 只有查到的和自己传入的一样才能返回false
			if (mdniOrganization.getId() != null) {
				String selfCode = this.entityDao.getById(mdniOrganization.getId()).getOrgCode();
				if (selfCode.equals(orgCode)) {
					flag = false;
				}
			}
		}
		return flag;
	}
	public Object saveOrUpdate(MdniOrganization mdniOrganization) {
		MdniOrganization orgFamily = null;
		if (mdniOrganization.getId() == null) {
			User user = new User(WebUtils.getLoggedUser().getId());
			mdniOrganization.setCreateUser(user.getId());
			mdniOrganization.setCreateDate(DateUtils.format(new Date(), "yyyy-MM-dd"));
			mdniOrganization.setRemindMessageType(0);
			this.entityDao.insert(mdniOrganization);
			orgFamily = this.entityDao.getByCode(mdniOrganization.getOrgCode());
		} else {
			orgFamily = this.entityDao.getById(mdniOrganization.getId());
			orgFamily.setOrgCode(mdniOrganization.getOrgCode());
			orgFamily.setOrgName(mdniOrganization.getOrgName());
			orgFamily.setParentId(mdniOrganization.getParentId());
			orgFamily.setSort(mdniOrganization.getSort());
			orgFamily.setType(mdniOrganization.getType());
			//设置是否为门店
			orgFamily.setStoreFlag(mdniOrganization.getStoreFlag());
		}
		// 原父节点数据
		MdniOrganization oldM = this.entityDao.getById(mdniOrganization.getParentId());
		// 如果父ID和临时父ID不一致，则需要把原来父ID下面的叶子的FamilyCode全部修改为临时父ID下的
		if (!mdniOrganization.getParentId().equals(mdniOrganization.getTmpId())) {
			MdniOrganization newM = this.entityDao.getById(mdniOrganization.getTmpId());
			// 该节点原来的FamilyCode
			String oldFamilyCode = orgFamily.getFamilyCode();
			// 该节点修改父节点后的FamilyCode
			String newFamilyCode = mdniOrganization.getFamilyCode();
			// 这个是设置新设置的父Code
			if (mdniOrganization.getTmpId() != 0) {
				orgFamily.setFamilyCode(newM.getFamilyCode() + "-" + orgFamily.getId());
				newFamilyCode = newM.getFamilyCode() + "-" + orgFamily.getId();
			} else {
				orgFamily.setFamilyCode(mdniOrganization.getFamilyCode());
			}
			// 设置新的父ID
			orgFamily.setParentId(mdniOrganization.getTmpId());
			// 把原来节点下面的把有节点的FamilyCode修改为新的
			handleFamilyCode(oldFamilyCode, newFamilyCode);
		}
		// 父ID和临时ID一致 在新添加节点和未修改父节点的时候会到这个分支
		else {
			// 如果父ID不为0，则根据父ID查询出相应的FamilyCode 加当前ID 拼装出 FamilyCode
			if (mdniOrganization.getParentId() != 0) {
				orgFamily.setFamilyCode(oldM.getFamilyCode() + "-" + orgFamily.getId());
			}
			// 如果父ID为0，则FamilyCode为自已的FamilyCode
			else {
				orgFamily.setFamilyCode(mdniOrganization.getFamilyCode());
			}
		}
		this.entityDao.update(orgFamily);
		return StatusDto.buildSuccess();
	}
	private void handleFamilyCode(String oldFamilyCode, String newFamilyCode) {
		// 根据旧FamilyCode查询出来所有的叶子节点
		List<MdniOrganization> mdniOrganizations = this.entityDao.selectOrgByFamilyCode(oldFamilyCode);
		if (mdniOrganizations == null || mdniOrganizations.size() == 0) return;
		for (MdniOrganization mdniOrganization : mdniOrganizations) {
			mdniOrganization.setFamilyCode(mdniOrganization.getFamilyCode().replace(oldFamilyCode, newFamilyCode));
			this.entityDao.update(mdniOrganization);
		}
	}
	public void disableOrg(Long id) {
		this.entityDao.disableOrg(id);
	}
	public List<MdniOrganization> findByType(String type) {
		List<MdniOrganization> mdniOrganizationList = this.entityDao.findByType(type);
		return mdniOrganizationList;
	}
	public List<MdniOrganization> findDepartment(String familyCode) {
		return this.entityDao.findDepartment(familyCode + "-");
	}
	public Integer selectByparentId(Long parentId) {
		return this.entityDao.selectByparentId(parentId);
	}
	public List<ZTreeOrg> fetchTree(List<Long> orgIds) {
		List<MdniOrganization> mdniOrganizationList = this.entityDao.findAll();
		List<ZTreeOrg> zTreeOrgList = new ArrayList<>();
		for (MdniOrganization mdniOrganization : mdniOrganizationList) {
			ZTreeOrg z = new ZTreeOrg();
			z.setId(mdniOrganization.getId());
			z.setpId(mdniOrganization.getParentId());
			z.setName(mdniOrganization.getOrgName());
			z.setType(mdniOrganization.getType());
			z.setCode(mdniOrganization.getOrgCode());
			z.setFamilyCode(mdniOrganization.getFamilyCode());
			if (CollectionUtils.isNotEmpty(orgIds) && orgIds.contains(mdniOrganization.getId())) {
				z.setChecked(true);
			}
			zTreeOrgList.add(z);
		}
		return zTreeOrgList;
	}
	public Object getOrgByLastCode(String code) {
		Map<String, Long> paramsMap = new HashMap<>();
		MdniOrganization mdniOrganization = this.entityDao.getOrgByLastCode(code);
		String[] familyArr = {};
		if (mdniOrganization != null) {
			familyArr = mdniOrganization.getFamilyCode().split("-");
		}
		for (int i = familyArr.length - 1; i >= 0; i--) {
			MdniOrganization mdniOrganization1 = this.entityDao.getById(Long.parseLong(familyArr[i]));
			if (mdniOrganization1.getType().equals("DEPARTMENT")) {
				if (paramsMap.get("DEPARTMENT") == null) {
					paramsMap.put("DEPARTMENT", mdniOrganization1.getId());
				}
			} else if (mdniOrganization1.getType().equals("COMPANY") || mdniOrganization1.getType().equals("BASE")) {
				paramsMap.put("COMPANY", mdniOrganization1.getId());
				break;
			}
			continue;
		}
		return StatusDto.buildSuccess(paramsMap);
	}
	public String findFamilyCodeById(Long id) {
		return entityDao.findFamilyCodeById(id);
	}
	public void setsignTime(String familyCode, Date signTime, Date signOutTime) {
		entityDao.setsignTime(familyCode, signTime, signOutTime);
	}
	public Date findSignTimeByDepartmentId(Long departmentId) {
		return entityDao.findSignTimeByDepartmentId(departmentId);
	}
	public Date findSignOutTimeByDepartmentId(Long departmentId) {
		return entityDao.findSignOutTimeByDepartmentId(departmentId);
	}
	public List<MdniOrganization> getByIds (List < Long > ids) {
		return this.entityDao.getByIds(ids);
	}
	public Date getSignTimeByDepId(Long department) {
		return entityDao.getSignTimeByDepId(department);
	}
	public Date getSignOutTimeByDepId(Long department) {
		return entityDao.getSignOutTimeByDepId(department);
	}
	public List<MdniOrganization> getSignTime(Long id) {
		return entityDao.getSignTime(id);
	}
	public Sign findTime() {
		Sign sign = new Sign();
		//查询部门签到时间
		Long departmentId = WebUtils.getLoggedUser().getDepartmentId();
		Date depSignTime = entityDao.findSignTimeByDepartmentId(departmentId);
		sign.setDepSignTime(depSignTime);
		//查询部门签退时间
		Date depSignOutTime = entityDao.findSignOutTimeByDepartmentId(departmentId);
		sign.setDepSignOutTime(depSignOutTime);
		//查询签到时间
		Date date = new Date();
		String formatDate = DateUtils.format(date, "yyyy-MM-dd");
		ShiroUser user = WebUtils.getLoggedUser();
		Map<String, Object> map = new HashedMap();
		map.put("employeeId", user.getId());
		map.put("date", formatDate);
		SignRecord signData = signRecordService.getByEmpIdAndDate(map);
		if(signData != null) {
			sign.setSignTime(signData.getSignDate());
		}else {
			sign.setSignTime(null);
		}
		//查询签退时间
		SignRecord signData2 = signRecordService.getSignOutTime(user.getId(),formatDate);
		if(signData2 != null){
			sign.setSignOutTime(signData2.getSignDate());
		}else {
			sign.setSignOutTime(null);
		}
		return sign;
	}
	public List<MdniOrganization> findAllSignTime() {
		return entityDao.findAllSignTime();
	}
	public void updateRemindMessageType(int type,Long id) {
		entityDao.updateRemindMessageType(type,id);
	}
	public void updateOutRemindMessageType(int type, Long id) {
		entityDao.updateOutRemindMessageType(type,id);
	}
	public List<MdniOrganization> findIdAndSIgnTime() {
		return entityDao.findIdAndSIgnTime();
	}
	public List<MdniOrganization> findComIdAndSignTime() {
		return entityDao.findComIdAndSignTime();
	}
	public void updateRemMessage() {
		entityDao.updateRemMessage();
	}
	public List<MdniOrganization> findStores() {
		return mdniOrganizationDao.findStores();
	}
	public List<Map<String,String>> getDesignGroup(String storeCode){
		return mdniOrganizationDao.getDesignGroup(storeCode);
	}
	public List<Map<String,String>> getEmployeeByOrgCode(String orgCode){
		return mdniOrganizationDao.getEmployeeByOrgCode(orgCode);
	}
	public List<Employee> getAuditByOrgCode(String storeCode) {
		return employeeDao.getAuditByOrgCode(storeCode);
	}
}