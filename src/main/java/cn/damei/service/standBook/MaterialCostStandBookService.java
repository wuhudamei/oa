package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import cn.damei.repository.standBook.SelectMaterialStandBookDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class MaterialCostStandBookService extends CrudService<SelectMaterialStandBookDao,SelectMaterialStandBook>{
    @Autowired
    private SelectMaterialStandBookDao selectMaterialStandBookDao;
    public List<SelectMaterialStandBook> findAllPrincipalMaterial(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.selectMaterialStandBookDao.findAllPrincipalMaterialByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
