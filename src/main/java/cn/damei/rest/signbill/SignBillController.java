package cn.damei.rest.signbill;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.mdniorder.MdniOrder;
import cn.damei.entity.singleSign.SignAndPublish;
import cn.damei.entity.singleSign.SignBill;
import cn.damei.service.mdniorder.MdniOrderService;
import cn.damei.service.signbill.PublishBillService;
import cn.damei.service.signbill.SignBillService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.excel.ExcelUtil;
@RestController
@RequestMapping("/api/singleSign")
public class SignBillController {
	@Autowired
	private SignBillService signBillService;
	@Autowired
	private PublishBillService publishBillService;
	@Autowired
	private MdniOrderService mdniOrderService;
	@RequestMapping(method = RequestMethod.GET)
	public Object companyList(@RequestParam(required = false) String company,
			@RequestParam(required = false) String beginDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String statue, @RequestParam(required = false) String orgCode,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "20") int limit) {
		if ("1".equals(orgCode)) {
			orgCode = "";
		}
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "company", company);
		if (StringUtils.isNotBlank(beginDate)) {
			MapUtils.putNotNull(params, "beginDate", beginDate + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endDate)) {
			MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
		}
		MapUtils.putNotNull(params, "statue", statue);
		MapUtils.putNotNull(params, "department", orgCode);
		MapUtils.putNotNull(params, "search", search);
		PageTable<?> page = this.publishBillService.searchScrollPage(params, new Pagination(offset, limit));
		return StatusDto.buildSuccess(page);
	}
	@RequestMapping(value = "/departmentList", method = RequestMethod.GET)
	public Object departmentList(
			@RequestParam(required = false) String beginDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String statue, @RequestParam(required = false) String orgCode,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "20") int limit) {
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		Map<String, Object> params = Maps.newHashMap();
		if (StringUtils.isNotBlank(beginDate)) {
			MapUtils.putNotNull(params, "beginDate", beginDate + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endDate)) {
			MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
		}
		MapUtils.putNotNull(params, "statue", statue);
		MapUtils.putNotNull(params, "department", loggedUser.getDepartmentId());
		MapUtils.putNotNull(params, "search", search);
		PageTable<?> page = this.publishBillService.searchScrollPage(params, new Pagination(offset, limit));
		return StatusDto.buildSuccess(page);
	}
	@RequestMapping(value = "/mdnOrderList", method = RequestMethod.GET)
	public Object mdnOrderList(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit,
			HttpServletRequest request) {
		Map<String, String> params = Maps.newHashMapWithExpectedSize(1);
		if (StringUtils.isNotBlank(keyword)) {
			params.put("mobile", keyword);
			List<MdniOrder> mdnOrderList = mdniOrderService.getOrderInfoByCondition(params);
			return StatusBootTableDto.buildDataSuccessStatusDto(mdnOrderList, Long.parseLong(mdnOrderList.size() + ""));
		}
		return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insertBill(@RequestBody SignBill signBill) {
		signBillService.insertBill(signBill);
		return StatusDto.buildSuccess("新增签报单成功!");
	}
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(HttpServletResponse resp, @RequestParam(required = false) Long companyId,
			@RequestParam(required = false) String beginDate, @RequestParam(required = false) String search,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String statue,
			@RequestParam(required = false) String department) {
		ShiroUser user = WebUtils.getLoggedUser();
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "search", search);
		MapUtils.putNotNull(params, "statue", statue);
		MapUtils.putNotNull(params, "department", department);
		if (StringUtils.isNotBlank(beginDate)) {
			MapUtils.putNotNull(params, "beginDate", beginDate + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endDate)) {
			MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
		}
		if (companyId != null && companyId > 0) {
			MapUtils.putNotNull(params, "company", companyId);
		} else {
			MapUtils.putNotNull(params, "department", user.getDepartmentId());
		}
		ServletOutputStream out = null;
		Workbook workbook = null;
		try {
			resp.setContentType("application/x-msdownload");
			resp.addHeader("Content-Disposition",
					"attachment; filename=\"" + java.net.URLEncoder.encode("签单表.xlsx", "UTF-8") + "\"");
			out = resp.getOutputStream();
			List<SignAndPublish> list = publishBillService.findExportBill(params);
			if (CollectionUtils.isNotEmpty(list)) {
				list.forEach(signAndPublish -> {
					signAndPublish.setStatusName(signAndPublish.getStatus() == 0 ? "未执行" : "已执行");
				});
				workbook = ExcelUtil.getInstance().exportObj2ExcelWithTitleAndFields(list, SignAndPublish.class, true,
						titles, fields);
			}
			if (workbook != null) {
				workbook.write(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	@RequestMapping(value = "updateStatus/{id}", method = RequestMethod.GET)
	public Object updateStatus(@PathVariable String id) {
		publishBillService.updateStatus(id);
		return StatusDto.buildSuccess("修改成功");
	}
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public Object deleteBill(@PathVariable Long id, @RequestParam("signCode") String signCode) {
		System.out.println(signCode);
		publishBillService.deleteBill(id, signCode);
		return StatusDto.buildSuccess("删除成功");
	}
	// 初识化导出的字段
	private static List<String> titles = null;
	private static List<String> fields = null;
	static {
		titles = Lists.newArrayListWithExpectedSize(3);
		titles.add("分公司");
		titles.add("签报编码");
		titles.add("处罚编码");
		titles.add("执行状态");
		titles.add("订单号");
		titles.add("客户姓名");
		titles.add("客户电话");
		titles.add("订单地址");
		titles.add("责任部门");
		titles.add("责任人");
		titles.add("处罚原因");
		titles.add("过程描述");
		titles.add("总经理意见");
		titles.add("签报时间");
		fields = Lists.newArrayListWithExpectedSize(3);
		fields.add("subCompany");
		fields.add("signCode");
		fields.add("punishCode");
		fields.add("statusName");
		fields.add("orderNum");
		fields.add("customerName");
		fields.add("customerPhone");
		fields.add("orderAddress");
		fields.add("punishDepartment");
		fields.add("punishMan");
		fields.add("punishReason");
		fields.add("procedureDescribe");
		fields.add("managerView");
		fields.add("createTime");
	}
}
