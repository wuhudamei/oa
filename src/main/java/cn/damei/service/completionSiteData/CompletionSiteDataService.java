package cn.damei.service.completionSiteData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.damei.common.PropertyHolder;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.completionSiteData.ContractInformation;
import cn.damei.repository.completionSiteData.CompletionSiteDataDao;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.des.MD5;
import cn.damei.utils.excel.imports.ExcelReaderUtil;
import cn.damei.utils.excel.imports.ExcelRowReaderDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class CompletionSiteDataService extends CrudService<CompletionSiteDataDao,ContractInformation> {
    @Autowired
    private CompletionSiteDataDao completionSiteDataDao;
    @Autowired
    private JdbcTemplate jdbcTemplateOa;
    public Object getCompletionSiteData(Map<String, Object> params) {
        ContractInformation contractInformation = null;
        List<ContractInformation> completionSiteData = completionSiteDataDao.getCompletionSiteData(params);
        if(completionSiteData.size()>0 && completionSiteData !=null){
            for(int i = 0;i< completionSiteData.size();i++){
                if(i == 0){
                    contractInformation = new ContractInformation();
                }
                ContractInformation contractInfo = completionSiteData.get(i);
                if(contractInformation.getJanuaryCount() != null){
                    contractInformation.setJanuaryCount(contractInfo.getJanuaryCount() + contractInformation.getJanuaryCount());
                }else{
                    contractInformation.setJanuaryCount(contractInfo.getJanuaryCount() + 0);
                }
                if(contractInformation.getFebruaryCount() != null){
                    contractInformation.setFebruaryCount(contractInfo.getFebruaryCount() + contractInformation.getFebruaryCount());
                }else{
                    contractInformation.setFebruaryCount(contractInfo.getFebruaryCount() + 0);
                }
                if(contractInformation.getMarchCount() != null){
                    contractInformation.setMarchCount(contractInfo.getMarchCount() + contractInformation.getMarchCount());
                }else{
                    contractInformation.setMarchCount(contractInfo.getMarchCount() + 0);
                }
                if(contractInformation.getAprilCount() != null){
                    contractInformation.setAprilCount(contractInfo.getAprilCount() + contractInformation.getAprilCount());
                }else{
                    contractInformation.setAprilCount(contractInfo.getAprilCount() + 0);
                }
                if(contractInformation.getMayCount() != null){
                    contractInformation.setMayCount(contractInfo.getMayCount() + contractInformation.getMayCount());
                }else{
                    contractInformation.setMayCount(contractInfo.getMayCount() + 0);
                }
                if(contractInformation.getJuneCount() != null){
                    contractInformation.setJuneCount(contractInfo.getJuneCount() + contractInformation.getJuneCount());
                }else{
                    contractInformation.setJuneCount(contractInfo.getJuneCount() + 0);
                }
                if(contractInformation.getJulyCount() != null){
                    contractInformation.setJulyCount(contractInfo.getJulyCount() + contractInformation.getJulyCount());
                }else{
                    contractInformation.setJulyCount(contractInfo.getJulyCount() + 0);
                }
                if(contractInformation.getAugustCount() != null){
                    contractInformation.setAugustCount(contractInfo.getAugustCount() + contractInformation.getAugustCount());
                }else{
                    contractInformation.setAugustCount(contractInfo.getAugustCount() + 0);
                }
                if(contractInformation.getSeptemberCount() != null){
                    contractInformation.setSeptemberCount(contractInfo.getSeptemberCount() + contractInformation.getSeptemberCount());
                }else{
                    contractInformation.setSeptemberCount(contractInfo.getSeptemberCount() + 0);
                }
                if(contractInformation.getOctoberCount() != null){
                    contractInformation.setOctoberCount(contractInfo.getOctoberCount() + contractInformation.getOctoberCount());
                }else{
                    contractInformation.setOctoberCount(contractInfo.getOctoberCount() + 0);
                }
                if(contractInformation.getNovemberCount() != null){
                    contractInformation.setNovemberCount(contractInfo.getNovemberCount() + contractInformation.getNovemberCount());
                }else{
                    contractInformation.setNovemberCount(contractInfo.getNovemberCount() + 0);
                }
                if(contractInformation.getDecemberCount() != null){
                    contractInformation.setDecemberCount(contractInfo.getDecemberCount() + contractInformation.getDecemberCount());
                }else{
                    contractInformation.setDecemberCount(contractInfo.getDecemberCount() + 0);
                }
                contractInformation.setStoreName("全国");
            }
            completionSiteData.add(contractInformation);
        }
        return completionSiteData;
    }
    public String importFile(@RequestParam("file") MultipartFile file) {
        String[] columns = new String[]{"project_code", "customer_name", "customer_phone", "project_address",
                "designer_name", "designer_phone", "manager_name", "manager_phone","inspector_name",
                "inspector_phone", "contract_start_date", "contract_finish_date", "actual_start_date", "actual_finish_date",
                "contract_total_amount", "alteration_total_amount", "change_total_amount", "paid_total_amount",
                "store_name", "project_mode", "data_sources", "create_time", "create_user_id"};
        String tableName = "order_contract_info";
        final StringBuilder str = new StringBuilder();
        ExcelRowReaderDB readerDBx = new ExcelRowReaderDB(jdbcTemplateOa, tableName, columns, Integer.MAX_VALUE) {
            @Override
            public boolean checkRow(int sheetIndex, int curRow, List<String> rowlist) {
                if (curRow == 0) {
                    return false;
                }
                if(rowlist!=null && rowlist.size()>0){
                    String projectCode = rowlist.get(0);
                    String customerName = rowlist.get(1);
                    String customerPhone = rowlist.get(2);
                    String storeName = rowlist.get(18);
                    String dataSources = rowlist.get(20);
                    if(projectCode.trim().length() == 0||customerName.trim().length() == 0||customerPhone.trim().length() == 0||storeName.trim().length() == 0||!"人工录入".equals(dataSources)){
                        str.append("第" +curRow+ "行数据有误!<br/>");
                        return false;
                    }
                }
                ShiroUser user = WebUtils.getLoggedUser();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateNow = sdf.format(new Date());
                rowlist.add(dateNow);
                rowlist.add(String.valueOf(user.getId()));
                return true;
            }
        };
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +file.getOriginalFilename());
        try {
            file.transferTo(tmpFile);
            String absolutePath = tmpFile.getAbsolutePath();
            readerDBx.setIgnore(true);
            readerDBx.setEmtyFillStr("");
            readerDBx.setErrMsg(true);
            readerDBx.setErrMsgColumn(new Integer[] { 0 });
            ExcelReaderUtil.readExcel(readerDBx, absolutePath);
            if(readerDBx.getRowCount() == 1 && str.toString().length() == 0){
                str.append("导入数据为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    public int orderInsert(){
        ShiroUser user = WebUtils.getLoggedUser();
        List<ContractInformation> contractInformationList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String secretKey = PropertyHolder.getWorkerMd5();
        String key = MD5.encryptKey(secretKey).toUpperCase();
        String url = PropertyHolder.getCompletionSiteAllDataUrl();
        Map<String, String> params = Maps.newHashMap();
        params.put("key",key);
        String completionSiteData = HttpUtils.post(url,params);
        Map<String, Object> contractInformationMap = JsonUtils.fromJsonAsMap(completionSiteData, String.class, Object.class);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) contractInformationMap.get("data");
        for(int i=0;i<dataList.size();i++) {
            Map<String, Object> dataMap = dataList.get(i);
            String storeName = (String) dataMap.get("stroeName");
            if(storeName != null && storeName.length() > 0){
                completionSiteDataDao.deleteByStoreName(storeName);
            }
        }
        if("1".equals(contractInformationMap.get("code"))){
            if(dataList != null && dataList.size()>0){
                try {
                    DynamicDataSourceHolder.setDataSource("dataSourceMdni");
                    for(int i=0;i<dataList.size();i++){
                        Map<String, Object> dataMap = dataList.get(i);
                        ContractInformation contractInformation = new ContractInformation();
                        String orderNumber = (String) dataMap.get("orderNumber");
                        String customerName = (String) dataMap.get("customerName");
                        String customerPhone = (String) dataMap.get("customerPhone");
                        String projectAddress = (String) dataMap.get("customerAddress");
                        String designerName = (String) dataMap.get("designerName");
                        String designerPhone = (String) dataMap.get("designerPhone");
                        String managerName = (String) dataMap.get("itemManager");
                        String managerPhone = (String) dataMap.get("itemManagerPhone");
                        String inspectorName = (String) dataMap.get("orderInspector");
                        String inspectorPhone = (String) dataMap.get("orderInspectorPhone");
                        String contractStartDate = (String) dataMap.get("contractStartDate");
                        String contractFinshDate = (String) dataMap.get("contractEndDate");
                        String actualStartDate = (String) dataMap.get("actualStartDate");
                        String actualFinishDate = (String) dataMap.get("actualEndDate");
                        String storeName = (String) dataMap.get("stroeName");
                        String projectMode = (String) dataMap.get("label");
                        ContractInformation contractTotalAmount = completionSiteDataDao.getContractTotalAmountByProjectCode(orderNumber);
                        ContractInformation paidTotalAmount = completionSiteDataDao.getPaidTotalAmountByProjectCode(orderNumber);
                        ContractInformation changeTotalAmount = completionSiteDataDao.getChangeTotalAmountByProjectCode(orderNumber);
                        contractInformation.setProjectCode(orderNumber);
                        contractInformation.setCustomerName(customerName);
                        contractInformation.setCustomerPhone(customerPhone);
                        contractInformation.setProjectAddress(projectAddress);
                        contractInformation.setDesignerName(designerName);
                        contractInformation.setDesignerPhone(designerPhone);
                        contractInformation.setManagerName(managerName);
                        contractInformation.setManagerPhone(managerPhone);
                        contractInformation.setInspectorName(inspectorName);
                        contractInformation.setInspectorPhone(inspectorPhone);
                        if(contractStartDate != null && contractStartDate.length() > 0){
                            contractInformation.setContractStartDate(sdf.parse(contractStartDate));
                        }
                        if(contractFinshDate != null && contractFinshDate.length() > 0){
                            contractInformation.setContractFinishDate(sdf.parse(contractFinshDate));
                        }
                        if(actualStartDate != null && actualStartDate.length() > 0){
                            contractInformation.setActualStartDate(sdf.parse(actualStartDate));
                        }
                        if(actualFinishDate != null && actualFinishDate.length() > 0){
                            contractInformation.setActualFinishDate(sdf.parse(actualFinishDate));
                        }
                        if(contractTotalAmount != null){
                            contractInformation.setContractTotalAmount(contractTotalAmount.getContractTotalAmount());
                            contractInformation.setAlterationTotalAmount(contractTotalAmount.getAlterationTotalAmount());
                        }
                        if(changeTotalAmount != null){
                            contractInformation.setChangeTotalAmount(changeTotalAmount.getChangeTotalAmount());
                        }
                        if(paidTotalAmount != null){
                            contractInformation.setPaidTotalAmount(paidTotalAmount.getPaidTotalAmount());
                        }
                        contractInformation.setStoreName(storeName);
                        contractInformation.setProjectMode(projectMode);
                        contractInformation.setDataSources("产业工人系统同步");
                        if(user == null){
                            contractInformation.setCreateUserId(null);
                        }else{
                            contractInformation.setCreateUserId(user.getId().intValue());
                        }
                        contractInformation.setCreateTime(new Date());
                        contractInformationList.add(contractInformation);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    DynamicDataSourceHolder.clearDataSource();//清除此数据源
                }
            }
        }
        int i = 0;
        if(contractInformationList!=null&&contractInformationList.size()>0){
            i = completionSiteDataDao.batchInsert(contractInformationList);
        }
        return i;
    }
    public void orderByActualEndDate(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date=calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String actualEndDate = sdf.format(date);
        String secretKey = PropertyHolder.getWorkerMd5();
        String key = MD5.encryptKey(actualEndDate+secretKey).toUpperCase();
        String url = PropertyHolder.getCompletionSiteDataUrl();
        Map<String, String> params = Maps.newHashMap();
        params.put("actualEndDate",actualEndDate);
        params.put("key",key);
        String completionSiteData = HttpUtils.post(url,params);
        ShiroUser user = WebUtils.getLoggedUser();
        Map<String, Object> contractInformationMap = JsonUtils.fromJsonAsMap(completionSiteData, String.class, Object.class);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) contractInformationMap.get("data");
        List<ContractInformation> contractInformationList = Lists.newArrayList();
        if("1".equals(contractInformationMap.get("code"))){
            if(dataList != null && dataList.size()>0){
                try {
                    DynamicDataSourceHolder.setDataSource("dataSourceMdni");
                    for(int i=0;i<dataList.size();i++){
                        Map<String, Object> dataMap = dataList.get(i);
                        ContractInformation contractInformation = new ContractInformation();
                        String orderNumber = (String) dataMap.get("orderNumber");
                        String customerName = (String) dataMap.get("customerName");
                        String customerPhone = (String) dataMap.get("customerPhone");
                        String projectAddress = (String) dataMap.get("customerAddress");
                        String designerName = (String) dataMap.get("designerName");
                        String designerPhone = (String) dataMap.get("designerPhone");
                        String managerName = (String) dataMap.get("itemManager");
                        String managerPhone = (String) dataMap.get("itemManagerPhone");
                        String inspectorName = (String) dataMap.get("orderInspector");
                        String inspectorPhone = (String) dataMap.get("orderInspectorPhone");
                        String contractStartDate = (String) dataMap.get("contractStartDate");
                        String contractFinshDate = (String) dataMap.get("contractEndDate");
                        String actualStartDate = (String) dataMap.get("actualStartDate");
                        String actualFinishDate = (String) dataMap.get("actualEndDate");
                        String storeName = (String) dataMap.get("stroeName");
                        String projectMode = (String) dataMap.get("label");
                        ContractInformation contractTotalAmount = completionSiteDataDao.getContractTotalAmountByProjectCode(orderNumber);
                        ContractInformation paidTotalAmount = completionSiteDataDao.getPaidTotalAmountByProjectCode(orderNumber);
                        ContractInformation changeTotalAmount = completionSiteDataDao.getChangeTotalAmountByProjectCode(orderNumber);
                        contractInformation.setProjectCode(orderNumber);
                        contractInformation.setCustomerName(customerName);
                        contractInformation.setCustomerPhone(customerPhone);
                        contractInformation.setProjectAddress(projectAddress);
                        contractInformation.setDesignerName(designerName);
                        contractInformation.setDesignerPhone(designerPhone);
                        contractInformation.setManagerName(managerName);
                        contractInformation.setManagerPhone(managerPhone);
                        contractInformation.setInspectorName(inspectorName);
                        contractInformation.setInspectorPhone(inspectorPhone);
                        if(contractStartDate != null && contractStartDate.length() > 0){
                            contractInformation.setContractStartDate(sdf.parse(contractStartDate));
                        }
                        if(contractFinshDate != null && contractFinshDate.length() > 0){
                            contractInformation.setContractFinishDate(sdf.parse(contractFinshDate));
                        }
                        if(actualStartDate != null && actualStartDate.length() > 0){
                            contractInformation.setActualStartDate(sdf.parse(actualStartDate));
                        }
                        if(actualFinishDate != null && actualFinishDate.length() > 0){
                            contractInformation.setActualFinishDate(sdf.parse(actualFinishDate));
                        }
                        if(contractTotalAmount != null){
                            contractInformation.setContractTotalAmount(contractTotalAmount.getContractTotalAmount());
                            contractInformation.setAlterationTotalAmount(contractTotalAmount.getAlterationTotalAmount());
                        }
                        if(changeTotalAmount != null){
                            contractInformation.setChangeTotalAmount(changeTotalAmount.getChangeTotalAmount());
                        }
                        if(paidTotalAmount != null){
                            contractInformation.setPaidTotalAmount(paidTotalAmount.getPaidTotalAmount());
                        }
                        contractInformation.setStoreName(storeName);
                        contractInformation.setProjectMode(projectMode);
                        contractInformation.setDataSources("产业工人系统同步");
                        if(user == null){
                            contractInformation.setCreateUserId(null);
                        }else{
                            contractInformation.setCreateUserId(user.getId().intValue());
                        }
                        contractInformation.setCreateTime(new Date());
                        contractInformationList.add(contractInformation);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    DynamicDataSourceHolder.clearDataSource();//清除此数据源
                }
            }
        }
        if(contractInformationList!=null&&contractInformationList.size()>0){
            completionSiteDataDao.batchInsert(contractInformationList);
        }
    }
}
