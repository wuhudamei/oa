package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.Stylist;
import java.util.List;
@Repository
public interface StylistDao extends CrudDao<Stylist> {
    void batchInsert(@Param(value = "stylists") List<Stylist> stylists);
    Stylist getByUserId(Long userId);
}