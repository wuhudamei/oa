package cn.damei.repository.wechat.menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.menu.ConditionalMenu;
@Repository
public interface ConditionalMenuDao extends CrudDao<ConditionalMenu> {
    ConditionalMenu getDetailById(@Param("id") Long id);
}