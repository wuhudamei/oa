package cn.damei.service.wechat.menu;
import com.rocoinfo.weixin.api.MenuApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.common.service.CrudService;
import cn.damei.dto.wechat.menu.ButtonDto;
import cn.damei.dto.wechat.menu.MatchRuleDto;
import cn.damei.entity.wechat.menu.ConditionalMenu;
import cn.damei.entity.wechat.menu.ConditionalMenuDetail;
import cn.damei.entity.wechat.tag.Tag;
import cn.damei.repository.wechat.menu.ConditionalMenuDao;
import cn.damei.service.wechat.tag.TagService;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class ConditionalMenuService extends CrudService<ConditionalMenuDao, ConditionalMenu> {
    @Autowired
    private TagService tagService;
    @Override
    @Transactional
    public int insert(ConditionalMenu menu) {
        this.getAndSetTagOid(menu);
        return super.insert(menu);
    }
    @Override
    @Transactional
    public int update(ConditionalMenu menu) {
        this.getAndSetTagOid(menu);
        return super.update(menu);
    }
    @Override
    @Transactional
    public int deleteById(Long id) {
        ConditionalMenu menu = this.getById(id);
        String oid = Optional.ofNullable(menu).map(ConditionalMenu::getOid).orElse(null);
        if (this.remoteDeleteByOid(oid)) {
            return super.deleteById(id);
        }
        return 0;
    }
    public ConditionalMenu getDetailById(Long id) {
        if (id != null) {
            return this.entityDao.getDetailById(id);
        }
        return null;
    }
    public boolean sync(Long id) {
        if (id == null) {
            return false;
        }
        ConditionalMenu menu = this.getDetailById(id);
        if (menu == null) {
            return false;
        }
        boolean deleted = this.remoteDeleteByOid(menu.getOid());
        if (deleted) {
            boolean pushed = this.remoteSync(menu);
            if (pushed) {
                this.update(menu);
                return pushed;
            }
        }
        return false;
    }
    private boolean remoteSync(ConditionalMenu menu) {
        if (menu == null) {
            return false;
        }
        List<ConditionalMenuDetail> list = menu.getDetails();
        List<ButtonDto> buttonDtos = ButtonDto.convertMenu2Buttons(list);
        Long tagId = Optional.ofNullable(menu.getTag()).map(Tag::getOid).orElse(null);
        MatchRuleDto matchRuleDto = MatchRuleDto.fromTagId(String.valueOf(tagId));
        Map<String, Object> map = MapUtils.of("button", buttonDtos, "matchrule", matchRuleDto);
        String json = JsonUtils.toJson(map);
        ApiResult res = MenuApi.addConditionMenu(json);
        boolean b = res.isSuccess();
        if (b) {
            Object menuId = Optional.ofNullable(res.fromJsonAsMap()).map((o) -> o.get("menuid")).orElse(null);
            menu.setOid(String.valueOf(menuId));
        }
        return b;
    }
    private void getAndSetTagOid(ConditionalMenu menu) {
        Long tagId = Optional.ofNullable(menu).map(ConditionalMenu::getTag).map(Tag::getId).orElse(null);
        if (tagId != null) {
            Tag tag = this.tagService.getById(tagId);
            if (tag != null) {
                menu.getTag().setOid(tag.getOid());
            }
        }
    }
    private boolean remoteDeleteByOid(String oid) {
        if (StringUtils.isNotEmpty(oid)) {
            ApiResult res = MenuApi.deleteConditionMenu(oid);
            return res.isSuccess();
        }
        return true;
    }
}