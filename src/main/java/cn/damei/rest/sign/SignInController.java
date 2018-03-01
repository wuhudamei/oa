package cn.damei.rest.sign;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignRecord;
import cn.damei.enumeration.SignType;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.sign.CheckOnWorkService;
import cn.damei.service.sign.SignRecordService;
import cn.damei.service.sign.SignService;
import cn.damei.service.sign.SignSiteService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/sign")
public class SignInController extends BaseController {
    @Autowired
    SignService signService;
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    SignRecordService signRecordService;
    @RequestMapping(value = "/goSign")
    public Object goSign() {
        SignRecord signRecord = getSign();
        if (signRecord == null) {//未签到
            return StatusDto.buildSuccess();
        } else {
            String formatDate = DateUtils.format(signRecord.getSignDate(), "yyyy-MM-dd");
            Integer signRecordCount = signRecordService.getBySignOutTime(signRecord.getEmployeeId(),formatDate);
            if (signRecordCount == 0) {//说明已签到但未签退
                return StatusDto.buildSuccess("签退");
            }
            return StatusDto.buildSuccess("今天已打卡");//已签到和签退
        }
    }
//    @RequestMapping(value = "/saveSign")
//    public Object saveSign(@RequestBody Sign sign) {
//        ShiroUser user = WebUtils.getLoggedUser();
//        Date date = new Date();//签到时间
//        Sign signData = getSign();
//        if (signData == null) {//未签到，进行签到
//            sign.setSignTime(date);
//            sign.setEmpId(user.getId());
//            sign.setCompany(user.getCompanyId());
//            sign.setDepartment(user.getDepartmentId());
//            sign.setCreator(user.getName());
//            sign.setCreatTime(date);
//            signService.insert(sign);
//
//        } else {//已经签到，更新签到时间
//            sign.setSignTime(date);
//            sign.setId(signData.getId());
//                signService.update(sign);
//        }
//        return StatusDto.buildSuccess();
//    }
    @RequestMapping(value = "/saveSign")
    public Object saveSign(@RequestBody Sign sign) {
        ShiroUser user = WebUtils.getLoggedUser();
        Date date = new Date();//签到时间
        SignRecord signRecord = new SignRecord();
        signRecord.setEmployeeId(user.getId());
        signRecord.setSignDate(date);
        signRecord.setLongitude(Float.parseFloat(sign.getLongitude()));
        signRecord.setLatitude(Float.parseFloat(sign.getLatitude()));
        signRecord.setAddress(sign.getAddress());
        signRecord.setPunchCardType(sign.getPunchCardType().toString());
        signRecord.setSignType(sign.getWorkType());
        signRecordService.insert(signRecord);
        return StatusDto.buildSuccess();
    }
//    @RequestMapping(value = "/signOut")
//    public Object signOut(@RequestBody Sign sign) {
//        Sign signData = getSign();
//        Date date = new Date();
//        ShiroUser user = WebUtils.getLoggedUser();
//        if (signData != null) {//已签到但未签退
//            sign.setSignOutTime(date);
//            sign.setId(signData.getId());
//            sign.setEmpId(signData.getEmpId());
//            sign.setCreator(user.getName());
//            sign.setCreatTime(date);
//                signService.update(sign);
//        } else {//未签到
//            sign.setCreator(user.getName());
//            sign.setCreatTime(date);
//            sign.setSignOutTime(date);
//            sign.setEmpId(user.getId());
//            sign.setCompany(user.getCompanyId());
//            sign.setDepartment(user.getDepartmentId());
//                signService.insert(sign);
//        }
//        return StatusDto.buildSuccess();
//
//    }
    @RequestMapping(value = "/signOut")
    public Object signOut(@RequestBody Sign sign) {
        Date date = new Date();
        ShiroUser user = WebUtils.getLoggedUser();
        SignRecord signRecord = new SignRecord();
        signRecord.setEmployeeId(user.getId());
        signRecord.setSignDate(date);
        signRecord.setLongitude(Float.parseFloat(sign.getOutLongitude()));
        signRecord.setLatitude(Float.parseFloat(sign.getOutLatitude()));
        signRecord.setAddress(sign.getOutAddress());
        signRecord.setPunchCardType(sign.getOutPunchCardType().toString());
        signRecord.setSignType(sign.getOutWorkType());
        signRecordService.insert(signRecord);
        return StatusDto.buildSuccess();
    }
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        ShiroUser user = WebUtils.getLoggedUser();
        Date date = new Date();
        String formatDate = DateUtils.format(date, "yyyy-MM-dd");
        MapUtils.putNotNull(params, "empId", user.getId());
        MapUtils.putNotNull(params, "date", formatDate);
        PageTable pagetable = signService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pagetable);
    }
    @RequestMapping("/findTime")
    public Object findTime() {
        Sign sign = mdniOrganizationService.findTime();
        return StatusDto.buildSuccess(sign);
    }
    @RequestMapping("/oldType")
    public Object findOldType(){
        Date date = new Date();
        String formatDate = DateUtils.format(date, "yyyy-MM-dd");
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        Sign sign = signService.findOldType(loggedUser.getId(),formatDate);
        return StatusDto.buildSuccess(sign);
    }
    private SignRecord getSign() {
        Date date = new Date();
        String formatDate = DateUtils.format(date, "yyyy-MM-dd");
        ShiroUser user = WebUtils.getLoggedUser();
        Map<String, Object> map = new HashedMap();
        map.put("employeeId", user.getId());
        map.put("date", formatDate);
        SignRecord signRecord = signRecordService.getByEmpIdAndDate(map);
        return signRecord;
    }
}
