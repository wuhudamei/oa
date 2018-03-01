package cn.damei.rest.system;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.system.SystemParam;
import cn.damei.service.system.SystemParamService;
import cn.damei.utils.MapUtils;
@RestController
@RequestMapping("/api/system/param")
public class SystemParamRestController extends BaseController {
	@Autowired
	private SystemParamService systemParamService;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit) {
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		PageTable<SystemParam> pageTable = this.systemParamService.searchScrollPage(params,
				new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
		return StatusDto.buildSuccess(pageTable);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public StatusDto edit(SystemParam param) {
		if (StringUtils.isEmpty(param.getParamKeyName())) {
			return StatusDto.buildFailure("参数名称不能为空");
		}
		if (StringUtils.isEmpty(param.getParamValue())) {
			return StatusDto.buildFailure("参数值不能为空");
		}
		this.systemParamService.update(param);
		return StatusDto.buildSuccess("编辑成功");
	}
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public StatusDto change(SystemParam param) {
		if (StringUtils.isEmpty(param.getParamValue())) {
			return StatusDto.buildFailure("参数值不能为空");
		}
		this.systemParamService.update(param);
		return StatusDto.buildSuccess("编辑成功");
	}
}
