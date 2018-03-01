package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.entity.stylist.Bill;
import cn.damei.service.stylist.BillService;
import java.util.List;
public class BillServiceTest extends SpringTestCase {
    @Autowired
    private BillService billService;
    @Test
    public void testGetByMonthBillId() {
        List<Bill> bills = billService.getByMonthBillId(6L);
        bills.forEach(e -> System.out.println(e.getTotalMoney()));
    }
}
