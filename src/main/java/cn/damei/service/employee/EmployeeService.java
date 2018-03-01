package cn.damei.service.employee;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.dto.employee.EmpOrgDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.employee.EmployeeEdu;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.employee.EmployeeWork;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.organization.ZTreeOrg;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.enumeration.org.EmployeeOrgType;
import cn.damei.repository.employee.EmployeeDao;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.shiro.PasswordUtil;
import cn.damei.utils.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class EmployeeService extends CrudService<EmployeeDao, Employee> {
	@Autowired
	private EmployeeEduService employeeEduService;
	@Autowired
	private EmployeeWorkService employeeWorkService;
	@Autowired
	private EmployeeOrgService employeeOrgService;
	@Autowired
	private MdniOrganizationService orgService;
	@Autowired
	private WechatUserService wechatUserService;
	public PageTable searchScrollPageWithOpenid(Map<String, Object> params, Pagination pagination) {
		// 设置排序条件
		params.put("sort", pagination.getSort());
		// 利用pagehelper分页查询
		PageHelper.offsetPage(pagination.getOffset(), pagination.getLimit());
		Page<Employee> result = (Page<Employee>) this.entityDao.searchWithOpenid(params);
		// 返回查询结果
		return new PageTable<>(result.getResult(), result.getTotal());
	}
	@Override
	@Transactional
	public int insert(Employee employee) {
		this.generatePassword(employee);
		employee.setJobNum(UUIDUtils.generateJobNum());
		int i = super.insert(employee);
		if (i > 0) {
			Long empId = employee.getId();
			// 插入员工的直属机构信息
			if (employee.getOrgId() != null) {
				EmployeeOrg org = this.processEmployeeOrg(empId, employee.getOrgId(), EmployeeOrgType.DIRECTLY);
				if (org != null) {
					this.employeeOrgService.insert(org);
				}
			}
			// 插入员工教育经历和工作经历
			this.batchInsertEdus(employee.getEdus(), empId);
			this.batchInsertWorks(employee.getWorks(), empId);
		}
		return i;
	}
	public synchronized String getMaxNextOrgCode() {
		return this.entityDao.getMaxNextOrgCode();
	}
	private EmployeeOrg processEmployeeOrg(Long empId, Long orgId, EmployeeOrgType employeeOrgType) {
		if (Objects.nonNull(empId) && Objects.nonNull(orgId)) {
			EmployeeOrg empOrg = new EmployeeOrg();
			empOrg.setEmployee(new Employee(empId)).setOrg(new MdniOrganization(orgId)).setType(employeeOrgType);
			this.getDepartmentCompany(orgId, empOrg);
			return empOrg;
		}
		return null;
	}
	private void getDepartmentCompany(Long orgId, EmployeeOrg empOrg) {
		if (orgId != null) {
			StatusDto res = (StatusDto) this.orgService.getOrgByLastCode(String.valueOf(orgId));
			if (res.getData() != null) {
				Map<String, Long> map = (Map<String, Long>) res.getData();
				if (map.get("COMPANY") != null) {
					empOrg.setCompany(new MdniOrganization(map.get("COMPANY")));
				}
				if (map.get("DEPARTMENT") != null) {
					empOrg.setDepartment(new MdniOrganization(map.get("DEPARTMENT")));
				}
			}
		}
	}
	private static final String DEFAULT_PASSWORD = "123456";
	private void generatePassword(Employee employee) {
		if (employee != null) {
			String salt = PasswordUtil.generateSalt();
			String password = PasswordUtil.entryptUserPassword(DEFAULT_PASSWORD, salt);
			employee.setSalt(salt);
			employee.setPassword(password);
		}
	}
	private void batchInsertEdus(List<EmployeeEdu> edus, Long empId) {
		if (CollectionUtils.isNotEmpty(edus)) {
			for (EmployeeEdu edu : edus) {
				edu.setEmpId(empId);
			}
			this.employeeEduService.batchInsert(edus);
		}
	}
	private void batchInsertWorks(List<EmployeeWork> works, Long empId) {
		if (CollectionUtils.isNotEmpty(works)) {
			for (EmployeeWork work : works) {
				work.setEmpId(empId);
			}
			this.employeeWorkService.batchInsert(works);
		}
	}
	@Transactional
	public int updateFullInfo(Employee employee) {
		int i = super.update(employee);
		if (i > 0) {
			Long empId = employee.getId();
			// 更新员工的直属部门信息
			if (employee.getOrgId() != null) {
				this.employeeOrgService.deleteDirectByEmpId(empId);
				EmployeeOrg org = this.processEmployeeOrg(empId, employee.getOrgId(), EmployeeOrgType.DIRECTLY);
				if (org != null) {
					this.employeeOrgService.insert(org);
				}
			}
			// 更新员工教育经历和工作经历
			this.batchUpdateEdus(employee.getEdus(), empId);
			this.batchUpdateWorks(employee.getWorks(), empId);
		}
		return i;
	}
	private void batchUpdateEdus(List<EmployeeEdu> edus, Long empId) {
		this.employeeEduService.deleteByEmpId(empId);
		this.batchInsertEdus(edus, empId);
	}
	private void batchUpdateWorks(List<EmployeeWork> works, Long empId) {
		this.employeeWorkService.deleteByEmpId(empId);
		this.batchInsertWorks(works, empId);
	}
	public Employee getByUsername(String username) {
		if (StringUtils.isNotBlank(username))
			return this.entityDao.getByUsername(username);
		return null;
	}
	public Employee getByOrgCode(String orgCode) {
		if (StringUtils.isNotBlank(orgCode)) {
			return this.entityDao.getByOrgCode(orgCode);
		}
		return null;
	}
	public boolean validateOrgCode(String orgCode, Long empId) {
		Employee e = this.getByOrgCode(orgCode);
		if (e == null || e.getId().equals(empId)) {
			return true;
		}
		return false;
	}
	public boolean validateUsername(String username, Long empId) {
		Employee e = this.getByUsername(username);
		if (e == null || e.getId().equals(empId)) {
			return true;
		}
		return false;
	}
	@Override
	@Transactional
	public int deleteById(Long id) {
		if (id == null)
			return 0;
		// 删除用户微信绑定信息
		wechatUserService.deleteByUserId(id);
		Employee employee = new Employee(id);
		employee.setDeleted(true);
		return this.update(employee);
	}
	public Employee getFullInfoById(Long id) {
		if (id == null)
			return null;
		Employee employee = this.getById(id);
		if (employee != null) {
			// 查询员工直属机构信息
			EmployeeOrg org = this.employeeOrgService.getDirectByEmpId(employee.getId());
			if (org != null) {
				employee.setOrg(org);
			}
			// 查询员工的教育经历和工作经历
			employee.setEdus(this.employeeEduService.findByEmpId(id));
			employee.setWorks(this.employeeWorkService.findByEmpId(id));
		}
		return employee;
	}
	public List<ZTreeOrg> buildOrgsTree(Long id) {
		// 查询员工兼职机构
		List<EmployeeOrg> orgs = this.employeeOrgService.findPartTimeByEmpId(id);
		// 用来保存员工兼职机构的id
		List<Long> orgIds = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (EmployeeOrg org : orgs) {
				orgIds.add(this.getPartTimeOrgId(org));
			}
		}
		// 过滤掉orgIds中为null的数据
		List<Long> nonNullIds = orgIds.stream().filter(Objects::nonNull).collect(Collectors.toList());
		// 查询所有机构，并构建树
		return this.orgService.fetchTree(nonNullIds);
	}
	private Long getPartTimeOrgId(EmployeeOrg org) {
		Optional<EmployeeOrg> orgOptional = Optional.ofNullable(org);
		return orgOptional.map(EmployeeOrg::getOrg).map(MdniOrganization::getId).orElse(null);
	}
	@Transactional
	public void savePartTimeOrgs(Long id, List<EmpOrgDto> orgs) {
		// 先删除原有信息
		this.employeeOrgService.deletePartTimeByEmpId(id);
		// 处理现新的的机构信息
		List<EmployeeOrg> empOrgs = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(orgs)) {
			for (EmpOrgDto o : orgs) {
				EmployeeOrg empOrg = this.processEmployeeOrg(id, o.getId(), EmployeeOrgType.PART_TIME);
				empOrgs.add(empOrg);
			}
		}
		this.employeeOrgService.batchInsert(empOrgs);
	}
	@Transactional
	public void setDepartmentPrincipal(Long empId, Long departmentId) {
		this.employeeOrgService.removeDepartmentPrincipal(departmentId);
		this.employeeOrgService.setDepartmentPrincipal(empId, departmentId);
	}
	@Transactional
	public void setCompanyPrincipal(Long empId, Long companyId) {
		this.employeeOrgService.removeCompanyPrincipal(companyId);
		this.employeeOrgService.setCompanyPrincipal(empId, companyId);
	}
	@Transactional(rollbackFor = Exception.class)
	public void dimission(Long id) {
		if (id != null) {
			Employee employee = new Employee(id);
			employee.setDimissionDate(new Date());
			employee.setEmployeeStatus(EmployeeStatus.DIMISSION);
			super.update(employee);
			// 删除用户微信绑定信息
			wechatUserService.deleteByUserId(id);
		}
	}
    public List<Long> getId() {
       return entityDao.getId();
    }
	public List<Employee> getByIds(List<Long> ids) {
		return this.entityDao.getByIds(ids);
	}
    public Long getAllCount() {
		return entityDao.getAllCount();
    }
    public List<Employee> findOutEmployee(Long id) {
		return entityDao.findOutEmployee(id);
    }
	public List<Employee> findEmployee(Long id) {
		return entityDao.findEmployee(id);
	}
	public List<Employee> getByUserJobNumForIDS(List<String> ids){
		return entityDao.getByUserJobNumForIDS(ids);
	}
}
