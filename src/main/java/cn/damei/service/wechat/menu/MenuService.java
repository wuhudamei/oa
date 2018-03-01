package cn.damei.service.wechat.menu;
import com.rocoinfo.weixin.api.MenuApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.common.service.CrudService;
import cn.damei.common.service.ServiceException;
import cn.damei.dto.wechat.menu.ButtonDto;
import cn.damei.dto.wechat.menu.MenuTreeDto;
import cn.damei.entity.organization.State;
import cn.damei.entity.wechat.menu.Menu;
import cn.damei.repository.wechat.menu.MenuDao;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
@Service
public class MenuService extends CrudService<MenuDao, Menu> {
    @Transactional(rollbackFor = ServiceException.class)
    public MenuTreeDto create(Menu menu) {
        if (Objects.nonNull(menu)) {
            int i = this.insert(menu);
            if (i > 0) {
                boolean b = this.push2Wechat();
                if (b) {
                    return new MenuTreeDto()
                            .setId(menu.getId())
                            .setText(menu.getName())
                            .setState(new State());
                }
                throw new ServiceException("菜单推送失败！");
            }
        }
        return null;
    }
    @Transactional(rollbackFor = ServiceException.class)
    public MenuTreeDto edit(Menu menu) {
        if (Objects.nonNull(menu)) {
            int i = this.update(menu);
            if (i > 0) {
                boolean b = this.push2Wechat();
                if (b) {
                    return new MenuTreeDto()
                            .setId(menu.getId())
                            .setText(menu.getName())
                            .setState(new State());
                }
                throw new ServiceException("菜单推送失败！");
            }
        }
        return null;
    }
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int deleteById(Long id) {
        if (Objects.nonNull(id)) {
            int i = super.deleteById(id);
            if (i > 0) {
                boolean b = this.push2Wechat();
                if (b) {
                    return i;
                }
                throw new ServiceException("菜单推送失败！");
            }
        }
        return 0;
    }
    private boolean push2Wechat() {
        List<Menu> list = super.findAll();
        List<ButtonDto> buttonDtos = ButtonDto.convertMenu2Buttons(list);
        String json = JsonUtils.toJson(MapUtils.of("button", buttonDtos));
        ApiResult res = MenuApi.create(json);
        return res.isSuccess();
    }
}