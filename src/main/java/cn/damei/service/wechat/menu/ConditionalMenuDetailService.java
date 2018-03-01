package cn.damei.service.wechat.menu;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.dto.wechat.menu.MenuTreeDto;
import cn.damei.entity.organization.State;
import cn.damei.entity.wechat.menu.ConditionalMenuDetail;
import cn.damei.repository.wechat.menu.ConditionalMenuDetailDao;
import java.util.List;
import java.util.Objects;
@Service
public class ConditionalMenuDetailService extends CrudService<ConditionalMenuDetailDao, ConditionalMenuDetail> {
    public List<ConditionalMenuDetail> findByCid(Long cid) {
        if (cid != null)
            return this.entityDao.findByCid(cid);
        return null;
    }
    public MenuTreeDto create(ConditionalMenuDetail menu) {
        if (Objects.nonNull(menu)) {
            int i = this.insert(menu);
            if (i > 0) {
                return new MenuTreeDto()
                        .setId(menu.getId())
                        .setText(menu.getName())
                        .setState(new State());
            }
        }
        return null;
    }
    public MenuTreeDto edit(ConditionalMenuDetail menu) {
        if (Objects.nonNull(menu)) {
            int i = this.update(menu);
            if (i > 0) {
                return new MenuTreeDto()
                        .setId(menu.getId())
                        .setText(menu.getName())
                        .setState(new State());
            }
        }
        return null;
    }
}