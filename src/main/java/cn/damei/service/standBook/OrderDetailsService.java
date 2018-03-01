package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.OrderDetails;
import cn.damei.repository.standBook.OrderDetailsByOrderNoDao;
import cn.damei.repository.standBook.OrderDetailsDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Service
public class OrderDetailsService extends CrudService<OrderDetailsDao, OrderDetails> {
    @Autowired
    private OrderDetailsByOrderNoDao orderDetailsByOrderNoDao;
    public List<OrderDetails> findAll(Map<String ,Object> params){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            List<OrderDetails> orderDetails = this.entityDao.findAllOrder(params);
            return orderDetails;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public OrderDetails findOrderDetail(String orderno){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            OrderDetails order = this.entityDao.findOrderDetail(orderno);
            OrderDetails orderDetails = findOrderDetailsByOrderNo(orderno);
            if(orderDetails == null){
                order.setSecondLinkman(null);
                order.setSecondLinkmanMobile(null);
                order.setMeasurehousearea(null);
                order.setUnitprice(null);
            }else{
                order.setSecondLinkman(orderDetails.getSecondLinkman());
                order.setSecondLinkmanMobile(orderDetails.getSecondLinkmanMobile());
                order.setMeasurehousearea(orderDetails.getMeasurehousearea());
                order.setUnitprice(orderDetails.getUnitprice());
            }
            return order;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public OrderDetails findServiceNameAndStylistName(String orderno){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try{
            OrderDetails orderDetails = this.entityDao.findServiceNameAndStylistName(orderno);
            return orderDetails;
        }catch (Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    private OrderDetails findOrderDetailsByOrderNo(String orderno){
        OrderDetails orderDetails = new OrderDetails();
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.orderDetailsByOrderNoDao.findOrderDetailByOrderNo(orderno);
        }catch(Exception e){
            return orderDetails;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
