package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.DesignTime;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import java.util.List;
@Repository
public interface SelectListDao extends CrudDao<SelectMaterialStandBook>{
    List<SelectMaterialStandBook> findAllComboStandardByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllUpgradeItemByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllAddItemByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllReduceItemByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllbasicInstallPriceByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllotherPriceByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllOtherSynthesizePriceByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllAuxiliaryMaterialByorderNo(@Param("orderno") String orderNo);
    List<SelectMaterialStandBook> findAllPrincipalMaterialByorderNo(String orderNo);
    List<SelectMaterialStandBook> findAllreduceItemInstallPriceByorderNo(String orderNo);
}
