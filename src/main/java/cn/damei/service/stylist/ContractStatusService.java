package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.ContractStatus;
import cn.damei.repository.stylist.ContractStatusDao;
import java.util.List;
@Service
public class ContractStatusService  extends CrudService<ContractStatusDao,ContractStatus> {
    public void batchInsert(List<ContractStatus> contractStatuses) {
        if(CollectionUtils.isNotEmpty(contractStatuses)) {
            this.entityDao.batchInsert(contractStatuses);
        }
    }
}