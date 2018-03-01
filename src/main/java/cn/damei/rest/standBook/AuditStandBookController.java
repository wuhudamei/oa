package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.standBook.AuditChangeStandBook;
import cn.damei.entity.standBook.AuditStandBook;
import cn.damei.service.standBook.AuditStandBookService;
import cn.damei.service.standBook.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/auditStandBook")
public class AuditStandBookController extends BaseController {
    @Autowired
    private AuditStandBookService auditStandBookService;
    @RequestMapping("/contractAudit")
    public Object findContractAudit(@RequestParam String orderno) {
        return StatusDto.buildSuccess(this.auditStandBookService.findContractAudit(orderno));
    }
    @RequestMapping("/changeAudit")
    public Object findChangeAudit(@RequestParam String orderno) {
        List<AuditChangeStandBook> changeAudit = this.auditStandBookService.findChangeAudit(orderno);
        return StatusDto.buildSuccess(changeAudit);
    }
}
