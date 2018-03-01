package cn.damei.repository.wechat.menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.menu.ConditionalMenuDetail;
import java.util.List;
@Repository
public interface ConditionalMenuDetailDao extends CrudDao<ConditionalMenuDetail> {
    List<ConditionalMenuDetail> findByCid(@Param("cid") Long cid);
}