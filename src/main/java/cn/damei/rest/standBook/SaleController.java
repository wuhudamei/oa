package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.standBook.Sale;
import cn.damei.repository.employee.EmployeeDao;
import cn.damei.service.standBook.SaleService;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/sale")
public class SaleController extends BaseController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private EmployeeDao employeeDao;
    @RequestMapping("/getOrderDetailByOrderNo")
    public Object getOrderDetailByCustomerId(@RequestParam(required = false) String orderno) {
        Sale sale = this.saleService.findOrderDetail(orderno);
        return StatusDto.buildSuccess(sale);
    }
}
