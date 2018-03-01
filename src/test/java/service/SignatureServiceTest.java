package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.entity.account.User;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.service.budget.SignatureService;
import java.util.Date;
public class SignatureServiceTest extends SpringTestCase {
    @Autowired
    private SignatureService signatureService;
    @Test
    public void testInsert() {
        Signature signature = new Signature();
        signature.setApplyTitle("2017-07签报单申请");
        signature.setApplyCode("2017070601");
        signature.setSignatureDate("2017-07");
        signature.setCompany(new MdniOrganization(1L));
        signature.setCreateUser(new User(1L));
        signature.setCreateDate(new Date());
        signature.setType(3L);
        signature.setReason("test");
        signature.setTotalMoney(200d);
        signature.setStatus(ApplyStatus.APPROVALING);
        signatureService.insert(signature);
    }
}
