package cn.damei.rest.stylist;
import com.alibaba.druid.util.StringUtils;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.stylist.Contract;
import cn.damei.enumeration.stylist.Status;
import cn.damei.repository.stylist.ContractDao;
import cn.damei.service.stylist.ContractService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/stylist/contracts")
public class ContractRestController {
    @Autowired
    private ContractService contractService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "searchMonth", required = false) String searchMonth,
                            @RequestParam(value = "status", required = false) Status status,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUserId();
        Map<String, Object> params = new HashMap<>();
        params.put(ContractDao.USER_ID, userId);
        MapUtils.putNotNull(params, ContractDao.KEYWORD, keyword);
        MapUtils.putNotNull(params, ContractDao.SEARCH_MONTH, searchMonth);
        MapUtils.putNotNull(params, ContractDao.STATUS, status);
        PageTable page = contractService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    public StatusDto update(@RequestBody Contract contract) {
        contractService.update(contract);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    public StatusDto findById(@PathVariable(value = "id") Long id) {
        if (id == null) {
            return StatusDto.buildFailure("没有找到此合同！");
        }
        return StatusDto.buildSuccess(contractService.getById(id));
    }
    @RequestMapping(value = "synContract", method = RequestMethod.GET)
    public StatusDto synContract(@RequestParam(value = "synMonth") String synMonth) {
        if (StringUtils.isEmpty(synMonth)) {
            return StatusDto.buildFailure("请选择同步月份！");
        }
        Long userId = WebUtils.getLoggedUserId();
        String message = contractService.synByUserAndMonth(userId, synMonth);
        if (message != null) {
            return StatusDto.buildFailure(message);
        }
        return StatusDto.buildSuccess("同步合同信息成功！");
    }
    @RequestMapping(value = "/synAllContract", method = RequestMethod.GET)
    public StatusDto synAllContract() {
        contractService.syncAllStylistContract();
        return StatusDto.buildSuccess("同步合同信息成功！");
    }
}
