package cn.damei.rest.standBook;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageResult;
import cn.damei.entity.standBook.OrderDetails;
import cn.damei.service.standBook.OrderDetailsService;
import java.util.Date;
import java.util.Map;
@RestController
@RequestMapping("/api/standBook")
public class OrderDetailsController extends BaseController {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @RequestMapping("/getOrderByKeywordAndStatus")
    public Object findAll(@RequestParam(required = false) String keyword,
                          @RequestParam String status,
                          @RequestParam(required = false) Date startDate,
                          @RequestParam(required = false) Date endDate,
                          @RequestParam(required = false ,defaultValue = "0") int offset,
                          @RequestParam(required = false ,defaultValue = "10") int limit) {
        String sql = "SELECT oo.OrderNo,orc.SignFinishTime,oc.CustomerName,oc.CustomerId,oc.CustomerId AS id,REPLACE(oc.Mobile, substring(oc.Mobile,4,4), '****') AS Mobile,oo.AllotState FROM Ord_Order oo LEFT JOIN Ord_Customer oc ON oo.CustomerId = oc.CustomerId LEFT JOIN Ord_Contract orc ON orc.OrderId = oo.OrderId where 1=1";
        MapSqlParameterSource param = new MapSqlParameterSource();
        if(StringUtils.isNotBlank(keyword)){
            sql += "AND (oo.OrderNo = :keyword  OR oc.CustomerName = :keyword OR Mobile = :keyword)";
            param.addValue("keyword",keyword);
        }
        if(StringUtils.isNotBlank(status)){
            sql+= " AND (oo.AllotState =:status)";
            param.addValue("status",status);
        }
        if(startDate != null){
            sql+= " AND (orc.SignFinishTime >=:startDate)";
            param.addValue("startDate",startDate);
        }
        if(endDate != null){
            sql+= " AND (orc.SignFinishTime <=:endDate)";
            param.addValue("endDate",endDate);
        }
        PageResult<Map<String, Object>> orderDetails = this.orderDetailsService.queryForPage(sql,offset,limit,param,new BeanPropertyRowMapper(OrderDetails.class));
        return StatusDto.buildSuccess(orderDetails);
    }
    @RequestMapping("/getOrderDetailByOrderNo")
    public Object getOrderDetailByCustomerId(@RequestParam(required = false) String orderno) {
        OrderDetails orderDetails = this.orderDetailsService.findOrderDetail(orderno);
        return StatusDto.buildSuccess(orderDetails);
    }
    @RequestMapping("/findServiceNameAndStylistName")
    public Object findServiceNameAndStylistName(@RequestParam String orderno) {
        return StatusDto.buildSuccess(this.orderDetailsService.findServiceNameAndStylistName(orderno));
    }
}
