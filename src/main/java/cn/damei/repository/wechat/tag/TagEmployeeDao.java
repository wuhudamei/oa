package cn.damei.repository.wechat.tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.tag.TagEmployee;
import java.util.List;
import java.util.Map;
@Repository
public interface TagEmployeeDao extends CrudDao<TagEmployee> {
    List<TagEmployee> findAllByTagId(@Param("tagId") Long tagId);
    int deleteByOpenids(@Param("openids") List<String> openids);
    void batchInsert(@Param("list") List<TagEmployee> list);
    List<TagEmployee> findByTagIdScrollPage(Map<String, Object> params);
}
