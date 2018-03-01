package cn.damei.service.standBook;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.damei.common.persistence.CrudDao;
import cn.damei.common.service.BaseService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.IdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
public abstract class SelectMaterStandBookUtilsService<D extends CrudDao<T>, T extends IdEntity> extends BaseService<T> {
    @Autowired
    protected D entityDao;
    private static final String PAGE_SORT = "sort";
    public PageTable searchScrollPage(Map<String, Object> params, Pagination page) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            params.put(PAGE_SORT, page.getSort());
            PageHelper.offsetPage(page.getOffset(), page.getLimit());
            Page<T> result = (Page<T>) this.entityDao.search(params);
            return new PageTable<T>(result.getResult(), result.getTotal());
        } catch (Exception e) {
            return null;
        } finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public T getById(Long id) {
        return null;
    }
    public int insert(T entity) {
        return 0;
    }
    public int update(T entity) {
        return 0;
    }
    public int deleteById(Long id) {
        return 0;
    }
    public List<T> findAll() {
        return null;
    }
}
