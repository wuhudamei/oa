package cn.damei.repository.wechat.menu;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.menu.Menu;
@Repository
public interface MenuDao extends CrudDao<Menu> {
}