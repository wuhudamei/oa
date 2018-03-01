package cn.damei.service.sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sign.Sign;
import cn.damei.enumeration.SignType;
import cn.damei.repository.sign.SignDao;
import cn.damei.repository.sign.SignZZDao;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SignService extends CrudService<SignDao,Sign> {
    @Autowired
    private SignZZDao signZZDao;
    public Sign getByEmpIdAndDate(Map<String,Object> map) {
        return entityDao.getByEmpIdAndDate(map);
    }
    public Integer getBySignOutTime(Date signOutTime) {
        return entityDao.getBySignOutTime(signOutTime);
    }
    public void updateSignDateById(Long empId, Date date) {
        this.entityDao.updateSignDateById(empId,date);
    }
    public void updateSignOutDateById(Long empId, Date date) {
        this.entityDao.updateSignOutDateById(empId,date);
    }
    public void updateAddress(Sign sign) {
        entityDao.updateAddress(sign);
    }
    public Date findByEmpId(Long departmentId,Date date) {
        return entityDao.findByEmpId(departmentId,date);
    }
    public List<Sign> getToDayData() {
       return entityDao.getToDayData();
    }
    public void updateSignTypeById(SignType belate, Long id) {
        entityDao.updateSignTypeById(belate,id);
    }
    public List<Sign> findBySignType() {
       return entityDao.findBySignType();
    }
    public List<Long> getNoSignEmployeeId() {
       return entityDao.getNoSignEmployeeId();
    }
    public void updateSignType(SignType normal, Long id) {
        entityDao.updateSignType(normal,id);
    }
    public Long findAttendance() {
       return entityDao.findAttendance();
    }
    public Long findAllBeLate() {
        return entityDao.findAllBeLate();
    }
    public List<Sign> findSignTimeNotNULL() {
        return entityDao.findSignTimeNotNULL();
    }
    public List<Sign> findAllToDay() {
        return entityDao.findAllToDay();
    }
    public Long getNoSignEmployeeCount() {
        return entityDao.getNoSignEmployeeCount();
    }
    public Long findOuter() {
        return entityDao.findOuter();
    }
    public Long findAllBelate() {
        return entityDao.findAllBeLate();
    }
    public Long findAllLeaveearly() {
        return entityDao.findAllLeaveearly();
    }
    public void updateSignTypeByEmpId(SignType absenteeism, Long id, Date date) {
        entityDao.updateSignTypeByEmpId(absenteeism,id,date);
    }
    public Long findbelateandleaveearly() {
        return entityDao.findbelateandleaveearly();
    }
    public Sign getSignOutTime(Map<String, Object> map) {
        return entityDao.getSignOutTime(map);
    }
    public Sign findSignTime(Long empId) {
        return entityDao.findSignTime(empId);
    }
    public void updateType(Date signTime, Date signOutTime, Long id) {
        entityDao.updateToBELATE(signTime,signOutTime,id);
        entityDao.updateToLEAVEEARLY(signTime,signOutTime,id);
        entityDao.updateToBELATEANDLEAVEEARLY(signTime,signOutTime,id);
        entityDao.updateToBELATEANDLEAVEEARLYDB(signTime,signOutTime,id);
        entityDao.updateToNORMAL(signTime,signOutTime,id);
    }
    public void updateComType(Date signTime, Date signOutTime, Long id) {
        entityDao.updateComToBELATE(signTime,signOutTime,id);
        entityDao.updateComToLEAVEEARLY(signTime,signOutTime,id);
        entityDao.updateComToBELATEANDLEAVEEARLY(signTime,signOutTime,id);
        entityDao.updateComToBELATEANDLEAVEEARLYDB(signTime,signOutTime,id);
        entityDao.updateComToNORMAL(signTime,signOutTime,id);
    }
    public Sign findOldType(Long empId,String formatDate) {
        return entityDao.findOldType(empId,formatDate);
    }
    public void updateSignDate(Long empId, Date signDate) {
        entityDao.updateSignDate(empId,signDate);
    }
    public void updateSignOutDate(Long empId, Date signOutDate) {
        entityDao.updateSignOutDate(empId,signOutDate);
    }
    public int findWorkCoefficient(Long empId, Date firstDay, Date lastDay) {
       return this.entityDao.findWorkCoefficient(empId,firstDay,lastDay);
    }
    public void insertToSignZZ(Sign sign) {
        signZZDao.insert(sign);
    }
    public int findSignByYesterdayAndEmpId(Long id) {
        return this.entityDao.findSignByYesterdayAndEmpId(id);
    }
    public Sign findSign(Long id, Date yesterday,Date today) {
        return this.entityDao.findSign(id,yesterday,today);
    }
    public void deleteTen() {
        this.entityDao.deleteTen();
    }
    public void updateTime(Sign sign) {
        this.entityDao.updateTime(sign);
    }
    public List<Sign> getByEmpIdAndYesdate( Map<String,Object> map ) {
        return this.entityDao.getByEmpIdAndYesdate(map);
    }
    public void insertSignList(List<Sign> signList) {
        this.entityDao.insertSignList(signList);
    }
    public List<Sign> findDepSignRecord() {
        return this.entityDao.findDepSignRecord();
    }
    public List<Sign> findComSignRecord() {
        return this.entityDao.findComSignRecord();
    }
    public void deleteByCreateTime(String date) {
        this.entityDao.deleteByCreateTime(date);
    }
}
