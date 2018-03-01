package cn.damei.repository.oa;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.oa.ApplyPurchase;
@Repository
public interface ApplyPurchaseDao extends CrudDao<ApplyPurchase> {
    Double sumPurchaseByCompany (@Param(value = "companyId") Long companyId);
}