package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import cn.damei.repository.standBook.SelectMaterialStandBookDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class SelectMaterialStandBookService extends CrudService<SelectMaterialStandBookDao,SelectMaterialStandBook>{
    public List<SelectMaterialStandBook> findAllComboStandard(String orderNo){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllComboStandardByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllUpgradeItem(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllUpgradeItemByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllAddItem(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllAddItemByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllReduceItem(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllReduceItemByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllbasicInstallPrice(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllbasicInstallPriceByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllotherPrice(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllotherPriceByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllOtherSynthesizePrice(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllOtherSynthesizePriceByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllAuxiliaryMaterial(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllAuxiliaryMaterialByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<SelectMaterialStandBook> findAllreduceItemInstallPrice(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAllreduceItemInstallPriceByorderNo(orderNo);
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
