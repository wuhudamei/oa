package cn.damei.repository.mdnicontract;
import org.springframework.stereotype.Repository;
import cn.damei.entity.mdnicontract.StylistContract;
import java.util.List;
import java.util.Map;
@Repository
public interface StylistContractDao {
    String STYLIST_NAME = "stylistName";
    String STYLIST_MOBILE = "stylistMobile";
    String MONTH = "month";
    List<StylistContract> getByCondition(Map<String, ? extends Object> params);
}
