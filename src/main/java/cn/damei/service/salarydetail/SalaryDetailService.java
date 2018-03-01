package cn.damei.service.salarydetail;
import com.google.common.collect.Maps;
import cn.damei.common.service.CrudService;
import cn.damei.common.view.ViewExcel;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.salarydetail.SalaryBasicData;
import cn.damei.entity.salarydetail.SalaryDetail;
import cn.damei.entity.sign.Sign;
import cn.damei.enumeration.SignType;
import cn.damei.enumeration.UploadCategory;
import cn.damei.repository.organization.MdniOrganizationDao;
import cn.damei.repository.salarydetail.SalaryBasicDataDao;
import cn.damei.repository.salarydetail.SalaryDetailDao;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.sign.SignService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtil;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.excel.export.DataResult;
import cn.damei.utils.excel.export.DataResultExportExcel;
import cn.damei.utils.excel.export.ExportResultDataAllColumns;
import cn.damei.utils.excel.export.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;
@Service
@Lazy(false)
public class SalaryDetailService extends CrudService<SalaryDetailDao, SalaryDetail> {
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
    @Autowired
    private SalaryBasicDataService salaryBasicDataService;
    @Autowired
    private EmployeeOrgService employeeOrgService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SalaryBasicDataDao salaryBasicDataDao;
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(SalaryDetail salaryDetail) {
        try {
            Date date = new Date();
            if (salaryDetail.getId() == null) {
                ShiroUser user = WebUtils.getLoggedUser();
                salaryDetail.setOaEmployeeOrgId(mdniOrganizationDao.getByEmpId(user.getId()));
                salaryDetail.setUpdateUser(user.getId());
                salaryDetail.setUpdateDate(date);
                this.entityDao.insert(salaryDetail);
            } else {
                salaryDetail.setUpdateDate(date);
                this.entityDao.update(salaryDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public ModelAndView export(String salaMonth) {
        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "salaMonth", salaMonth);
        List<SalaryDetail> salaryDetailList = this.salaryDetailDao.getSalaryDetailBySalaMonth(params);
        for (SalaryDetail salaryDetail : salaryDetailList) {
            if (salaryDetail.getSalaryBasicDn() != null && salaryDetail.getSalaryPerformanceDn() != null) {
                BigDecimal salaryBasicTotalDn = new BigDecimal(salaryDetail.getSalaryBasicDn()).add(new BigDecimal(salaryDetail.getSalaryPerformanceDn()));
                salaryDetail.setSalaryBasicTotal(salaryBasicTotalDn.toString());
            } else if (salaryDetail.getSalaryBasicDn() == null && salaryDetail.getSalaryPerformanceDn() != null) {
                salaryDetail.setSalaryBasicTotal(salaryDetail.getSalaryPerformanceDn());
            } else {
                salaryDetail.setSalaryBasicTotal(salaryDetail.getSalaryBasicDn());
            }
            if (salaryDetail.getShouldBasicSalaryDn() != null && salaryDetail.getDeductionWageDn() != null) {
                BigDecimal shouldSalaryTotalDn = new BigDecimal(salaryDetail.getShouldBasicSalaryDn()).add(new BigDecimal(salaryDetail.getDeductionWageDn()));
                salaryDetail.setShouldSalaryTotal(shouldSalaryTotalDn.toString());
            } else if (salaryDetail.getShouldBasicSalaryDn() == null && salaryDetail.getDeductionWageDn() != null) {
                salaryDetail.setShouldSalaryTotal(salaryDetail.getDeductionWageDn());
            } else {
                salaryDetail.setShouldSalaryTotal(salaryDetail.getShouldBasicSalaryDn());
            }
            String shouldSalaryTotal = salaryDetail.getShouldSalaryTotal();
            salaryDetail.setShouldSalaryTotalDn(shouldSalaryTotal);
            String salaryBasicTotal = salaryDetail.getSalaryBasicTotal();
            salaryDetail.setSalaryBasicTotalDn(salaryBasicTotal);
        }
        LinkedHashMap<String, String> title = Maps.newLinkedHashMap();
        title.put("orgCode", "工号");
        title.put("empName", "姓名");
        title.put("orgDepartmentName", "部门");
        title.put("position", "岗位");
        title.put("censusNature", "户口性质");
        title.put("salaryBasicDn", "基本工资");
        title.put("salaryPerformanceDn", "绩效工资");
        title.put("salaryBasicTotalDn", "基本工资合计");
        title.put("deductionWageDn", "提成金额");
        title.put("salaryTotalDn", "工资合计");
        title.put("shouldWorkDays", "应出勤天数");
        title.put("practicalWorkDays", "实际出勤天数");
        title.put("workCoefficient", "出勤系数");
        title.put("sickLeaveSalaryDn", "病假工资");
        title.put("mealSubsidyDn", "餐补");
        title.put("trafficSubsidyDn", "交通补助");
        title.put("otherSubsidyDn", "其他补助");
        title.put("shouldBasicSalaryDn", "应发基本工资");
        title.put("shouldSalaryTotalDn", "应发工资总额");
        title.put("endowmentInsuranceDn", "养老保险");
        title.put("employmentInjuryInsuranceDn", "工伤保险,");
        title.put("medicareDn", "医疗保险");
        title.put("birthInsuranceDn", "生育保险");
        title.put("unemploymentInsuranceDn", "失业保险");
        title.put("housingFundDn", "住房公积金");
        title.put("socialSecurityPersonalDn", "社保个人扣款");
        title.put("deductMoneyDn", "扣款");
        title.put("salaryTaxableDn", "应税工资");
        title.put("individualIncomeTaxDn", "个人所得税");
        title.put("compensationDn", "补偿金");
        title.put("practicalSalaryTotalDn", "实发工资");
        title.put("socialSecurityCompanyDn", "社保公司扣款合计");
        title.put("creditCardNumbers", "银行卡号");
        title.put("bank", "银行");
        title.put("bankOfDeposit", "开户行");
        title.put("idNum", "身份证号");
        ViewExcel<SalaryDetail> viewExcel = new ViewExcel<SalaryDetail>("安徽智装研究院工资表.xls", "salaryDetail", title, salaryDetailList);
        viewExcel.setEmptyFillStr(" ");
        viewExcel.setDateFmt("yyyy-MM-dd HH:mm:ss");
        viewExcel.setWriteRowNumer(true);//设置行号
        ModelAndView modelAndView = new ModelAndView(viewExcel, model);
        return modelAndView;
    }
    public int salaryTax() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDay = null;
        Date firstparse = null;
        Date lastparse = null;
        String firstDay = null;
        Date last = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            firstDay = sdf.format(calendar.getTime());
            firstparse = sdf.parse(firstDay);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.DAY_OF_MONTH, 0);
            lastDay = sdf.format(calendar2.getTime());
            lastparse = sdf.parse(lastDay);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.add(Calendar.MONTH, 0);
            calendar3.set(Calendar.DAY_OF_MONTH, 1);
            last = sdf.parse(sdf.format(calendar3.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        salaryDetailDao.deleteSalary(firstparse);//删除上月工资单(下版本更改：条件增加删除智装的)
        List<EmployeeOrg> employeeOrgList = employeeOrgService.findAllZZEmp(firstparse,last);
        for (EmployeeOrg employeeOrg : employeeOrgList) {
            double signCount = signService.findWorkCoefficient(employeeOrg.getEmployee().getId(), firstparse, last);
            List<SalaryBasicData> salaryDetailList = salaryBasicDataService.findAllByUpMonth(firstparse, employeeOrg.getEmployee().getId());
            for (SalaryBasicData salaryBasicData : salaryDetailList) {
                BigDecimal salaryBasic = new BigDecimal(0);
                BigDecimal mealSubsidy = new BigDecimal(0);
                BigDecimal otherSubsidy = new BigDecimal(0);
                BigDecimal socialSecurityPersonal = new BigDecimal(0);
                BigDecimal housingFund = new BigDecimal(0);
                BigDecimal deductMoney = new BigDecimal(0);
                BigDecimal compensation = new BigDecimal(0);
                BigDecimal workCoefficient = new BigDecimal(0);
                BigDecimal zero = new BigDecimal(0);
                double work = 0.0;
                if (StringUtils.isNotBlank(salaryBasicData.getSalaryBasicDn())) {
                    salaryBasic = new BigDecimal(salaryBasicData.getSalaryBasicDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getMealSubsidyDn())) {
                    mealSubsidy = new BigDecimal(salaryBasicData.getMealSubsidyDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getOtherSubsidyDn())) {
                    otherSubsidy = new BigDecimal(salaryBasicData.getOtherSubsidyDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getSocialSecurityPersonalDn())) {
                    socialSecurityPersonal = new BigDecimal(salaryBasicData.getSocialSecurityPersonalDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getHousingFundDn())) {
                    housingFund = new BigDecimal(salaryBasicData.getHousingFundDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getDeductMoneyDn())) {
                    deductMoney = new BigDecimal(salaryBasicData.getDeductMoneyDn());
                }
                if (StringUtils.isNotBlank(salaryBasicData.getCompensationDn())) {
                    compensation = new BigDecimal(salaryBasicData.getCompensationDn());
                }
                if (null != salaryBasicData.getShouldWorkDays() && signCount != 0) {
                    work = signCount / salaryBasicData.getShouldWorkDays();
                    if (work >= 1) {
                        work = 1;
                    }
                    workCoefficient = new BigDecimal(work);
                } else {
                    workCoefficient = new BigDecimal(0);
                }
                BigDecimal shouldBasicSalary = salaryBasic.multiply(workCoefficient).
                        add(mealSubsidy.multiply(workCoefficient)).
                        add(otherSubsidy);
                shouldBasicSalary =  shouldBasicSalary.setScale(2,BigDecimal.ROUND_HALF_UP);
                if (shouldBasicSalary.compareTo(zero) == -1) {
                    shouldBasicSalary = new BigDecimal(0);
                }
                BigDecimal salaryTaxable = shouldBasicSalary.subtract(socialSecurityPersonal).
                        subtract(housingFund).
                        subtract(deductMoney);
                salaryTaxable = salaryTaxable.setScale(2, BigDecimal.ROUND_HALF_UP);
                if (salaryTaxable.compareTo(zero) == -1) {
                    salaryTaxable = new BigDecimal(0);
                }
                double i = salaryAfterTax(salaryTaxable.doubleValue());//获得实发
                double value = salaryTaxable.doubleValue();
                double individualIncomeTaxNew = value - i;//个税
                if (individualIncomeTaxNew <= 0) {
                    individualIncomeTaxNew = 0;
                }
                double practicalSalaryTotalnew = i + compensation.doubleValue();//实发
                mealSubsidy = workCoefficient.multiply(mealSubsidy);
                SalaryDetail salaryDetail = new SalaryDetail();
                salaryDetail.setBankOfDeposit(salaryBasicData.getBankOfDeposit());
                salaryDetail.setCreditCardNumbers(salaryBasicData.getCreditCardNumbers());
                salaryDetail.setShouldWorkDays(salaryBasicData.getShouldWorkDays());
                salaryDetail.setPracticalWorkDays(signCount);
                salaryDetail.setSalaryBasic(salaryBasic.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setMealSubsidy(mealSubsidy.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setOtherSubsidy(otherSubsidy.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setCompensation(compensation.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setSocialSecurityPersonal(socialSecurityPersonal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setHousingFund(housingFund.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setDeductMoney(deductMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setWorkCoefficient(workCoefficient.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                salaryDetail.setShouldBasicSalary((shouldBasicSalary.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                salaryDetail.setSalaryTaxable((salaryTaxable.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                salaryDetail.setIndividualIncomeTax((new BigDecimal(individualIncomeTaxNew).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                salaryDetail.setPracticalSalaryTotal(new BigDecimal(practicalSalaryTotalnew).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                salaryDetail.setEmpId(salaryBasicData.getEmpId());
                salaryDetail.setMonthSalary(firstparse);
                salaryDetail.setOaEmployeeOrgId(salaryBasicData.getOaEmployeeOrgId());
                salaryDetail.setBank(salaryBasicData.getBank());
                this.entityDao.insert(salaryDetail);
                this.entityDao.updatePracticalWorkDays(employeeOrg.getEmployee().getId(), signCount,firstparse);
            }
        }
        return 1;
    }
    public void insertss(){
        signService.deleteTen();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = null;
        String lastDay = null;
        Date firstparse = null;
        Date lastparse = null;
        String last = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            firstDay = sdf.format(calendar.getTime());
            firstparse = sdf.parse(firstDay);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.DAY_OF_MONTH, 0);
            lastDay = sdf.format(calendar2.getTime());
            lastparse = sdf.parse(lastDay);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.add(Calendar.MONTH, 0);
            calendar3.set(Calendar.DAY_OF_MONTH, 1);
            last = sdf.format(calendar3.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date lastMonth = DateUtils.getAssignMonthAndDay(date, -1, 1);
        Date thisMonth = DateUtils.getAssignMonthAndDay(date, 0, 1);
//查询智装下的所有人
        List<EmployeeOrg> employeeOrgList = employeeOrgService.findAllZZEmp(lastMonth,thisMonth);
        List<EmployeeOrg> productDepList = employeeOrgService.findProductDep();
        String signStartDate = "09:00";
        String signEndDate = "10:00";
        String signOutStartDate = "19:00";
        String signOutEndDate = "21:00";
        List<Date> between = getBetweenDates(firstparse, lastparse);//获取上个月的所有日期
        for (EmployeeOrg employeeOrg : employeeOrgList) {
            Date entryDate = null;
            Date dimissionDate = null;
            Employee employee = employeeService.getById(employeeOrg.getEmployee().getId());
            if (employee.getEntryDate() != null) {
                entryDate = employee.getEntryDate();//入职日期
            }
            long entryTime = 0;
            long firstTime = 0;
            long dimissionTime = 0;
            long lastTime = 0;
            dimissionDate = employee.getDimissionDate();//离职日期
            if (dimissionDate != null) {
                try {
                    dimissionTime = sdf.parse(sdf.format(dimissionDate)).getTime();//获取到离职日期的参数
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                entryTime = sdf.parse(sdf.format(entryDate)).getTime();//获得到入职日期的毫秒值
                firstTime = sdf.parse(firstDay).getTime();//获得到上个月的一号的毫秒值
                lastTime = sdf.parse(lastDay).getTime();//获取到上月最后一天的参数
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (entryTime <= firstTime && dimissionDate == null) {
                Date startSignDate = firstparse;//考勤开始日期
                Date endSignDate = lastparse;//考勤结束日期
                this.betweenDate(startSignDate,endSignDate,signStartDate,signEndDate,signOutStartDate,signOutEndDate,employeeOrg);
            } else if (entryTime >= firstTime && dimissionDate == null) {//入职日期大于等于上月1号，则考勤开始取入职日期，并且离职日期为空的，则考勤结束日期就取上月最后一天
                Date startSignDate = entryDate;//考勤开始日期
                Date endSignDate = lastparse;//考勤结束日期
                this.betweenDate(startSignDate,endSignDate,signStartDate,signEndDate,signOutStartDate,signOutEndDate,employeeOrg);
            } else if (entryTime <= firstTime && dimissionTime <= lastTime) {
                Date startSignDate = firstparse;//考勤开始日期
                Date endSignDate = dimissionDate;//考勤结束日期
                this.betweenDate(startSignDate,endSignDate,signStartDate,signEndDate,signOutStartDate,signOutEndDate,employeeOrg);
            } else if (entryTime >= firstTime && dimissionTime <= lastTime) {
                Date startSignDate = entryDate;//考勤开始日期
                Date endSignDate = dimissionDate;//考勤结束日期
                this.betweenDate(startSignDate,endSignDate,signStartDate,signEndDate,signOutStartDate,signOutEndDate,employeeOrg);
            }
        }
        for (Date date2 : between) {
            Date sunDay = null;
            List<EmployeeOrg> satDepList = new ArrayList<>();
            List<EmployeeOrg> sunDepList = new ArrayList<>();
            for (EmployeeOrg employeeOrg : productDepList) {
                int i = (int) (Math.random() * 2);
                if (i == 0) {
                    satDepList.add(employeeOrg);
                } else {
                    sunDepList.add(employeeOrg);
                }
            }
            Date signDate = randomSignDate(date2, signStartDate, signEndDate);
            Date signOutDate = randomSignDate(date2, signOutStartDate, signOutEndDate);
            int week = DateUtil.getDayOfWeek(date2);
            if (week == 7) {//是周六
                for (EmployeeOrg employeeOrg : satDepList) {
                    Sign sign = insertSign(employeeOrg, signDate, signOutDate);
                    signService.insert(sign);
                }
                sunDay = DateUtils.getDateBeforOrAfterDate(date2, 1);
                Date signDates = randomSignDate(sunDay, signStartDate, signEndDate);
                Date signOutDates = randomSignDate(sunDay, signOutStartDate, signOutEndDate);
                for (EmployeeOrg employeeOrg : sunDepList) {
                    Sign sign = insertSign(employeeOrg, signDates, signOutDates);
                    signService.insert(sign);
                }
            }
        }
    }
    private  void  betweenDate(Date startSignDate,Date endSignDate,String signStartDate,String signEndDate,String signOutStartDate,String signOutEndDate,EmployeeOrg employeeOrg){
        List<Date> betweenDates = getBetweenDates(startSignDate, endSignDate);//获取上个月的所有日期
        for (Date betweenDate : betweenDates) {//遍历上个月的每一天
            Date signDate = randomSignDate(betweenDate, signStartDate, signEndDate);
            Date signOutDate = randomSignDate(betweenDate, signOutStartDate, signOutEndDate);
            int week = DateUtil.getDayOfWeek(betweenDate);
            if (week == 2 || week == 3 || week == 4 || week == 5 || week == 6) {
                Sign sign = insertSign(employeeOrg, signDate, signOutDate);
                signService.insert(sign);
            }
        }
    }
    private static Sign insertSign(EmployeeOrg employeeOrg, Date signDate, Date signOutDate) {
        String firstDay = null;
        Date firstparse = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDay = sdf.format(calendar.getTime());
        try{
            firstparse = sdf.parse(firstDay);
        }catch (Exception e){
            e.printStackTrace();
        }
        Sign sign = new Sign();
        sign.setEmpId(employeeOrg.getEmployee().getId());
        sign.setSignTime(signDate);
        sign.setSignOutTime(signOutDate);
        sign.setCompany(employeeOrg.getCompany().getId());
        sign.setDepartment(employeeOrg.getDepartment().getId());
        sign.setAddress("北京分店");
        sign.setOutAddress("北京分店");
        sign.setPunchCardType(1L);
        sign.setOutPunchCardType(1L);
        sign.setSignType(SignType.NORMAL);
        sign.setCreatTime(firstparse);
        return sign;
    }
    private static List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDay = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        firstDay = sdf.format(calendar.getTime());
        try{
            Date firstparse = sdf.parse(firstDay);
            System.out.println(firstparse);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static double salaryAfterTax(double salaryBeforeTax) {
//      （3W-3.5K）*25%-1005
//      扣税公式是：
//      （扣除社保医保公积金后薪水-个税起征点）*税率-速算扣除数
        double taxbase = salaryBeforeTax - 3500;
        int Taxrate = 0;//这里税率没有除以百分比；
        int Quickdeduction = 0;
        if (taxbase <= 0)//低于个税起征点
        {
            return salaryBeforeTax;
        } else if (taxbase <= 1500) {
            Taxrate = 3;
            Quickdeduction = 0;
        } else if (taxbase <= 4500) {
            Taxrate = 10;
            Quickdeduction = 105;
        } else if (taxbase <= 9000) {
            Taxrate = 20;
            Quickdeduction = 555;
        } else if (taxbase <= 35000) {
            Taxrate = 25;
            Quickdeduction = 1005;
        } else if (taxbase <= 55000) {
            Taxrate = 30;
            Quickdeduction = 2755;
        } else if (taxbase <= 80000) {
            Taxrate = 35;
            Quickdeduction = 5505;
        } else {
            Taxrate = 45;
            Quickdeduction = 13505;
        }
        return salaryBeforeTax - ((salaryBeforeTax - 3500) * Taxrate / 100 - Quickdeduction);
    }
    public List<SalaryDetail> getSalaryDetail(Map<String, Object> map) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String upMonth = null;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        upMonth = sdf.format(calendar1.getTime());//获取  到上个月(查找工资生效时间段)
        Date dateUpMonth = null;
        try{
            dateUpMonth = sdf.parse(upMonth);
        }catch(Exception e){
            e.printStackTrace();
        }
        String id = (String) map.get("id");
        String empid = (String) map.get("empId");
        Long empId = null;
        if(empid!=null){
            empId = Long.parseLong(empid);
        }
        List<SalaryDetail> salaryDetailList = null;
        String mealSubsidyDn = null;
        if(id!=null && empId!=null){
            List<SalaryBasicData> salaryBasicDataList = this.salaryBasicDataDao.findAllByUpMonth(dateUpMonth,empId);
            if(salaryBasicDataList!=null&&salaryBasicDataList.size()>0){
                for(SalaryBasicData salaryBasicData:salaryBasicDataList){
                    mealSubsidyDn = salaryBasicData.getMealSubsidyDn();
                }
                map.put("empId",null);
                salaryDetailList = this.entityDao.getSalaryDetail(map);
                if(salaryDetailList!=null&&salaryDetailList.size()>0){
                    for(SalaryDetail salaryDetail:salaryDetailList){
                        salaryDetail.setMealSubsidy(mealSubsidyDn);
                        String mealSubsidy = salaryDetail.getMealSubsidy();
                        salaryDetail.setMealSubsidyDn(mealSubsidy);
                    }
                }
            }else{
                map.put("empId",null);
                salaryDetailList = this.entityDao.getSalaryDetail(map);
                if(salaryDetailList!=null&&salaryDetailList.size()>0){
                    for(SalaryDetail salaryDetail:salaryDetailList){
                        salaryDetail.setMealSubsidy("0");
                        String mealSubsidy = salaryDetail.getMealSubsidy();
                        salaryDetail.setMealSubsidyDn(mealSubsidy);
                    }
                }
            }
            return salaryDetailList;
        }else{
            salaryDetailList = this.entityDao.getSalaryDetail(map);
            return salaryDetailList;
        }
    }
    public List<SalaryDetail> getSalaryDetail(String salaMonth) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "salaMonth", null);
        return this.entityDao.search(params);
    }
    public List<SalaryDetail> findAllSalaryDetail() {
        return this.entityDao.findAllSalaryDetail();
    }
    private static String getFormat(long mis, String fff) {
        SimpleDateFormat sdf = new SimpleDateFormat(fff);
        Date date = new Date();
        date.setTime(mis);
        String day = sdf.format(date);
        return day;
    }
    public int updateSalary(SalaryDetail salaryDetail) {
        BigDecimal salaryBasic = new BigDecimal(0);
        BigDecimal mealSubsidy = new BigDecimal(0);
        BigDecimal otherSubsidy = new BigDecimal(0);
        BigDecimal socialSecurityPersonal = new BigDecimal(0);
        BigDecimal housingFund = new BigDecimal(0);
        BigDecimal deductMoney = new BigDecimal(0);
        BigDecimal compensation = new BigDecimal(0);
        BigDecimal workCoefficient = new BigDecimal(0);
        BigDecimal zero = new BigDecimal(0);
        double work = 0.0;
        if (StringUtils.isNotBlank(salaryDetail.getSalaryBasic())) {
            salaryDetail.setSalaryBasicDn(salaryDetail.getSalaryBasic());
            salaryBasic = new BigDecimal(salaryDetail.getSalaryBasicDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getMealSubsidy())) {
            salaryDetail.setMealSubsidyDn(salaryDetail.getMealSubsidy());
            mealSubsidy = new BigDecimal(salaryDetail.getMealSubsidyDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getOtherSubsidy())) {
            salaryDetail.setOtherSubsidyDn(salaryDetail.getOtherSubsidy());
            otherSubsidy = new BigDecimal(salaryDetail.getOtherSubsidyDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getSocialSecurityPersonal())) {
            salaryDetail.setSocialSecurityPersonalDn(salaryDetail.getSocialSecurityPersonal());
            socialSecurityPersonal = new BigDecimal(salaryDetail.getSocialSecurityPersonalDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getHousingFund())) {
            salaryDetail.setHousingFundDn(salaryDetail.getHousingFund());
            housingFund = new BigDecimal(salaryDetail.getHousingFundDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getDeductMoney())) {
            salaryDetail.setDeductMoneyDn(salaryDetail.getDeductMoney());
            deductMoney = new BigDecimal(salaryDetail.getDeductMoneyDn());
        }
        if (StringUtils.isNotBlank(salaryDetail.getCompensation())) {
            salaryDetail.setCompensationDn(salaryDetail.getCompensation());
            compensation = new BigDecimal(salaryDetail.getCompensationDn());
        }
        if (null != salaryDetail.getShouldWorkDays() && null != salaryDetail.getPracticalWorkDays()) {
            work = salaryDetail.getPracticalWorkDays() / salaryDetail.getShouldWorkDays();
            if (work >= 1) {
                work = 1;
            }
            workCoefficient = new BigDecimal(work);
        } else {
            workCoefficient = new BigDecimal(0);
        }
        BigDecimal shouldBasicSalary = salaryBasic.multiply(workCoefficient).
                add(mealSubsidy.multiply(workCoefficient)).
        add(otherSubsidy);
        shouldBasicSalary =  shouldBasicSalary.setScale(2,BigDecimal.ROUND_HALF_UP);
        if (shouldBasicSalary.compareTo(zero) == -1) {
            shouldBasicSalary = new BigDecimal(0);
        }
        BigDecimal salaryTaxable = shouldBasicSalary.subtract(socialSecurityPersonal).
                subtract(housingFund).
                subtract(deductMoney);
        salaryTaxable = salaryTaxable.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (salaryTaxable.compareTo(zero) == -1) {
            salaryTaxable = new BigDecimal(0);
        }
        double i = salaryAfterTax(salaryTaxable.doubleValue());//获得实发
        double value = salaryTaxable.doubleValue();
        double individualIncomeTaxNew = value - i;//个税
        if (individualIncomeTaxNew <= 0) {
            individualIncomeTaxNew = 0;
        }
        double practicalSalaryTotalnew = i + compensation.doubleValue();//实发
        mealSubsidy = workCoefficient.multiply(mealSubsidy);
        salaryDetail.setBankOfDeposit(salaryDetail.getBankOfDeposit());
        salaryDetail.setCreditCardNumbers(salaryDetail.getCreditCardNumbers());
        salaryDetail.setShouldWorkDays(salaryDetail.getShouldWorkDays());
        salaryDetail.setPracticalWorkDays(salaryDetail.getPracticalWorkDays());
        salaryDetail.setSalaryBasic(salaryBasic.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setMealSubsidy(mealSubsidy.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setOtherSubsidy(otherSubsidy.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setCompensation(compensation.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setSocialSecurityPersonal(socialSecurityPersonal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setHousingFund(housingFund.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setDeductMoney(deductMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryDetail.setWorkCoefficient(workCoefficient.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        salaryDetail.setShouldBasicSalary((shouldBasicSalary.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
        salaryDetail.setSalaryTaxable((salaryTaxable.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
        salaryDetail.setIndividualIncomeTax((new BigDecimal(individualIncomeTaxNew).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        salaryDetail.setPracticalSalaryTotal(new BigDecimal(practicalSalaryTotalnew).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        int update = this.entityDao.update(salaryDetail);
        return update;
    }
    public static Date randomSignDate(Date betweenDate, String signStartDate, String signEndDate) {
        Date signDate = null;
        try {
            Date randomDate = randomDate(signStartDate, signEndDate);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String parse = format.format(betweenDate);
            String ranDate = format2.format(randomDate);
            signDate = format3.parse(parse + " " + ranDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signDate;
    }
    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date start = format.parse(beginDate);// 开始日期
            Date end = format.parse(endDate);// 结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }
    private static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }
    public Object findAllByUpMonth(Long empId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String upMonth = null;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        upMonth = sdf.format(calendar1.getTime());//获取  到上个月(查找工资生效时间段)
        Date dateUpMonth = null;
        try {
            dateUpMonth = sdf.parse(upMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.salaryBasicDataDao.findAllByUpMonth(dateUpMonth, empId);
    }
}
