package cn.damei.repository.wechat.tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.tag.Tag;
@Repository
public interface TagDao extends CrudDao<Tag> {
	Tag getTagByOid(@Param("oid") Long oid);
}