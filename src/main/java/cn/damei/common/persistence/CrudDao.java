package cn.damei.common.persistence;
import java.util.List;
import java.util.Map;
public interface CrudDao<T> extends BaseDao {
    String KEYWORD = "keyword";
    String STATUS = "status";
    T getById(Long id);
    int insert(T entity);
    int update(T entity);
    int deleteById(Long id);
    List<T> findAll();
    List<T> search(Map<String, Object> params);
    Long searchTotal(Map<String, Object> params);
}