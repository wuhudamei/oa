package cn.damei.service.wechat.tag;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.entity.wechat.tag.Tag;
import cn.damei.entity.wechat.tag.TagEmployee;
import cn.damei.repository.wechat.tag.TagEmployeeDao;
import cn.damei.service.wechat.WechatUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service
public class TagEmployeeService extends CrudService<TagEmployeeDao, TagEmployee> {
    @Autowired
    private WechatUserService wechatUserService;
    public List<TagEmployee> findAllByTagId(Long tagId) {
        if (tagId != null) {
            return this.entityDao.findAllByTagId(tagId);
        }
        return null;
    }
    @Override
    public int update(TagEmployee tagEmployee){
    	return this.entityDao.update(tagEmployee);
    }
    public List<TagEmployee> buildPojoFromEmpIds(List<Long> empIds, Tag tag) {
        List<TagEmployee> res = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(empIds) && tag != null) {
            List<WechatUser> users = this.wechatUserService.getByUserIds(empIds);
            for (WechatUser user : users) {
                res.add(new TagEmployee(tag, new Employee(user.getUserId()), user.getOpenid()));
            }
        }
        return res;
    }
    public int deleteByOpenids(List<String> openids) {
        if (CollectionUtils.isNotEmpty(openids)) {
            return this.entityDao.deleteByOpenids(openids);
        }
        return 0;
    }
    public void batchInsert(List<TagEmployee> tagEmployees) {
        if (CollectionUtils.isNotEmpty(tagEmployees)) {
            this.entityDao.batchInsert(tagEmployees);
        }
    }
    public PageTable findByTagIdScrollPage(Map<String, Object> params, Pagination pagination) {
        params.put("sort", pagination.getSort());
        PageHelper.offsetPage(pagination.getOffset(), pagination.getLimit());
        Page<TagEmployee> result = (Page<TagEmployee>) this.entityDao.findByTagIdScrollPage(params);
        return new PageTable<>(result.getResult(), result.getTotal());
    }
}
