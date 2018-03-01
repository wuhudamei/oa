package cn.damei.service.sign;
import com.google.common.collect.Maps;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignRecord;
import cn.damei.enumeration.SignType;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.salarydetail.SalaryDetailService;
import cn.damei.service.sign.SignService;
import cn.damei.utils.DateUtil;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import org.apache.avro.generic.GenericData;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@EnableScheduling
@Lazy(false)
public class SetSignTypeQuartzBean {
    @Autowired
    SignService signService;
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    EmployeeOrgService employeeOrgService;
    @Autowired
    SignRecordService signRecordService;
    private static final Logger logger = LogManager.getLogger(SetSignTypeQuartzBean.class);
    @Scheduled(cron = "0 5 0 * * ?")
    public void setSignType() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();//获得日历
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String format = sdf.format(date);
        String[] split = format.split(" ");
        signService.deleteByCreateTime(split[0]);
        List<Sign> depSigns = signService.findDepSignRecord();
        List<Sign> depSignList = getSign(sdf, split, depSigns,date);
        signService.insertSignList(depSignList);
        List<Sign> comSigns = signService.findComSignRecord();
        List<Sign> comSignList = getSign(sdf, split, comSigns,date);
        signService.insertSignList(comSignList);
        Date yesDate = new Date();
        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(yesDate);
        calendar1.add(Calendar.DATE,-1);
        yesDate = calendar1.getTime();
        String signStartDate = "09:00";
        String signEndDate = "10:00";
        String signOutStartDate = "19:00";
        String signOutEndDate = "21:00";
        Date yseterday = DateUtils.getDateBeforOrAfterDateStart(new Date(), -1);
        Date today = DateUtils.getDateBeforOrAfterDateEnd(new Date(), -1);
        List<Long> empCPIds = new ArrayList<>();//产品部所有人的id
        List<EmployeeOrg> employeeCPList = employeeOrgService.findEmpZZCP();
        List<Long> empCPId = new ArrayList<>();
        List<EmployeeOrg> employeeList = employeeOrgService.findEmpZZ();
        List<Long> empZZId = new ArrayList<>();
        for (EmployeeOrg employeeOrg : employeeCPList) {
            empCPId.add(employeeOrg.getEmployee().getId());//把产品部的所有人的id放入集合
        }
        for (EmployeeOrg employeeOrg : employeeList) {
            empZZId.add(employeeOrg.getEmployee().getId());//把智装的所有人的id放入集合
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("list",empZZId);
        map.put("yseterday",yseterday);
        map.put("today",today);
//        List<Sign> signCPList = signService.getByEmpIdAndYesdate(empCPId,yesDate);
        List<Sign> signZZList = signService.getByEmpIdAndYesdate(map);
        List<Sign> signCPList = signZZList.stream().filter(a -> (a.getDepartment() != null && a.getDepartment() == 208)).collect(Collectors.toList());
        int yesTodayOfWeek = getDayOfWeek(yesDate);
        if (yesTodayOfWeek != 1 && yesTodayOfWeek != 7) {
            for (Sign sign : signZZList) {
                if (!sign.getSignType().equals(SignType.NORMAL)) {
                    sign = getUpdateSign(sign, yesDate, signStartDate, signEndDate, signOutStartDate, signOutEndDate);
                    signService.update(sign);
                }
            }
        }else if (yesTodayOfWeek == 7) {
            Collections.shuffle(employeeCPList);
            double i1 = (double) employeeCPList.size() / 2;
            int floor = (int)Math.floor(i1);
            List<EmployeeOrg> employeeOrgList = employeeCPList.subList(0, floor);
            List<Long> satDepListIds = employeeOrgList.stream().filter(a -> a.getEmployee().getId() != null).map(b -> b.getEmployee().getId()).collect(Collectors.toList());
            logger.info("周六"+StringUtils.join(satDepListIds,","));
            Map<String,Object> map2 = Maps.newHashMap();
            map2.put("list",satDepListIds);
            map2.put("yseterday",yseterday);
            map2.put("today",today);
            List<Sign> signList =  signService.getByEmpIdAndYesdate(map2);
            for (Sign sign : signList) {
                if (!sign.getSignType().equals(SignType.NORMAL)) {
                    sign = getUpdateSign(sign, yesDate, signStartDate, signEndDate, signOutStartDate, signOutEndDate);
                    signService.update(sign);
                }
            }
        }else {
            List<Long> satDepListIds = employeeCPList.stream().filter(a -> a.getEmployee().getId() != null).map(b -> b.getEmployee().getId()).collect(Collectors.toList());
            Date ytwoDaysBeforeStart = DateUtils.getDateBeforOrAfterDateStart(new Date(), -2);
            Date ytwoDaysBeforeEnd = DateUtils.getDateBeforOrAfterDateEnd(new Date(), -2);
            List<Long> sunNoSignTime = new ArrayList<>();
            Map<String,Object> map3 = Maps.newHashMap();
            map3.put("list",satDepListIds);
            map3.put("yseterday",ytwoDaysBeforeStart);
            map3.put("today",ytwoDaysBeforeEnd);
            List<Sign> signList =  signService.getByEmpIdAndYesdate(map3);
            for (Sign sign : signList) {
                if (!(sign.getSignType().equals(SignType.NORMAL))) {
                    sunNoSignTime.add(sign.getEmpId());
                }
            }
            logger.info("周日"+StringUtils.join(sunNoSignTime,","));
            Map<String,Object> map4 = Maps.newHashMap();
            map4.put("list",sunNoSignTime);
            map4.put("yseterday",yseterday);
            map4.put("today",today);
            List<Sign> sunSignList =  signService.getByEmpIdAndYesdate(map3);
            for (Sign sign : sunSignList) {
                if (!sign.getSignType().equals(SignType.NORMAL)) {
                    sign = getUpdateSign(sign, yesDate, signStartDate, signEndDate, signOutStartDate, signOutEndDate);
                    signService.update(sign);
                }
            }
        }
        mdniOrganizationService.updateRemMessage();//消息发送结束后把属性设置为0;
    }
    private List<Sign> getSign(SimpleDateFormat sdf, String[] split, List<Sign> depSigns,Date date) {
        List<Sign> signList = new ArrayList<>();
        for (Sign sign : depSigns) {
            Sign newSign = new Sign();
            setSign(sign, newSign,date);
            execSignType(sdf, split, sign, newSign);
            signList.add(newSign);
        }
        return signList;
    }
    private void setSign(Sign sign, Sign newSign,Date date) {
        if (sign.getEmpId() != null) {
            newSign.setEmpId(sign.getEmpId());
        }
        newSign.setEmpId(sign.getEmpId());
        if (sign.getSignTime() != null) {
            newSign.setSignTime(sign.getSignTime());
            newSign.setInsertSigntypeTime(sign.getSignTime());
        }
        if (sign.getSignOutTime() != null) {
            newSign.setSignOutTime(sign.getSignOutTime());
            newSign.setInsertSigntypeTime(sign.getSignOutTime());
        }
        if (StringUtils.isNotBlank(sign.getLongitude())) {
            newSign.setLongitude(sign.getLongitude());
        }
        if (StringUtils.isNotBlank(sign.getLatitude())) {
            newSign.setLatitude(sign.getLatitude());
        }
        if (StringUtils.isNotBlank(sign.getAddress())) {
            newSign.setAddress(sign.getAddress());
        }
        if (sign.getCompany() != null) {
            newSign.setCompany(sign.getCompany());
        }
        if (sign.getDepartment() != null) {
            newSign.setDepartment(sign.getDepartment());
        }
        if (StringUtils.isNotBlank(sign.getOutLongitude())) {
            newSign.setOutLongitude(sign.getOutLongitude());
        }
        if (StringUtils.isNotBlank(sign.getOutLatitude())) {
            newSign.setOutLatitude(sign.getOutLatitude());
        }
        if (StringUtils.isNotBlank(sign.getOutAddress())) {
            newSign.setOutAddress(sign.getOutAddress());
        }
        if (sign.getPunchCardType() != null) {
            newSign.setPunchCardType(sign.getPunchCardType());
        }
        if (sign.getOutPunchCardType() != null) {
            newSign.setOutPunchCardType(sign.getOutPunchCardType());
        }
        if (StringUtils.isNotBlank(sign.getCreator())) {
            newSign.setCreator(sign.getCreator());
        }
        newSign.setCreatTime(date);
    }
    private void execSignType(SimpleDateFormat sdf, String[] split, Sign sign, Sign newSign) {
        if (sign.getDepSignTime() != null && sign.getDepSignOutTime() != null) {
            String depsigntimeformat = sdf.format(sign.getDepSignTime());
            String depsignouttimeformat = sdf.format(sign.getDepSignOutTime());
            String[] splitdeptime = depsigntimeformat.split(" ");
            String[] splitdepouttime = depsignouttimeformat.split(" ");
            String depsigntime = split[0] + " " + splitdeptime[1];
            String depsignouttime = split[0] + " " + splitdepouttime[1];
            try {
                Date deptime = sdf.parse(depsigntime);//部门的上班时间
                Date depouttime = sdf.parse(depsignouttime);//部门的下班时间
                if (sign.getSignTime() != null && sign.getSignOutTime() != null) {
                    Date signTime = sign.getSignTime();//上班的打卡时间
                    Date signOutTime = sign.getSignOutTime();//下班的打卡时间
                    if (signTime.compareTo(deptime) == 1 &&  signOutTime.compareTo(depouttime) == -1) {//签到时间大于部门上班时间且签退小于部门下班时间
                        newSign.setSignType(SignType.BELATEANDLEAVEEARLY);
                    }else if (signTime.compareTo(deptime) == 1 && (signOutTime.compareTo(depouttime) == 1 || signOutTime.compareTo(depouttime) == 0)) {//签到时间大于部门上班时间且签退大于等于部门下班时间
                        newSign.setSignType(SignType.BELATE);
                    }else if ((signTime.compareTo(deptime) == -1 || signTime.compareTo(deptime) == 0) && signOutTime.compareTo(depouttime) == -1) {//签到时间小于等于部门上班时间且签退小于部门下班时间
                        newSign.setSignType(SignType.LEAVEEARLY);
                    }else if ((signTime.compareTo(deptime) == -1 || signTime.compareTo(deptime) == 0) && (signOutTime.compareTo(depouttime) == 1 || signOutTime.compareTo(depouttime) == 0)) {//签到时间小于等于部门上班时间且签退大于等于部门下班时间
                        newSign.setSignType(SignType.NORMAL);
                    }
                }else if (sign.getSignTime() != null && sign.getSignOutTime() == null) {//签到时间不为空且签退时间为空
                    Date signTime = sign.getSignTime();//签到时间
                    if (signTime.compareTo(deptime) == -1 || signTime.compareTo(deptime) == 0) {//签到时间小于等于部门上班时间
                        newSign.setSignType(SignType.LEAVEEARLY);
                    }else {
                        newSign.setSignType(SignType.BELATEANDLEAVEEARLY);
                    }
                }else if (sign.getSignTime() == null && sign.getSignOutTime() != null) {//签到时间为空且签退时间不为空
                    Date signOutTime = sign.getSignOutTime();//下班的打卡时间
                    if (signOutTime.compareTo(depouttime) == -1) {//签退时间小于部门下班时间
                        newSign.setSignType(SignType.BELATEANDLEAVEEARLY);
                    }else{
                        newSign.setSignType(SignType.BELATE);
                    }
                }else{
                    newSign.setSignType(SignType.ABSENTEEISM);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static Sign getUpdateSign(Sign sign,Date yesDate,String signStartDate,String signEndDate,
                                      String signOutStartDate,String signOutEndDate){
        Date signDate = randomSignDate(yesDate, signStartDate, signEndDate);//生成签到时间（随机）
        Date signOutDate = randomSignDate(yesDate, signOutStartDate, signOutEndDate);//生成签退时间（随机）
        sign.setSignTime(signDate);
        sign.setSignOutTime(signOutDate);
        sign.setSignType(SignType.NORMAL);
        sign.setAddress("北京分店");
        sign.setOutAddress("北京分店");
        sign.setPunchCardType(1L);
        sign.setOutPunchCardType(1L);
        sign.setCreatTime(signOutDate);
        sign.setInsertSigntypeTime(signOutDate);
        return sign;
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
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
}
