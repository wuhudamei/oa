package cn.damei.repository.organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.organization.MdniOrganization;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface MdniOrganizationDao extends CrudDao<MdniOrganization> {
    MdniOrganization getByCode(String orgCode);
    void disableOrg(@Param("id") Long id);
    List<MdniOrganization> findDepartment(@Param("familyCode") String familyCode);
    List<MdniOrganization> findByType(@Param("type") String type);
    MdniOrganization getOrgByLastCode(@Param("code") String code);
    Integer selectByparentId(@Param("parentId") Long parentId);
    String findFamilyCodeById(Long id);
    void setsignTime(@Param("familyCode") String familyCode, @Param("newSignTime") Date signTime, @Param("newSignOutTime") Date signOutTime);
    Date findSignTimeByDepartmentId(@Param("departmentId") Long departmentId);
    Date findSignOutTimeByDepartmentId(@Param("departmentId") Long departmentId);
    List<MdniOrganization> selectOrgByFamilyCode(@Param("familyCode") String familyCode);
    List<MdniOrganization> getByIds(@Param("ids") List<Long> ids);
    Date getSignTimeByDepId(@Param("department") Long department);
    Date getSignOutTimeByDepId(@Param("department") Long department);
    List<MdniOrganization> getSignTime(Long id);
    List<MdniOrganization> findAllSignTime();
    void updateRemindMessageType(@Param("type") int type,@Param("id") Long id);
    void updateOutRemindMessageType(@Param("type") int type, @Param("id") Long id);
    List<MdniOrganization> findIdAndSIgnTime();
    List<MdniOrganization> findComIdAndSignTime();
    void updateRemMessage();
    Long getByEmpId(@Param("empId") Long empId);
    List<MdniOrganization> findStores();
    List<Map<String,String>> getDesignGroup(@Param("storeCode") String storeCode);
    List<Map<String,String>> getEmployeeByOrgCode(@Param("orgCode") String orgCode);
}