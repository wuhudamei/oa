package cn.damei.service.stylist;
import com.google.common.collect.Lists;
import com.rocoinfo.weixin.util.CollectionUtils;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.Rule;
import cn.damei.enumeration.stylist.Indirect;
import cn.damei.enumeration.stylist.Level;
import cn.damei.enumeration.stylist.Status;
import cn.damei.repository.stylist.RuleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
@Service
public class RuleService extends CrudService<RuleDao, Rule> {
    public Map<String, List<Rule>> findRules() {
        Level[] levels = Level.values();
        Indirect[] indirects = Indirect.values();
        Status[] statuses = Status.values();
        List<Rule> dbRules = this.entityDao.findAll();
        List<Rule> levelRules = Lists.newArrayList();
        for (Level level : levels) {
            buildRules(level.name(), level.getLabel(), Rule.Type.LEVEL, dbRules, levelRules);
        }
        for (Indirect indirect : indirects) {
            buildRules(indirect.name(), indirect.getLabel(), Rule.Type.INDIRECT, dbRules, levelRules);
        }
        for (Status status : statuses) {
            buildRules(status.name(), status.getLabel(), Rule.Type.GRANT, dbRules, levelRules);
        }
        return levelRules.stream().collect(groupingBy(rule -> rule.getType().name().toLowerCase()));
    }
    private void buildRules(String code, String label, Rule.Type type, List<Rule> originRules, List<Rule> targetRules) {
        Rule dbRule = getDbRuleByCode(originRules, code);
        Rule rule = new Rule().setCode(code)
                .setName(label)
                .setType(type);
        if (dbRule != null) {
            rule.setRatio1(dbRule.getRatio1())
                    .setRatio2(dbRule.getRatio2());
        }
        targetRules.add(rule);
    }
    private Rule getDbRuleByCode(List<Rule> rules, String code) {
        if (CollectionUtils.isNotEmpty(rules) && StringUtils.isNotBlank(code)) {
            for (Rule rule : rules) {
                if (code.equals(rule.getCode())) {
                    return rule;
                }
            }
        }
        return null;
    }
    @Transactional
    public void updateRules(List<Rule> rules) {
        this.entityDao.clean();
        this.entityDao.batchInsert(rules);
    }
}