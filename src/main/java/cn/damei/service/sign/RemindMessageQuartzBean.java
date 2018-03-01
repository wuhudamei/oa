package cn.damei.service.sign;
import com.google.common.collect.Lists;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.sign.DepSignMessageLog;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.sign.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
@EnableScheduling
@Lazy(false)
public class RemindMessageQuartzBean {
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MessageManagerService messageManagerService;
    @Scheduled(cron = "0 0/5 * * * ?")
    public void remindMessage() {
        String singHead = "您好，您有一条上班打卡提醒！";
        String singOutHead = "您好，您有一条下班打卡提醒！";
        String signStatus = "上班打卡";
        String signOutStatus = "下班打卡";
        String signRemark = null;
        String signOutRemark = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateFormat = sdf.format(date);//格式化系统当前时间
        List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findAllSignTime();
        for (MdniOrganization mdniOrganization : mdniOrganizationList) {
            Date depSignTime = mdniOrganization.getSignTime();//设置的签到时间
            if (depSignTime != null) {
                String depFormat = sdf.format(depSignTime);//格式化部门设置的时间
                signRemark = "上班时间为：" + depFormat + "，请别忘记打卡！";
                try {
                    long depTime = sdf.parse(depFormat).getTime();
                    long dateTime = sdf.parse(dateFormat).getTime();
                    if ((depTime - dateTime >= 600000 && depTime - dateTime <= 1200000) && (0 == mdniOrganization.getRemindMessageType())) {//设置的时间与系统当前时间相等，发送消息提醒
                        if (mdniOrganization.getId() != 1) {
                            List<Employee> employeeList = employeeService.findEmployee(mdniOrganization.getId());
                            for (Employee employee : employeeList) {
                                Properties properties = PropertiesLoaderUtils.loadAllProperties("filtration.properties");
                                boolean b = properties.containsValue(employee.getJobNum());
                                if (!b) {
                                    messageManagerService.sendAttendanceRecordsTemplate(null, employee.getJobNum(), singHead, employee.getName(), sdf2.format(new Date()), signStatus, signRemark);
                                }
                            }
                        }
                        mdniOrganizationService.updateRemindMessageType(1, mdniOrganization.getId());//1为已发送消息提醒
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Date depSignOutTime = mdniOrganization.getSignOutTime();//设置的签退时间
            if (depSignOutTime != null) {
                String depOutFormat = sdf.format(depSignOutTime);//格式化部门设置的时间
                signOutRemark = "下班时间为：" + depOutFormat + "，请别忘记打卡！";
                try {
                    long depOutTime = sdf.parse(depOutFormat).getTime();
                    long dateOutTime = sdf.parse(dateFormat).getTime();
                    if ((depOutTime - dateOutTime >= 0 && depOutTime - dateOutTime <= 300000) && (0 == mdniOrganization.getOutRemindMessageType())) {//设置的时间与系统当前时间相等，发送消息提醒
                        if (mdniOrganization.getId() != 1) {
                            List<Employee> employeeList = employeeService.findOutEmployee(mdniOrganization.getId());
                            for (Employee employee : employeeList) {
                                Properties properties = PropertiesLoaderUtils.loadAllProperties("filtration.properties");
                                boolean b = properties.containsValue(employee.getJobNum());
                                if (!b) {
                                    messageManagerService.sendAttendanceRecordsTemplate(null, employee.getJobNum(), singOutHead, employee.getName(), sdf2.format(new Date()), signOutStatus, signOutRemark);
                                }
                            }
                        }
                        mdniOrganizationService.updateOutRemindMessageType(1, mdniOrganization.getId());//1为已发送消息提醒
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
