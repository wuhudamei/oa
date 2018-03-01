package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.service.stylist.MdniContractService;
public class StylistContractServiceTest extends SpringTestCase {
    @Autowired
    private MdniContractService mdniContractService;
    @Test
    public void testGetContracts() {
        mdniContractService.findByMonth("2017-04");
    }
}
