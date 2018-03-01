package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.Stylist;
import cn.damei.repository.stylist.StylistDao;
import cn.damei.shiro.ShiroUser;
import java.util.List;
import java.util.Optional;
@Service
public class StylistService extends CrudService<StylistDao, Stylist> {
    public void batchInsert(List<Stylist> stylists, ShiroUser logged) {
        if (CollectionUtils.isNotEmpty(stylists)) {
            for (Stylist stylist : stylists) {
                stylist.setDepartment(Optional.ofNullable(logged).map(ShiroUser::getDepartmentId).orElse(null));
                stylist.setType(Stylist.Type.STYLIST);
            }
            this.entityDao.batchInsert(stylists);
        }
    }
    public Stylist getByUseId(Long userId) {
        if (userId == null) {
            return null;
        }
        return this.entityDao.getByUserId(userId);
    }
}