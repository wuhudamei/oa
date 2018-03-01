package cn.damei.rest.stylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.stylist.Rule;
import cn.damei.service.stylist.RuleService;
import java.util.List;
@RestController
@RequestMapping("/api/rules")
public class RuleRestController {
    @Autowired
    private RuleService ruleService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto findRules() {
        return StatusDto.buildSuccess(ruleService.findRules());
    }
    @RequestMapping(method = RequestMethod.POST)
    public StatusDto updateRules(@RequestBody List<Rule> rules) {
        ruleService.updateRules(rules);
        return StatusDto.buildSuccess("更新提成规则成功！");
    }
}
