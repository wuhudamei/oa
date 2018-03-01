package cn.damei.service.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillAdjustRecord;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatch;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatchDetail;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillItem;
import cn.damei.repository.materialSupplierAccountChecking.StRdBillBatchDao;
import cn.damei.repository.materialSupplierAccountChecking.StRdBillItemDao;
import cn.damei.utils.WebUtils;
@Service
public class StRdBillItemService extends CrudService<StRdBillItemDao, StRdBillItem> {
    @Autowired
    private StRdBillItemDao stRdBillItemDao;
    @Autowired
    private StRdBillBatchDetailService stRdBillBatchDetailService;
    @Autowired
    private StRdBillBatchDao stRdBillBatchDao;
    @Autowired
    private StRdBillBatchService stRdBillBatchService;
    @Autowired
    private StRdBillAdjustRecordService stRdBillAdjustRecordService;
    public List<Map<String,String>> findSupplyAndGood(String status,String accep) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findSupplyAndGood(status,accep);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<StRdBillItem> findItemBySupplyAndSkuName(String pdSupplier, String brandname , String startTime ,String  endTime,String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findItemBySupplyAndSkuName(pdSupplier,brandname,startTime,endTime,orderNo);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<StRdBillItem> findItemByAcceptance(String pdSupplier, String pdSkuname,String keyword) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findItemByAcceptance(pdSupplier,pdSkuname,keyword);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<StRdBillItem> findBillBatchDetail(String orderNo,String pdSupplier, String brandname,String startTime, String endTime) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findBillBatchDetail(orderNo,pdSupplier,brandname,startTime,endTime);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public String updateBatch(String[] billItemIds, String[] pdCounts, String[] prices,String[] cuArr,String supplier,String batchExplain){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            String operator = WebUtils.getLoggedUser().getName();
            int updateCount = 0;
            Date date = new Date();
            String billBatch = createProjectCode();
            BigDecimal tatolAmountMoney = new BigDecimal("0");
            String bid =UUID.randomUUID().toString();
            List<StRdBillBatchDetail> billBatachDetailList = new ArrayList<StRdBillBatchDetail>();
            StRdBillItem billItem = null;
            for(int i = 0 ; i < billItemIds.length; i++){
                billItem = this.entityDao.getByBillItemId(billItemIds[i]);
                BigDecimal pdCount = new BigDecimal(pdCounts[i]);
                BigDecimal cuArrCount = new BigDecimal(cuArr[i]);
                BigDecimal price = new BigDecimal(prices[i]);
                int count = pdCount.compareTo(cuArrCount);
                if(count == 1){
                    StRdBillBatchDetail stRdBillBatchDetail = new StRdBillBatchDetail();
                    BigDecimal amountMoney = price.multiply(cuArrCount);
                    stRdBillBatchDetail.setBillBatchDetailId(UUID.randomUUID().toString());
                    stRdBillBatchDetail.setBillItemId(billItemIds[i]);
                    stRdBillBatchDetail.setBillBatchId(bid);
                    stRdBillBatchDetail.setBillNumber(cuArrCount);
                    stRdBillBatchDetail.setBillAmountMoney(amountMoney);
                    stRdBillBatchDetail.setSettlement(false);
                    stRdBillBatchDetail.setSettlementTime(null);
                    billBatachDetailList.add(stRdBillBatchDetail);
                    tatolAmountMoney = tatolAmountMoney.add(amountMoney);
                    BigDecimal accumulateSettlementMoney = price.multiply(cuArrCount.add(billItem.getAccumulateSettlementCount()));
                    BigDecimal pdCurrentCount = pdCount.subtract(cuArrCount);
                    this.stRdBillItemDao.updateBatch(cuArrCount.add(billItem.getAccumulateSettlementCount()),accumulateSettlementMoney,pdCurrentCount,status,billItem.getChange(),billItemIds[i]);
                }else {
                    BigDecimal amountMoney = price.multiply(cuArrCount);
                    StRdBillBatchDetail stRdBillBatchDetail = new StRdBillBatchDetail();
                    stRdBillBatchDetail.setBillBatchDetailId(UUID.randomUUID().toString());
                    stRdBillBatchDetail.setBillItemId(billItemIds[i]);
                    stRdBillBatchDetail.setBillBatchId(bid);
                    stRdBillBatchDetail.setBillNumber(cuArrCount);
                    stRdBillBatchDetail.setBillAmountMoney(amountMoney);
                    stRdBillBatchDetail.setSettlement(false);
                    stRdBillBatchDetail.setSettlementTime(null);
                    billBatachDetailList.add(stRdBillBatchDetail);
                    tatolAmountMoney = tatolAmountMoney.add(amountMoney);
                    BigDecimal accumulateSettlementMoney = price.multiply(cuArrCount.add(billItem.getAccumulateSettlementCount()));
                    BigDecimal pdCurrentCount = pdCount.subtract(cuArrCount);
                    this.stRdBillItemDao.updateBatch(cuArrCount.add(billItem.getAccumulateSettlementCount()),accumulateSettlementMoney,pdCurrentCount,reconciliationStatus,billItem.getChange(),billItemIds[i]);
                }
            }
            this.stRdBillBatchDetailService.insertBatch(billBatachDetailList);
            StRdBillBatch stRdBillBatch = new StRdBillBatch();
            stRdBillBatch.setBillBatchId(bid);
            stRdBillBatch.setSupplier(billItem.getPdSupplier());
            stRdBillBatch.setBillAmountMoney(tatolAmountMoney);
            stRdBillBatch.setBillBatch(billBatch);
            stRdBillBatch.setOperator(operator);
            stRdBillBatch.setOperateTime(date);
            stRdBillBatch.setBatchExplain(batchExplain);
            this.stRdBillBatchService.insert(stRdBillBatch);
            return billBatch;
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int updateCount(String billItemId,BigDecimal pdChangeCount,BigDecimal pdChangeCountEnd,String changeAdjust){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            StRdBillAdjustRecord stRdBillAdjustRecord = new StRdBillAdjustRecord();
            stRdBillAdjustRecord.setBillAdjustRecordId(UUID.randomUUID().toString());
            stRdBillAdjustRecord.setBillItemId(billItemId);
            stRdBillAdjustRecord.setPdChangeCountBefore(pdChangeCount);
            stRdBillAdjustRecord.setPdChangeCountEnd(pdChangeCountEnd);
            stRdBillAdjustRecord.setChangeAdjust(changeAdjust);
            stRdBillAdjustRecord.setOperator(WebUtils.getLoggedUser().getName());
            stRdBillAdjustRecord.setChangeTime(new Date());
            stRdBillAdjustRecord.setOperateType("手动");
            stRdBillAdjustRecordService.insert(stRdBillAdjustRecord);
            return this.entityDao.updateCount(billItemId,pdChangeCountEnd);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    private String status = "未对账";
    private String reconciliationStatus = "已对账";
    public List<StRdBillItem> findCheckOnWork(List<String> billItemIds) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findCheckOnWork(billItemIds);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int checkAndAccept(Map<String, Object> params) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.checkAndAccept(params);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<StRdBillItem> findPrice() {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findPrice();
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int updatePrice(String[] prices,String[] billItemIds) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            int updateCount = 0;
            for(int i = 0; i<prices.length;i++){
                BigDecimal price = new BigDecimal(prices[i]);
                updateCount = this.entityDao.updatePrice(price,billItemIds[i]);
            }
            return updateCount;
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    private String createProjectCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());
        String code = stRdBillBatchDao.selectByDate(currentDate);
        if (StringUtils.isEmpty(code)) {
            return currentDate + "001";
        } else {
            int s = Integer.parseInt(code.replace(currentDate, "")) + 1;
            s = s == 1000 ? 1 : s;
            String reslut = s >= 10 ? (s > 100 ? s + "" : "0" + s) : "00" + s;
            return currentDate + reslut;
        }
    }
    public List<StRdBillItem> findOrderByNoAccountCHecking(String pdSupplier, String brandname, String startTime, String endTime,String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return stRdBillItemDao.findOrderByNoAccountCHecking(pdSupplier,brandname,startTime,endTime,orderNo);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
