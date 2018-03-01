package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.Contract;
import cn.damei.entity.stylist.ContractStatus;
import cn.damei.entity.stylist.Stylist;
import cn.damei.enumeration.stylist.Status;
import cn.damei.repository.stylist.ContractDao;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import java.util.*;
import static java.util.stream.Collectors.groupingBy;
@Service
@Lazy(value = false)
public class ContractService extends CrudService<ContractDao, Contract> {
    @Autowired
    private StylistService stylistService;
    @Autowired
    private ContractStatusService contractStatusService;
    @Autowired
    private MdniContractService mdniContractService;
    public List<Contract> findByMonth(String month) {
        return this.entityDao.search(MapUtils.of(ContractDao.SEARCH_MONTH, month));
    }
    public synchronized String synByUserAndMonth(Long userId, String month) {
        if (StringUtils.isBlank(month)) {
            return "请选择同步月份！";
        }
        Stylist stylist = stylistService.getByUseId(userId);
        if (stylist == null) {
            return "没有查到您的信息，请联系管理员，将你添加到设计师列表！";
        }
        synByUserAndMonth(stylist, month);
        return null;
    }
    @Scheduled(cron = "0 0 0 1 * ? ")
    public synchronized void syncAllStylistContract() {
        List<Stylist> stylists = stylistService.findAll();
        String currentMonth = DateUtils.format(new Date(), DateUtils.YYYY_MM);
//        String currentMonth = "2017-04";
        List<Contract> localContracts = this.getContractsByMonth(currentMonth);
        List<Contract> remoteContracts = mdniContractService.findByMonth(currentMonth);
        if (CollectionUtils.isNotEmpty(stylists) && CollectionUtils.isNotEmpty(remoteContracts)) {
            Map<Long, List<Contract>> stylistLocalContracts = localContracts.stream().collect(groupingBy(Contract::getEmpId));
            Map<Long, List<Contract>> stylistRemoteContracts = remoteContracts.stream().collect(groupingBy(Contract::getEmpId));
            for (Stylist stylist : stylists) {
                List<Contract> stylistLocalContract = stylistLocalContracts.get(stylist.getUserId());
                List<Contract> stylistRemoteContract = stylistRemoteContracts.get(stylist.getUserId());
                if (CollectionUtils.isNotEmpty(stylistRemoteContract)) {
                    this.CASContract(stylistRemoteContract, stylistLocalContract);
                }
            }
        }
    }
    public List<Contract> getContractsByMonth(String month) {
        return this.entityDao.search(MapUtils.of(ContractDao.SEARCH_MONTH, month));
    }
    private void synByUserAndMonth(Stylist stylist, String month) {
        List<Contract> remoteContracts = getRemoteContracts(stylist, month);
        if (CollectionUtils.isEmpty(remoteContracts)) {
            return;
        }
        List<Contract> dbContracts = this.getByUserAndMonth(stylist.getUserId(), month);
        this.CASContract(remoteContracts, dbContracts);
    }
    public List<Contract> getByUserAndMonth(Long userId, String month) {
        Map<String, Object> params = new HashMap<>();
        params.put(ContractDao.USER_ID, userId);
        params.put(ContractDao.SEARCH_MONTH, month);
        return this.entityDao.search(params);
    }
    private List<Contract> getRemoteContracts(Stylist stylist, String month) {
        return mdniContractService.findByStylistAndMonth(stylist, month);
    }
    @Transactional
    private void CASContract(List<Contract> remoteContracts, List<Contract> dbContracts) {
        if (CollectionUtils.isEmpty(remoteContracts)) {
            return;
        }
        List<ContractStatus> contractStatuses = new ArrayList<>();
        for (Contract remoteContract : remoteContracts) {
            Contract dbContract = getByContractNoAndStatus(dbContracts, remoteContract.getContractNo(), null);
            if (dbContract != null) {
                Contract dbContractStatus = getByContractNoAndStatus(dbContracts, remoteContract.getContractNo(), remoteContract.getStatus().getStatus());
                if (dbContractStatus == null) {
                    ContractStatus contractStatus = remoteContract.getStatus();
                    contractStatus.setContractId(dbContract.getId());
                    contractStatuses.add(contractStatus);
                }
            } else {
                this.insertAndStatus(remoteContract);
            }
        }
        if (CollectionUtils.isNotEmpty(contractStatuses)) {
            contractStatusService.batchInsert(contractStatuses);
        }
    }
    @Transactional
    private void insertAndStatus(Contract remoteContract) {
        super.insert(remoteContract);
        ContractStatus status = remoteContract.getStatus();
        status.setContractId(remoteContract.getId());
        contractStatusService.insert(status);
    }
    private Contract getByContractNoAndStatus(List<Contract> contracts, String contractNo, Status status) {
        if (CollectionUtils.isNotEmpty(contracts) && StringUtils.isNotBlank(contractNo)) {
            if (status == null) {
                for (Contract contract : contracts) {
                    if (contractNo.equals(contract.getContractNo())) {
                        return contract;
                    }
                }
            } else {
                for (Contract contract : contracts) {
                    if (contractNo.equals(contract.getContractNo()) || status.equals(Optional.of(contract).map(Contract::getStatus).map(ContractStatus::getStatus).orElse(null))) {
                        return contract;
                    }
                }
            }
        }
        return null;
    }
}