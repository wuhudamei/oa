package cn.damei.rest.organization;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.OrganizationTreeDto;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.organization.OrgState;
import cn.damei.entity.organization.ZTreeOrg;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/org")
public class MdniOrganizationController extends BaseController {
	@Autowired
	MdniOrganizationService mdniOrganizationService;
	@RequestMapping("/findAll")
	public Object findAll() {
		List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findByType("COMPANY");
		return StatusDto.buildSuccess(mdniOrganizationList);
	}
	@RequestMapping("/findDepartment/{familyCode}")
	public Object findDepartment(@PathVariable String familyCode) {
		List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findDepartment(familyCode);
		return StatusDto.buildSuccess(mdniOrganizationList);
	}
	@RequestMapping("/tree")
	public Object tree() {
		StatusDto result = StatusDto.buildSuccess();
		List<MdniOrganization> list = mdniOrganizationService.findAll();
		OrganizationTreeDto tree = MdniOrganization.buildOrganizationTree(new OrganizationTreeDto(0L), list);
		List<OrganizationTreeDto> childrenList=tree.getChildren();
		for (OrganizationTreeDto org : childrenList) {
			org.setState(new OrgState(true));
		}
		result.setData(childrenList);
		return result;
	}
	@RequestMapping("/fetchTree")
	public Object fetchTree() {
		List<ZTreeOrg> zTreeOrgList = mdniOrganizationService.fetchTree(null);
		for (ZTreeOrg z : zTreeOrgList) {
			if(z.getCode().equals("jt")){
				z.setOpen(true);
			}
			break;
		}
		return StatusDto.buildSuccess(zTreeOrgList);
	}
	@RequestMapping("/delete/{id}")
	public Object deleteById(@PathVariable Long id) {
		if (null == id) {
			return StatusDto.buildFailure("ID不能为空");
		}
		MdniOrganization mdniOrganization = mdniOrganizationService.getById(id);
		if (mdniOrganization.getParentId() == 0) {
			return StatusDto.buildFailure("初始化数据不允许删除");
		}
		int count = mdniOrganizationService.selectByparentId(mdniOrganization.getId());
		if (count > 0) {
			return StatusDto.buildFailure("含有子节点不允许删除");
		} else {
			mdniOrganizationService.disableOrg(id);
		}
		return StatusDto.buildSuccess("删除成功");
	}
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object add(MdniOrganization mdniOrganization) {
		if (mdniOrganizationService.validateCodeAvailable(mdniOrganization)) {
			return StatusDto.buildFailure("编码重复！");
		}
		mdniOrganizationService.saveOrUpdate(mdniOrganization);
		OrganizationTreeDto dto = new OrganizationTreeDto(mdniOrganization.getId(), mdniOrganization.getOrgName(),mdniOrganization.getType());
		return StatusDto.buildSuccess(dto);
	}
	@RequestMapping("/list")
	public Object list(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", defaultValue = "1", required = false) String status,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			@RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
			@RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "status", status);
		PageTable page = mdniOrganizationService.searchScrollPage(params, new Pagination(offset, limit));
		return StatusDto.buildSuccess(page);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatusDto getById(@PathVariable(value = "id") Long id) {
		MdniOrganization org = mdniOrganizationService.getById(id);
		if (org != null && org.getParentId() != null) {
			org.setParent(mdniOrganizationService.getById(org.getParentId()));
			org.setTmpId(org.getParentId());
		}
		if (org == null) {
			return StatusDto.buildFailure("获取组织架构失败！");
		}
		return StatusDto.buildSuccess(org);
	}
	@RequestMapping("/signTimeSet")
	public Object signTimeSet(@RequestParam(required = false) Long id,
							  @RequestParam(required = true) String signTime,
							  @RequestParam(required = true) String signOutTime){
		if(id == null){
			return StatusDto.buildFailure();
		}
		Date newSignTime;
		Date newSignOutTime;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			newSignTime = sdf.parse(signTime);
			newSignOutTime = sdf.parse(signOutTime);
		String familyCode = mdniOrganizationService.findFamilyCodeById(id);//通过id获取到familyCode
		mdniOrganizationService.setsignTime(familyCode,newSignTime,newSignOutTime);
		}catch (Exception e){
			e.printStackTrace();
		}
		return StatusDto.buildSuccess("设置成功");
	}
	@RequestMapping("/findTime")
	public Object findTime(@RequestParam(required = false) Long id){
		List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.getSignTime(id);
		return StatusDto.buildSuccess(mdniOrganizationList);
	}
}