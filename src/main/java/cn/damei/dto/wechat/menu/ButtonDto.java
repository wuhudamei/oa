package cn.damei.dto.wechat.menu;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import cn.damei.entity.wechat.menu.Menu;
import org.apache.commons.collections.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
public class ButtonDto {
    public static ButtonDto fromName(String name) {
        ButtonDto buttonDto = new ButtonDto();
        buttonDto.setName(name);
        return buttonDto;
    }
    private String name;
    private String type;
    private String url;
    private String key;
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("sub_button")
    private List<ButtonDto> subButtons;
    public String getName() {
        return name;
    }
    public ButtonDto setName(String name) {
        this.name = name;
        return this;
    }
    public String getType() {
        return type;
    }
    public ButtonDto setType(String type) {
        this.type = type;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public ButtonDto setUrl(String url) {
        this.url = url;
        return this;
    }
    public String getKey() {
        return key;
    }
    public ButtonDto setKey(String key) {
        this.key = key;
        return this;
    }
    public String getMediaId() {
        return mediaId;
    }
    public ButtonDto setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }
    public List<ButtonDto> getSubButtons() {
        return subButtons;
    }
    public ButtonDto setSubButtons(List<ButtonDto> subButtons) {
        this.subButtons = subButtons;
        return this;
    }
    public static List<ButtonDto> convertMenu2Buttons(List<? extends Menu> menus) {
        List<ButtonDto> res = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(menus)) {
            Map<Long, List<Menu>> menuMap = menus.stream()
                    .sorted((o1, o2) -> o2.getSort().compareTo(o1.getSort()))
                    .collect(Collectors.groupingBy(Menu::getPid));
            List<Menu> level1Menus = menuMap.get(Menu.ROOT_PID);
            if (CollectionUtils.isNotEmpty(level1Menus)) {
                res = level1Menus.stream()
                        .limit(3)
                        .map((o) -> convertMenu2Button(o, menuMap.get(o.getId())))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }
        return res;
    }
    private static ButtonDto convertMenu2Button(Menu menu, List<Menu> subMenus) {
        if (Objects.nonNull(menu)) {
            ButtonDto buttonDto = ButtonDto.fromName(menu.getName());
            if (CollectionUtils.isNotEmpty(subMenus)) {
                List<ButtonDto> subButtons = subMenus.stream().limit(5)
                        .map((o) -> fromMenu(o))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(subButtons)) {
                    buttonDto.setSubButtons(subButtons);
                }
                return buttonDto;
            }
            return fromMenu(menu);
        }
        return null;
    }
    private static ButtonDto fromMenu(Menu menu) {
        if (menu != null) {
            ButtonDto buttonDto = ButtonDto.fromName(menu.getName());
            buttonDto.setType(menu.getType().getValue());
            switch (menu.getType()) {
                case VIEW:
                    buttonDto.setUrl(menu.getUrl());
                    break;
                case CLICK:
                    buttonDto.setKey(menu.getClickKey());
                    break;
                default:
                    break;
            }
            return buttonDto;
        }
        return null;
    }
}
