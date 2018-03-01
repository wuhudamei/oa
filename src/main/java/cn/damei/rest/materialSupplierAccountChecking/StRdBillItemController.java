package cn.damei.rest.materialSupplierAccountChecking;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillItem;
import cn.damei.service.materialSupplierAccountChecking.StRdBillItemService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.excel.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value ="/api/stRdBillItem")
public class StRdBillItemController extends BaseController {
    @Autowired
    private StRdBillItemService stRdBillItemService;
    @RequestMapping("/findSupplyAndGood")
    public Object findSupplyAndGood(@RequestParam String status,String accep){
        return StatusDto.buildSuccess(stRdBillItemService.findSupplyAndGood(status,accep));
    }
    @RequestMapping("/findItemBySupplyAndSkuName")
    public Object findItemBySupplyAndSkuName(@RequestParam String pdSupplier,
                                             @RequestParam String brandname ,
                                             @RequestParam String startTime ,
                                             @RequestParam String  endTime,
                                             @RequestParam String orderNo){
        if(StringUtils.isNotBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNotBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        return StatusDto.buildSuccess(this.stRdBillItemService.findItemBySupplyAndSkuName(pdSupplier,brandname,startTime,endTime,orderNo));
    }
    @RequestMapping("/findItemByAcceptance")
    public Object findItemByAcceptance(@RequestParam String pdSupplier,
                                       @RequestParam String pdSkuname,
                                       @RequestParam String keyword){
        List<StRdBillItem> itemByAcceptance = this.stRdBillItemService.findItemByAcceptance(pdSupplier, pdSkuname, keyword);
        Map<String, List<StRdBillItem>> map = new HashMap<>();
        map.put("data",itemByAcceptance);
        return StatusDto.buildSuccess(map);
    }
    @RequestMapping("/updateCount")
    public Object updateCount(@RequestParam String billItemId,
                              @RequestParam BigDecimal pdCurrentCount,
                              @RequestParam BigDecimal pdChangeCountEnd,
                              @RequestParam String changeAdjust){
        return StatusDto.buildSuccess(this.stRdBillItemService.updateCount(billItemId,pdCurrentCount,pdChangeCountEnd,changeAdjust));
    }
    @RequestMapping("/updateBatch")
    public Object updateBatch(HttpServletRequest request) {
        String[] billItemIds = request.getParameterValues("billItemIds[]");
        String[] pdCounts = request.getParameterValues("pdCounts[]");
        String[] prices = request.getParameterValues("prices[]");
        String[] cuArr = request.getParameterValues("cuArr[]");
        String supplier = request.getParameter("supplier");
        String batchExplain = request.getParameter("batchExplain");
        String  billBatch = this.stRdBillItemService.updateBatch(billItemIds, pdCounts, prices, cuArr, supplier,batchExplain);
        StatusDto dto = StatusDto.buildSuccess();
        dto.setData(billBatch);
        return dto;
    }
    @RequestMapping("/checkAndAccept")
    public Object checkAndAccept(@RequestParam("billItemIds[]") String[] billItemIds){
        Date date = new Date();
        Map<String, Object> params = Maps.newHashMap();
        for(String billItemId : billItemIds){
            MapUtils.putNotNull(params,"date",date);
            MapUtils.putNotNull(params,"billItemId",billItemId);
            stRdBillItemService.checkAndAccept(params);
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping("/noAccountCheckingOrder")
    public Object noAccountCheckingOrder(@RequestParam String pdSupplier,
                                         @RequestParam String brandname ,
                                         @RequestParam String startTime ,
                                         @RequestParam String  endTime,
                                         @RequestParam String orderNo){
        List<StRdBillItem> orderByNoAccountCHecking = stRdBillItemService.findOrderByNoAccountCHecking(pdSupplier, brandname, startTime, endTime,orderNo);
        Map<String,List<StRdBillItem>> map = new HashMap<>();
        map.put("data",orderByNoAccountCHecking);
        return StatusDto.buildSuccess(map);
    }
    @RequestMapping("/export")
    public void export(HttpServletResponse resp,
                       @RequestParam String[] billItemId,
                       @RequestParam String[] cuArr,
                       @RequestParam(required = false) String billBatch) {
        ServletOutputStream out = null;
        Workbook workbook = null;
        Map<String,String> map = new HashMap<>();
        BigDecimal cuArrCount = null;
        for(int i=0;i<billItemId.length;i++ ){
            map.put(billItemId[i],cuArr[i]);
        }
        List<StRdBillItem> list = stRdBillItemService.findCheckOnWork( java.util.Arrays.asList(billItemId));
        for (StRdBillItem stRdBillItem : list){
            cuArrCount =new BigDecimal(map.get(stRdBillItem.getBillItemId()));
            stRdBillItem.setPrice(stRdBillItem.getPdPrice().multiply(cuArrCount));
            stRdBillItem.setBillNumber(map.get(stRdBillItem.getBillItemId()));
            if(stRdBillItem.getChange()){
                stRdBillItem.setIsChange("已调整");
                stRdBillItem.setCount(stRdBillItem.getPdCurrentCount());
            }else {
                stRdBillItem.setIsChange("未调整");
                stRdBillItem.setCount(stRdBillItem.getPdCurrentCount());
            }
        }
        try {
            resp.setContentType("application/x-msdownload");
            resp.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode("对账表.xlsx", "UTF-8") + "\"");
            out = resp.getOutputStream();
            if (CollectionUtils.isNotEmpty(list)) {
                workbook = ExcelUtil.getInstance().exportObj2ExcelWithTitleAndFields(list, StRdBillItem.class, true, titles, fields);
            }
            if (workbook != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    @RequestMapping("/findPrice")
    public Object findPrice(){
        List<StRdBillItem> itemByPrice = this.stRdBillItemService.findPrice();
        Map<String, List<StRdBillItem>> map = new HashMap<>();
        map.put("data",itemByPrice);
        return StatusDto.buildSuccess(map);
    }
    @RequestMapping("/updatePrice")
    public Object updatePrice(HttpServletRequest request){
        String[] billItemIds = request.getParameterValues("billItemIds[]");
        String[] prices = request.getParameterValues("prices[]");
        return StatusDto.buildSuccess(this.stRdBillItemService.updatePrice(prices,billItemIds));
    }
    private static List<String> titles = null;
    private static List<String> fields = null;
    static {
        titles = Lists.newArrayListWithExpectedSize(3);
        titles.add("订单编号");
        titles.add("客户姓名");
        titles.add("商品名称");
        titles.add("规格");
        titles.add("待结算数量");
        titles.add("本次结算数量");
        titles.add("单价");
        titles.add("状态");
        titles.add("是否调整");
        titles.add("总金额");
        fields = Lists.newArrayListWithExpectedSize(3);
        fields.add("orderNo");
        fields.add("customerName");
        fields.add("pdSkuname");
        fields.add("pdModel");
        fields.add("count");
        fields.add("billNumber");
        fields.add("pdPrice");
        fields.add("reconciliationStatus");
        fields.add("isChange");
        fields.add("price");
    }
}
