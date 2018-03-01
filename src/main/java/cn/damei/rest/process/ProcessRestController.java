package cn.damei.rest.process;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.dto.StatusDto;
import cn.damei.dto.process.ProcessEntityTreeDto;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.process.ProcessEntity;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.process.ProcessService;
import cn.damei.service.subjectprocess.SubjectProcessService;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
@RestController
@RequestMapping("/api/process")
public class ProcessRestController {
	@Autowired
	private ProcessService processService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MdniOrganizationService mdniOrganizationService;
	@Autowired
	private SubjectProcessService subjectProcessService;
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Object edit(@RequestBody ProcessEntity permission) {
		// 判断该流程下机构是否重复选择
		if (StringUtils.isNotBlank(permission.getApplyOrg()) && permission.getWfNature() != null && processService.checkOrg(permission)) {
			return StatusDto.buildFailure("同一个流程机构不能重复选择");
		}
		// 判断上级机构是否有人在操作 删除
		if (permission.getPid() != null && 0 == processService.getById(permission.getPid()).getStatus()) {
			return StatusDto.buildFailure("父级节点数据已被删除，请刷新页面后再操作!");
		}
		ProcessEntityTreeDto treeDto = processService.createOrUpdate(permission);
		return StatusDto.buildSuccess("操作成功", treeDto);
	}
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public Object search() {
		ProcessEntityTreeDto processEntityTreeDto = processService.findProcessTree();
		if (processEntityTreeDto != null) {
			return StatusDto.buildSuccess(processEntityTreeDto);
		}
		return StatusDto.buildFailure("无审批流程!");
	}
	@RequestMapping(value = "/findByPid")
	public Object fetchByParentId(@RequestParam("pid") Long pid,
								  @RequestParam("wfNature") String wfNature) {
		ProcessEntityTreeDto processEntityTreeDto = processService.findProcessTreeByParentId(pid, wfNature);
		if (processEntityTreeDto != null) {
			return StatusDto.buildSuccess(processEntityTreeDto);
		}
		return StatusDto.buildFailure("无节点!");
	}
	@RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
	public Object getById(@PathVariable(value = "id") Long id) {
		ProcessEntity pe = processService.getById(id);
		// 审批人
		if (pe != null && StringUtils.isNotBlank(pe.getApprover())) {
			String[] ids = pe.getApprover().split(",");
			List<Long> lIds = new ArrayList<Long>();
			for (String s : ids) {
				lIds.add(Long.valueOf(s));
			}
			StringBuffer approverNames = new StringBuffer();
			for (Employee e : employeeService.getByIds(lIds)) {
				approverNames.append(e.getName()).append(",");
			}
			pe.setApproverName(approverNames.substring(0, approverNames.length() - 1).toString());
		}
		// 抄送人
		if (pe != null && StringUtils.isNotBlank(pe.getCcUser())) {
			String[] ids = pe.getCcUser().split(",");
			List<Long> lIds = new ArrayList<Long>();
			for (String s : ids) {
				lIds.add(Long.valueOf(s));
			}
			StringBuffer ccNames = new StringBuffer();
			for (Employee e : employeeService.getByIds(lIds)) {
				ccNames.append(e.getName()).append(",");
			}
			pe.setCcUserName(ccNames.substring(0, ccNames.length() - 1).toString());
		}
		// 申请部门
		if (pe != null && StringUtils.isNotBlank(pe.getApplyOrg())) {
			String[] ids = pe.getApplyOrg().substring(1, pe.getApplyOrg().length() - 1).split(",");
			List<Long> lIds = new ArrayList<Long>();
			for (String s : ids) {
				lIds.add(Long.valueOf(s));
			}
			StringBuffer applyOrgs = new StringBuffer();
			for (MdniOrganization e : mdniOrganizationService.getByIds(lIds)) {
				applyOrgs.append(e.getOrgName()).append(",");
			}
			pe.setApplyOrgName(applyOrgs.substring(0, applyOrgs.length() - 1).toString());
		}
		return StatusDto.buildSuccess(pe);
	}
	 @RequestMapping(value = "/{id}/del")
	 public StatusDto delete(@PathVariable(value = "id") Long id) {
		if (subjectProcessService.getByWfId(id) > 0) {
			return StatusDto.buildFailure("该流程已被科目流程管理相关的流程类型和费用引用，请先取消相关引用！");
		}
	 	ProcessEntity pe = new ProcessEntity();
	 	pe.setId(id);
	 	pe.setStatus(0);
	 	processService.update(pe);
	 	return StatusDto.buildSuccess("删除成功！");
	 }
}
