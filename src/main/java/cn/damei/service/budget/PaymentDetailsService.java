package cn.damei.service.budget;
import com.google.common.collect.Lists;
import cn.damei.common.service.CrudService;
import cn.damei.entity.budget.PaymentDetails;
import cn.damei.entity.budget.SignatureDetails;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.FirstTypes;
import cn.damei.repository.budget.PaymentDetailsDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;
@Service
public class PaymentDetailsService extends CrudService<PaymentDetailsDao, PaymentDetails> {
    Map<Long, Double> getOccupied(Long companyId, Long type, String month, ApplyStatus... statuses) {
        if (companyId == null || type == null || StringUtils.isEmpty(month)) {
            return Collections.emptyMap();
        }
        List<PaymentDetails> details = this.findByConditions(companyId, type, month, statuses);
        if (CollectionUtils.isNotEmpty(details)) {
            return transferDetails2Map(details);
        }
        return Collections.emptyMap();
    }
     public Map<Long, Double> getOccupied(Long signatureId) {
        if (signatureId == null) {
            return Collections.emptyMap();
        }
        List<PaymentDetails> details = this.entityDao.getDetailsBySignatureId(signatureId, Lists.newArrayList(ApplyStatus.APPROVALING, ApplyStatus.ADOPT));
        if (CollectionUtils.isNotEmpty(details)) {
            return transferDetails2Map(details);
        }
        return Collections.emptyMap();
    }
    private Map<Long, Double> transferDetails2Map(List<PaymentDetails> details) {
        Map<Long, Double> moneys = details.stream().collect(groupingBy(PaymentDetails::getCostItemId,
                mapping(PaymentDetails::getMoney,
                        reducing(0D, (m, n) -> n + m))));
        return moneys;
    }
    public List<PaymentDetails> findByConditions(Long companyId, Long type, String month, ApplyStatus... statuses) {
        if (statuses != null && statuses.length > 0) {
            return this.entityDao.findByConditions(companyId, type, month, Arrays.asList(statuses));
        }
        return this.entityDao.findByConditions(companyId, type, month, null);
    }
    public List<PaymentDetails> getByPaymentId(Long id) {
        return entityDao.getByPaymentId(id);
    }
}