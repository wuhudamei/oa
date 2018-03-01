package cn.damei.service.dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.repository.dict.MdniDictionaryDao;
import cn.damei.service.account.RoleService;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
@Service
public class MdniDictionaryService extends CrudService<MdniDictionaryDao, MdniDictionary> {
    @Autowired
    private RoleService roleService;
    public Object addOrUpdate(MdniDictionary mdniDictionary) {
        if (mdniDictionary.getId() == null) {
            if (mdniDictionary.getParentCode() == null) {
                mdniDictionary.setParentCode(0);
            }
            this.entityDao.insert(mdniDictionary);
            return StatusDto.buildSuccess("添加成功");
        }
        this.entityDao.update(mdniDictionary);
        return StatusDto.buildSuccess("修改成功");
    }
    public List<MdniDictionary> getNode(Long type) {
        return this.entityDao.getNode(type);
    }
    public List<MdniDictionary> getByType(Integer parentCode, Integer type) {
        return this.entityDao.getByType(parentCode, type);
    }
    public List<MdniDictionary> findChildNodeByParent(Long parentCode) {
        return this.entityDao.findChildNodeByParent(parentCode);
    }
    public List<MdniDictionary> getByParendCode(Long parentCode, Integer type) {
        if (parentCode == null) {
            return Collections.emptyList();
        }
        return this.entityDao.getByType(parentCode.intValue(), type);
    }
    public List<MdniDictionary> findTree() {
        return this.entityDao.findTree();
    }
    public List<MdniDictionary> filterDictionary(List<MdniDictionary> list) {
        if (list == null || list.size() == 0) return null;
        List<Long> subs = roleService.findSubjectIdsByRoleId(-1L);
        List<MdniDictionary> returnList = new ArrayList<MdniDictionary>();
        for (MdniDictionary m : list) {
            if (subs.contains(m.getId())) returnList.add(m);
        }
        return returnList;
    }
}