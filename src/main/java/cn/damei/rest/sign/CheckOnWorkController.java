package cn.damei.rest.sign;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignExportDto;
import cn.damei.enumeration.SignType;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.sign.CheckOnWorkService;
import cn.damei.service.sign.SignService;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.excel.ExcelUtil;
import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/checkonwork")
public class CheckOnWorkController extends BaseController {
    @Autowired
    CheckOnWorkService checkOnWorkService;
    @Autowired
    EmployeeOrgService employeeOrgService;
    @Autowired
    SignService signService;
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    EmployeeService employeeService;
    @RequestMapping(value = "/checkonwork")
    public Object findAllChenkOnWork(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "20") int limit,
                                     @RequestParam(value = "empName") String empName,
                                     @RequestParam(value = "companyName") String companyName,
                                     @RequestParam(value = "startDate") String startDate,
                                     @RequestParam(value = "endDate") String endDate,
                                     @RequestParam(value = "signType") String signType,
                                     @RequestParam(value = "punchCardType") String punchCardType) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "empName", empName);
        MapUtils.putNotNull(params, "companyName", companyName);
        MapUtils.putNotNull(params, "signType", signType);
        MapUtils.putNotNull(params, "punchCardType", punchCardType);
        if (StringUtils.isNoneBlank(startDate)) {
            MapUtils.putNotNull(params, "startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNoneBlank(endDate)) {
            MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
        }
        PageTable pagetable = checkOnWorkService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pagetable);
    }
    @RequestMapping("/findAllCount")
    public Object findAllCount(@RequestParam(value = "empName", required = false) String empName,
                               @RequestParam(value = "companyName", required = false) String companyName,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "signType") String signType,
                               @RequestParam(value = "punchCardType") String punchCardType) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "empName", empName);
        MapUtils.putNotNull(params, "companyName", companyName);
        MapUtils.putNotNull(params, "signType", signType);
        MapUtils.putNotNull(params, "punchCardType", punchCardType);
        if (StringUtils.isNoneBlank(startDate)) {
            MapUtils.putNotNull(params, "startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNoneBlank(endDate)) {
            MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
        }
        List<Sign> signCollectList = checkOnWorkService.findAllCount(params);
        return StatusDto.buildSuccess(signCollectList);
    }
    @RequestMapping("/findAllcount")
    public Object findAllcount(@RequestParam(value = "empName", required = false) String empName,
                               @RequestParam(value = "companyName", required = false) String companyName,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "signType") String signType,
                               @RequestParam(value = "punchCardType") String punchCardType) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "empName", empName);
        MapUtils.putNotNull(params, "companyName", companyName);
        MapUtils.putNotNull(params, "signType", signType);
        MapUtils.putNotNull(params, "punchCardType", punchCardType);
        if (StringUtils.isNoneBlank(startDate)) {
            MapUtils.putNotNull(params, "startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNoneBlank(endDate)) {
            MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
        }
        List<Sign> signList = checkOnWorkService.findAllcount(params);
        Map<String, Integer> mapList = new HashMap<>();
        Integer BELATENUM = 0;
        Integer LEAVEEARLYNUM = 0;
        Integer BELATEANDLEAVEEARLYNUM = 0;
        Integer ABSENTEEISMNUM = 0;
        Integer PunchCardType = 0;
        for (Sign sign : signList) {
            if (sign.getSignType() != null) {
                if ("BELATE".equals(sign.getSignType().name())) {
                    BELATENUM++;
                    mapList.put("BELATENUM", BELATENUM);
                } else if ("LEAVEEARLY".equals(sign.getSignType().name())) {
                    LEAVEEARLYNUM++;
                    mapList.put("LEAVEEARLYNUM", LEAVEEARLYNUM);
                } else if ("BELATEANDLEAVEEARLY".equals(sign.getSignType().name())) {
                    BELATEANDLEAVEEARLYNUM++;
                    mapList.put("BELATEANDLEAVEEARLYNUM", BELATEANDLEAVEEARLYNUM);
                } else if ("ABSENTEEISM".equals(sign.getSignType().name())) {
                    ABSENTEEISMNUM++;
                    mapList.put("ABSENTEEISMNUM", ABSENTEEISMNUM);
                }
            }
            if ((sign.getPunchCardType() != null && sign.getPunchCardType() == 2) ||
                    (sign.getOutPunchCardType() != null && sign.getOutPunchCardType() == 2)) {
                PunchCardType++;
                mapList.put("PunchCardType", PunchCardType);
            }
        }
        return StatusDto.buildSuccess(mapList);
    }
    @RequestMapping(value = "/export")
    public void exportSign(HttpServletResponse resp,
                           @RequestParam(value = "empName", required = false) String empName,
                           @RequestParam(value = "companyName", required = false) String companyName,
                           @RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "signType") String signType,
                           @RequestParam(value = "punchCardType") String punchCardType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfdd = new SimpleDateFormat("dd");
        String firstDay = null;
        String lastDay = null;
        String signLastDay = null;
        Date exportStartDate = null;
        Date exportEndDate = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            firstDay = sdf.format(calendar.getTime());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.DAY_OF_MONTH, 0);
            lastDay = sdf.format(calendar2.getTime());
            Calendar calendar3 = Calendar.getInstance();
            calendar3.add(Calendar.MONTH, 0);
            calendar3.set(Calendar.DAY_OF_MONTH, 1);
            signLastDay = sdf.format(calendar3.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "empName", empName);
        MapUtils.putNotNull(params, "companyName", companyName);
        MapUtils.putNotNull(params, "signType", signType);
        MapUtils.putNotNull(params, "punchCardType", punchCardType);
        if (StringUtils.isNoneBlank(startDate)) {
            MapUtils.putNotNull(params, "startDate", startDate + " 00:00:00");
        } else {
            MapUtils.putNotNull(params, "startDate", firstDay + " 00:00:00");
        }
        if (StringUtils.isNoneBlank(endDate)) {
            MapUtils.putNotNull(params, "endDate", endDate + " 23:59:59");
        } else {
            MapUtils.putNotNull(params, "endDate", lastDay + " 23:59:59");
        }
        List<Sign> list = checkOnWorkService.findCheckOnWork(params);//查询考勤明细
        Set<SignExportDto> signExportDto = getSignExportDto(list, startDate);
        list.forEach(signStr -> {//解决导出后时间只显示年月日不显示时分秒的问题
            signStr.setTmpSignTime(signStr.getSignTime() != null ? DateUtils.format(signStr.getSignTime(), "yyyy-MM-dd HH:mm:ss") : "");
            signStr.setTmpSignOutTime(signStr.getSignOutTime() != null ? DateUtils.format(signStr.getSignOutTime(), "yyyy-MM-dd HH:mm:ss") : "");
        });
        Map<Long, Long> collect = list.stream().filter(a -> a.getSignTime() != null).collect(Collectors.groupingBy(Sign::getEmpId, Collectors.counting()));
        for (Sign sign : list) {
            Long aLong = collect.get(sign.getEmpId());
            if (aLong == null) {
                aLong = 0L;
            }
            sign.setPracticalWorkDays(Double.valueOf(aLong));
        }
        List<Sign> signCollectList = checkOnWorkService.findCollect(params);
        HSSFWorkbook workbook = new HSSFWorkbook();//创建一个工作薄
        HSSFSheet sheet1 = workbook.createSheet("考勤明细表");//创建第一个sheet页
        HSSFSheet sheet2 = workbook.createSheet("考勤汇总表");//创建第二个sheet页
        HSSFCellStyle style = workbook.createCellStyle();//生产一个样式
        HSSFRow row = sheet1.createRow(0);//创建第一行（表头）
        HSSFRow row2 = sheet2.createRow(0);//创建第一行（表头）
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//样式字体居中
        HSSFCell cell = row.createCell((short) 0);
        HSSFCell cell2 = row2.createCell((short) 0);
        long days = 0L;
        long startTime = 0L;
        long endTime = 0L;
        try {
            if (StringUtils.isNotBlank(startDate)) {
                startTime = sdf.parse(startDate).getTime();
            } else {
                startTime = sdf.parse(firstDay).getTime();
            }
            if (StringUtils.isNotBlank(endDate)) {
                endTime = sdf.parse(endDate).getTime();
            } else {
                endTime = sdf.parse(lastDay).getTime();
            }
            days = (endTime - startTime) / (1000 * 3600 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cell.setCellValue("公司");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("部门");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("工号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("上班时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("下班时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("考勤类型");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("上班打卡地址");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("下班打卡地址");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("上班考勤类型");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("下班考勤类型");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);
        cell.setCellValue("打卡设备");
        cell.setCellStyle(style);
        cell2.setCellValue("工号");
        cell2.setCellStyle(style);
        cell2 = row2.createCell((short) 1);
        cell2.setCellValue("姓名");
        cell2.setCellStyle(style);
        cell2 = row2.createCell((short) 2);
        cell2.setCellValue("应出勤天数");
        cell2.setCellStyle(style);
        cell2 = row2.createCell((short) 3);
        cell2.setCellValue("实际出勤天数");
        cell2.setCellStyle(style);
        cell2 = row2.createCell((short) 4);
        cell2.setCellValue("加班天数");
        cell2.setCellStyle(style);
        cell2 = row2.createCell((short) 5);
        for (int i = 6; i < days + 6; i++) {
            cell2.setCellValue(i - 5);
            cell2.setCellStyle(style);
            cell2 = row2.createCell((short) i);
        }
        for (short i = 0; i < list.size(); i++) {
            row = sheet1.createRow(i + 1);
            if (list.get(i).getCompanyName() != null) {
                row.createCell(0).setCellValue(list.get(i).getCompanyName());
            } else {
                row.createCell(0).setCellValue("-");
            }
            if (list.get(i).getDepName() != null) {
                row.createCell(1).setCellValue(list.get(i).getDepName());
            } else {
                row.createCell(1).setCellValue("-");
            }
            if (list.get(i).getOrgCode() != null) {
                row.createCell(2).setCellValue(list.get(i).getOrgCode());
            } else {
                row.createCell(2).setCellValue("-");
            }
            if (list.get(i).getEmpName() != null) {
                row.createCell(3).setCellValue(list.get(i).getEmpName());
            } else {
                row.createCell(3).setCellValue("-");
            }
            if (list.get(i).getTmpSignTime() != null) {
                row.createCell(4).setCellValue(list.get(i).getTmpSignTime());
            } else {
                row.createCell(4).setCellValue("-");
            }
            if (list.get(i).getTmpSignOutTime() != null) {
                row.createCell(5).setCellValue(list.get(i).getTmpSignOutTime());
            } else {
                row.createCell(5).setCellValue("-");
            }
            if (list.get(i).getSignType() != null) {
                if ("NORMAL".equals(list.get(i).getSignType().toString())) {
                    row.createCell(6).setCellValue("正常");
                } else if ("BELATE".equals(list.get(i).getSignType().toString())) {
                    row.createCell(6).setCellValue("迟到");
                } else if ("LEAVEEARLY".equals(list.get(i).getSignType().toString())) {
                    row.createCell(6).setCellValue("早退");
                } else if ("ABSENTEEISM".equals(list.get(i).getSignType().toString())) {
                    row.createCell(6).setCellValue("旷工");
                } else if ("BELATEANDLEAVEEARLY".equals(list.get(i).getSignType().toString())) {
                    row.createCell(6).setCellValue("迟到并早退");
                }
            } else {
                row.createCell(6).setCellValue("-");
            }
            if (list.get(i).getAddress() != null) {
                row.createCell(7).setCellValue(list.get(i).getAddress());
            } else {
                row.createCell(7).setCellValue("-");
            }
            if (list.get(i).getOutAddress() != null) {
                row.createCell(8).setCellValue(list.get(i).getOutAddress());
            } else {
                row.createCell(8).setCellValue("-");
            }
            if (list.get(i).getPunchCardType() != null) {
                if (list.get(i).getPunchCardType() == 1) {
                    row.createCell(9).setCellValue("内勤");
                } else {
                    row.createCell(9).setCellValue("外勤");
                }
            } else {
                row.createCell(9).setCellValue("-");
            }
            if (list.get(i).getOutPunchCardType() != null) {
                if (list.get(i).getOutPunchCardType() == 1) {
                    row.createCell(10).setCellValue("内勤");
                } else {
                    row.createCell(10).setCellValue("外勤");
                }
            } else {
                row.createCell(10).setCellValue("-");
            }
            row.createCell(11).setCellValue("-");
            row.createCell(12).setCellValue("手机");
        }
        fileExcel(signExportDto, startDate, endDate, sheet2);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");
            Date parse = sdf3.parse(startDate);
            String format = sdf3.format(parse);
            ServletOutputStream out = null;
            resp.setContentType("application/x-msdownload");
            resp.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(format+"考勤统计表.xls", "UTF-8") + "\"");
            out = resp.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Long DateStrConvertToDayNum(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = null;
        Long day = 0L;
        try {
            startTime = sdf.parse(startDate);
            day = DateStrConvertToDayNum(startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }
    private Long DateStrConvertToDayNum(Date date) {
        Long day = 0L;
        try {
            Calendar cld = Calendar.getInstance();
            cld.setTime(date);
            int i = cld.get(Calendar.DAY_OF_MONTH);
            day = Long.valueOf(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }
    public Set<SignExportDto> getSignExportDto(List<Sign> signList, String startDate) {
        Long day = DateStrConvertToDayNum(startDate);
        Set<SignExportDto> setSignExportDto = new HashSet<>();
        Set<Long> empIds = new HashSet<>();
        Map<Long, Map<Long, Sign>> map = Maps.newHashMap();
        Map<Long, Long> collect = signList.stream().filter(a -> a.getSignTime() != null).collect(Collectors.groupingBy(Sign::getEmpId, Collectors.counting()));
        for (Sign sign : signList) {
            Long empId = sign.getEmpId();
            Long aLong = collect.get(empId);
            if (aLong == null) {
                aLong = 0L;
            }
            sign.setPracticalWorkDays(Double.valueOf(aLong));
            SignExportDto signExportDto = new SignExportDto();
            signExportDto.setEmpId(sign.getEmpId());
            signExportDto.setName(sign.getEmpName());
            signExportDto.setOrgCode(sign.getOrgCode());
            signExportDto.setShouldWorkDays(sign.getShouldWorkDays());
            signExportDto.setPracticalWorkDays(sign.getPracticalWorkDays());
            signExportDto.setOverTimeWorkDays(0D);
            if (!empIds.contains(empId)) {
                empIds.add(empId);
                setSignExportDto.add(signExportDto);
            }
            if (map.containsKey(empId)) {
                Map<Long, Sign> signMap = map.get(empId);
                if (sign.getSignTime() == null) {
                    continue;
                }
                Date signTime = sign.getSignTime();
                Long signTimeDay = DateStrConvertToDayNum(signTime);
                Long newKey = signTimeDay - day;
                signMap.put(newKey, sign);
                map.put(empId, signMap);
            } else {
                Map<Long, Sign> signMap = new HashMap<>();
                if (sign.getSignTime() == null) {
                    continue;
                }
                Date signTime = sign.getSignTime();
                Long signTimeDay = DateStrConvertToDayNum(signTime);
                Long newKey = signTimeDay - day;
                signMap.put(newKey, sign);
                map.put(empId, signMap);
            }
        }
        for (SignExportDto signExportDto : setSignExportDto) {
            Long empId = signExportDto.getEmpId();
            Map<Long, Sign> longSignMap = map.get(empId);
            signExportDto.setMap(longSignMap);
        }
        return setSignExportDto;
    }
    public void fileExcel(Set<SignExportDto> signExportDtoSet, String startDate, String endDate, Sheet sheet) {
        int rowNum = 1;
        Long startDay = DateStrConvertToDayNum(startDate);
        Long endDay = DateStrConvertToDayNum(endDate);
        List<String> heards = Arrays.asList("工号","姓名","应出勤天数","实际出勤天数","加班天数");
        List<String> everyDayHeards = new ArrayList<>();
        for (SignExportDto signExportDto : signExportDtoSet) {
            Row row = sheet.createRow(rowNum);
            String str = "-";
            String str1 = "-";
            String str2 = "-";
            String str3 = "-";
            String str4 = "-";
            if (signExportDto.getOrgCode() != null) {
                str = signExportDto.getOrgCode();
            }
            row.createCell(0).setCellValue(str);
            if (signExportDto.getName() != null) {
                str1 = signExportDto.getName();
            }
            row.createCell(1).setCellValue(str1);
            if (signExportDto.getShouldWorkDays() != null) {
                str2 = signExportDto.getShouldWorkDays().toString();
            }
            row.createCell(2).setCellValue(str2);
            if (signExportDto.getPracticalWorkDays() != null) {
                str3 = signExportDto.getPracticalWorkDays().toString();
            }
            row.createCell(3).setCellValue(str3);
            if (signExportDto.getOverTimeWorkDays() != null) {
                str4 = signExportDto.getOverTimeWorkDays().toString();
            }
            row.createCell(4).setCellValue(str4);
            Long empId = signExportDto.getEmpId();
            Map<Long, Sign> map = signExportDto.getMap();
            for (Long i = 0L; i < endDay - startDay + 1; i++) {
                if (rowNum == 1) {
                    everyDayHeards.add(i+startDay+"号");
                }
                String signType = signStatus(map, i);
                row.createCell(i.intValue() + 5).setCellValue(signType);
            }
            rowNum++;
        }
        List<String> allHeard = new ArrayList<>();
        allHeard.addAll(heards);
        allHeard.addAll(everyDayHeards);
        Row row = sheet.createRow(0);
        for (int i = 0; i < allHeard.size();i++) {
            row.createCell(i).setCellValue(allHeard.get(i));
        }
    }
    public String signStatus(Map<Long, Sign> map, Long i) {
        String str = "";
        Sign sign = map.get(i);
        if (sign == null || sign.getSignType() == null) {
            str = "旷工";
        } else {
            String signType = sign.getSignType().toString();
            if ("NORMAL".equals(signType)) {
                str = "正常";
            } else if ("BELATE".equals(signType)) {
                str = "迟到";
            } else if ("LEAVEEARLY".equals(signType)) {
                str = "早退";
            } else if ("BELATEANDLEAVEEARLY".equals(signType)) {
                str = "迟到并早退";
            }
        }
        return str;
    }
}
