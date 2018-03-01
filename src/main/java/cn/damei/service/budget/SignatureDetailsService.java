package cn.damei.service.budget;
import com.rocoinfo.weixin.util.CollectionUtils;
import cn.damei.common.service.CrudService;
import cn.damei.entity.budget.SignatureDetails;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.repository.budget.SignatureDetailsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SignatureDetailsService extends CrudService<SignatureDetailsDao, SignatureDetails> {
    public List<SignatureDetails> findSignatureDetails(Long companyId, Long type, String month, ApplyStatus... statuses) {
        if (companyId == null || type == null || StringUtils.isEmpty(month)) {
            return Collections.EMPTY_LIST;
        }
        if (statuses != null && statuses.length > 0) {
            return this.entityDao.findSignatureDetails(companyId, month, type, Arrays.asList(statuses));
        }
        return this.entityDao.findSignatureDetails(companyId, month, type, null);
    }
    Map<Long, Double> getOccupied(Long companyId, Long type, String month, ApplyStatus... statuses) {
        List<SignatureDetails> signatureDetailses = findSignatureDetails(companyId, type, month, statuses);
        if (CollectionUtils.isNotEmpty(signatureDetailses)) {
            return this.transferDetails2Map(signatureDetailses);
        }
        return Collections.emptyMap();
    }
    Map<Long, Double> getMoneyGroupByCostId(Long signatureId) {
        List<SignatureDetails> details = getDetailsBySignatureId(signatureId);
        return transferDetails2Map(details);
    }
    public List<SignatureDetails> getDetailsBySignatureId(Long signatureId) {
        if (signatureId == null) {
            throw new IllegalArgumentException("签报单Id不能为空！");
        }
        return this.entityDao.getBySignatureId(signatureId);
    }
    private Map<Long, Double> transferDetails2Map(List<SignatureDetails> signatureDetailses) {
        Map<Long, Double> map = signatureDetailses.stream().collect(Collectors.groupingBy(SignatureDetails::getCostItemId,
                Collectors.mapping(SignatureDetails::getMoney,
                        Collectors.reducing(0d, (m, n) -> n + m))));
        return map;
    }
}