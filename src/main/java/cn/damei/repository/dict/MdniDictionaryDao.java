package cn.damei.repository.dict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.dict.MdniDictionary;
import java.util.List;
@Repository
public interface MdniDictionaryDao extends CrudDao<MdniDictionary> {
    List<MdniDictionary> getNode(@Param("type") Long type);
    List<MdniDictionary> findChildNodeByParent(@Param("parentCode") Long parentCode);
    List<MdniDictionary> findByTypeIfId(@Param(value = "id") Long id, @Param("type") Integer type);
    List<MdniDictionary> findByParentIds(@Param(value = "parentIds") List<Long> parentIds);
    List<MdniDictionary> getByType(@Param("parentCode") Integer parentCode, @Param("type") Integer type);
    List<MdniDictionary> findTree();
}