package cn.damei.common.service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.damei.common.persistence.CrudDao;
import cn.damei.dto.page.PageResult;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.IdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
public abstract class CrudService<D extends CrudDao<T>, T extends IdEntity> extends BaseService<T> {
    @Autowired
    protected D entityDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public T getById(Long id) {
        if (id == null || id < 1)
            return null;
        return entityDao.getById(id);
    }
    @Transactional
    public int insert(T entity) {
        if (entity == null)
            return 0;
        return entityDao.insert(entity);
    }
    public int update(T entity) {
        if (entity == null || entity.getId() == null)
            return 0;
        return entityDao.update(entity);
    }
    public int deleteById(Long id) {
        if (id == null || id < 1)
            return 0;
        return this.entityDao.deleteById(id);
    }
    public List<T> findAll() {
        return entityDao.findAll();
    }
    private static final String PAGE_SORT = "sort";
    public PageTable searchScrollPage(Map<String, Object> params, Pagination page) {
        params.put(PAGE_SORT, page.getSort());
        PageHelper.offsetPage(page.getOffset(), page.getLimit());
        Page<T> result = (Page<T>) this.entityDao.search(params);
        return new PageTable<T>(result.getResult(), result.getTotal());
    }
    public PageResult<Map<String, Object>> queryForPage(String sql, int offset, int limit, MapSqlParameterSource param, RowMapper<T> rowMapper) {
        String countSql = " select count(*) from ( " + sql + ") a";
        Integer total = namedParameterJdbcTemplate.queryForObject(countSql, param, Integer.class);
        StringBuffer pageSql = pageSql(sql,offset,limit);
        List<Map<String, Object>> queryForList = namedParameterJdbcTemplate.queryForList(pageSql.toString(), param);
        PageResult<Map<String, Object>> page = new PageResult<Map<String, Object>>();
        page.setRows(queryForList);
        page.setTotal(total);
        return page;
    }
    public StringBuffer pageSql(String sql, int offset, int limit) {
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT TOP "+limit+" *\n" +
                "  FROM (SELECT ROW_NUMBER() OVER(ORDER BY id) AS rownumber,*\n" +
                "          FROM ("+sql+") a) A\n" +
                " WHERE rownumber >= "+offset);
        return buff;
    }
}
