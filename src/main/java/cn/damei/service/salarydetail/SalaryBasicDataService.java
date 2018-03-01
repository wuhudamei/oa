package cn.damei.service.salarydetail;
import com.google.common.collect.Maps;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.salarydetail.SalaryBasicData;
import cn.damei.entity.salarydetail.SalaryDetail;
import cn.damei.enumeration.UploadCategory;
import cn.damei.repository.organization.MdniOrganizationDao;
import cn.damei.repository.salarydetail.SalaryBasicDataDao;
import cn.damei.repository.salarydetail.SalaryDetailDao;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.sign.SignService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
import cn.damei.utils.excel.export.DataResult;
import cn.damei.utils.excel.export.DataResultExportExcel;
import cn.damei.utils.excel.export.ExportResultDataAllColumns;
import cn.damei.utils.excel.export.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class SalaryBasicDataService extends CrudService<SalaryBasicDataDao, SalaryBasicData> {
    @Autowired
    private MdniOrganizationDao mdniOrganizationDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SalaryDetailDao salaryDetailDao;
    @Autowired
    private MdniOrganizationService mdniOrganizationService;
    @Autowired
    private SignService signService;
    private SalaryBasicDataService salaryBasicDataService;
    @Transactional(rollbackFor = Exception.class)
    public Object insertOrUpdate(SalaryBasicData salaryBasicData) {
        try {
            Date date = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.MONTH, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String upMonth = null;
            upMonth = sdf.format(calendar1.getTime());//获取到上个月
            if (salaryBasicData.getId() == null) {
                List<SalaryBasicData> salaryBasicDataList = this.entityDao.findListByEmpId(salaryBasicData.getEmpId());
                if(salaryBasicDataList == null || salaryBasicDataList.size() == 0){
                    ShiroUser user = WebUtils.getLoggedUser();
                    salaryBasicData.setOaEmployeeOrgId(mdniOrganizationDao.getByEmpId(salaryBasicData.getEmpId()));
                    salaryBasicData.setUpdateUser(user.getId());
                    salaryBasicData.setUpdateDate(date);
                    salaryBasicData.setMonthSalary(sdf.parse(upMonth));
                    this.entityDao.insert(salaryBasicData);
                } else {
                    int j = 0;
//                    for(int i = 0; i<= salaryBasicDataList.size(); i++){
//                    }
                    Date effectiveDate = salaryBasicData.getEffectiveDate();
                    Date expiryDate = salaryBasicData.getExpiryDate();
                    Map map = Maps.newHashMap();
                    map.put("effectiveDate",effectiveDate);
                    map.put("expiryDate",expiryDate);
                    map.put("empId",salaryBasicData.getEmpId());
                    j = this.entityDao.findAllDate(map);
                    if(j == 0){
                        ShiroUser user = WebUtils.getLoggedUser();
                        salaryBasicData.setOaEmployeeOrgId(mdniOrganizationDao.getByEmpId(salaryBasicData.getEmpId()));
                        salaryBasicData.setUpdateUser(user.getId());
                        salaryBasicData.setUpdateDate(date);
                        salaryBasicData.setMonthSalary(sdf.parse(upMonth));
                        this.entityDao.insert(salaryBasicData);
                    }else {
                        return StatusDto.buildFailure("在此日期期间内有配置，不允许添加");
                    }
                }
            } else {
                salaryBasicData.setUpdateDate(date);
                this.entityDao.updateById(salaryBasicData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StatusDto.buildSuccess("操作成功");
    }
    private static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
    public void export(Map<String, Object> map) {
        String sql =
                "SELECT sala.id     AS id,\n" +
                        "   emp.org_code    AS orgCode,\n" +
                        "   emp.`name`     AS empName,\n" +
                        "   organ.org_name      AS orgName,\n" +
                        "   emp.position      AS position,\n" +
                        "   emp.census_nature       AS censusNature,\n" +
                        "   sala.salary_basic       AS salaryBasic,\n" +
                        "   sala.salary_performance     AS salaryPerformance,\n" +
                        "   sala.salary_basic_total     AS salaryBasicTotal,\n" +
                        "   sala.deduction_wage     AS deductionWage,\n" +
                        "   sala.salary_total       AS salaryTotal,\n" +
                        "   sala.should_work_days       AS shouldWorkDays,\n" +
                        "   sala.practical_work_days        AS practicalWorkDays,\n" +
                        "   sala.work_coefficient       AS workCoefficient,\n" +
                        "   sala.sick_leave_salary      AS sickLeaveSalary,\n" +
                        "   sala.meal_subsidy       AS mealSubsidy,\n" +
                        "   sala.traffic_subsidy        AS trafficSubsidy,\n" +
                        "   sala.should_basic_salary        AS shouldBasicSalary,\n" +
                        "   sala.should_salary_total        AS shouldSalaryTotal,\n" +
                        "   sala.endowment_insurance        AS endowmentInsurance,\n" +
                        "   sala.employment_injury_insurance        AS employmentInjuryInsurance,\n" +
                        "   sala.medicare       AS medicare,\n" +
                        "   sala.birth_insurance        AS birthInsurance,\n" +
                        "   sala.unemployment_insurance         AS unemploymentInsurance,\n" +
                        "   sala.housing_fund       AS housingFund,\n" +
                        "   sala.social_security_personal       AS socialSecurityPersonal,\n" +
                        "   sala.deduct_money       AS deductMoney,\n" +
                        "   sala.salary_taxable         AS salaryTaxable,\n" +
                        "   sala.individual_income_tax          AS individualIncomeTax,\n" +
                        "   sala.compensation       AS compensation,\n" +
                        "   sala.practical_salary_total         AS practicalSalaryTotal,\n" +
                        "   sala.social_security_company        AS socialSecurityCompany,\n" +
                        "   sala.credit_card_numbers        AS creditCardNumbers,\n" +
                        "   sala.bank       AS bank,\n" +
                        "   sala.bank_of_deposit        AS bankOfDeposit,\n" +
                        "   emp.id_num      AS idNum\n" +
                        "  FROM oa_salary_detail sala\n" +
                        "  LEFT JOIN oa_employee_org org\n" +
                        "    ON sala.oa_employee_org_id = org.id\n" +
                        "  LEFT JOIN oa_employee emp\n" +
                        "    ON org.emp_id = emp.id\n" +
                        "  LEFT JOIN oa_organization organ\n" +
                        "    ON organ.id = org.org_id\n" ;
        String keyword = (String) map.get("keyword");
        String orgCode = (String) map.get("orgCode");
        String salaMonth = (String) map.get("salaMonth");
        if (salaMonth != null) {
            sql += "AND sala.month_salary='" + salaMonth + "'";
        }
        Object[] params = new Object[]{};
        String[] title = {"序号", "工号", "姓名", "部门", "岗位", "户口性质", "基本工资", "绩效工资", "基本工资合计", "提成金额", "工资合计",
                "应出勤天数", "实际出勤天数", "出勤系数", "病假工资", "餐补", "交通补助", "应发基本工资", "应发工资总额", "养老保险", "工伤保险",
                "医疗保险", "生育保险", "失业保险", "住房公积金", "社保个人扣款", "扣款", "应税工资", "个人所得税", "补偿金", "实发工资", "社保公司扣款合计", "银行卡号", "银行", "开户行", "身份证号"};
        String[] columns = {"id", "orgCode", "empName", "orgName", "position", "censusNature", "salaryBasic", "salaryPerformance",
                "salaryBasicTotal", "deductionWage", "salaryTotal", "shouldWorkDays", "practicalWorkDays", "workCoefficient",
                "sickLeaveSalary", "mealSubsidy", "trafficSubsidy", "shouldBasicSalary", "shouldSalaryTotal", "endowmentInsurance",
                "employmentInjuryInsurance", "medicare", "birthInsurance", "unemploymentInsurance", "housingFund", "socialSecurityPersonal",
                "deductMoney", "salaryTaxable", "individualIncomeTax", "compensation", "practicalSalaryTotal", "socialSecurityCompany", "creditCardNumbers", "bank", "bankOfDeposit", "idNum"};
        String fileName = String.format("common-%s.xls", new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
        String saveFilePath = uploadService.saveFilePath(UploadCategory.EXCLE, fileName);
        File outFile = new File(saveFilePath);
        DataResult dataResult = new DataResultExportExcel(100, title, columns, outFile,
                new ExportResultDataAllColumns());
        QueryResult.doQuery(dataSource, sql, params, dataResult);
    }
    public List<SalaryDetail> getSalaryDetail(Map<String, Object> map) {
        return this.entityDao.getSalaryDetail(map);
    }
    public List<SalaryBasicData> findAllSalaryBasicData(Long empId) {
        return this.entityDao.findAllSalaryBasicData(empId);
    }
    public List<Long> findAllZZEmp() {
        return entityDao.findAllZZEmp();
    }
    public void updatePracticalWorkDays(Long empId, double signCount) {
//        this.entityDao.updatePracticalWorkDays(empId,signCount);
    }
    public List<SalaryBasicData> findAllByUpMonth(Date upMonth, Long empId) {
        return this.entityDao.findAllByUpMonth(upMonth,empId);
    }
    private static String getFormat(long mis,String fff){
        SimpleDateFormat sdf = new SimpleDateFormat(fff);
        Date date = new Date();
        date.setTime(mis);
        String day = sdf.format(date);
        return day;
    }
    public Double findShouldWorkDays(Long empId) {
        return this.entityDao.findShouldWorkDays(empId);
    }
}
