package cn.damei.dto.wechat.menu;
import com.google.common.collect.Lists;
import cn.damei.entity.organization.State;
import cn.damei.entity.wechat.menu.Menu;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class MenuTreeDto {
    public MenuTreeDto() {
        super();
    }
    public MenuTreeDto(Long id) {
        this.id = id;
    }
    public MenuTreeDto(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }
    private Long id;
    private List<MenuTreeDto> children;
    private String text;
    private State state;
    public Long getId() {
        return id;
    }
    public MenuTreeDto setId(Long id) {
        this.id = id;
        return this;
    }
    public List<MenuTreeDto> getChildren() {
        return children;
    }
    public MenuTreeDto setChildren(List<MenuTreeDto> children) {
        this.children = children;
        return this;
    }
    public String getText() {
        return text;
    }
    public MenuTreeDto setText(String text) {
        this.text = text;
        return this;
    }
    public State getState() {
        return state;
    }
    public MenuTreeDto setState(State state) {
        this.state = state;
        return this;
    }
    public static MenuTreeDto buildTree(List<? extends Menu> menus) {
        MenuTreeDto root = new MenuTreeDto(0L);
        if (CollectionUtils.isEmpty(menus)) {
            return root;
        }
        Map<Long, List<Menu>> menuMap = menus.stream()
                .sorted(((o1, o2) -> o2.getSort().compareTo(o1.getSort())))
                .collect(Collectors.groupingBy(Menu::getPid));
        if (MapUtils.isNotEmpty(menuMap)) {
            List<MenuTreeDto> level1MenuDtos = fromPojos2Dtos(menuMap.get(Menu.ROOT_PID));
            for (MenuTreeDto leve1MenuDto : level1MenuDtos) {
                List<MenuTreeDto> level2MenuDtos = fromPojos2Dtos(menuMap.get(leve1MenuDto.getId()));
                leve1MenuDto.setChildren(level2MenuDtos);
            }
            root.setChildren(level1MenuDtos);
        }
        return root;
    }
    private static List<MenuTreeDto> fromPojos2Dtos(List<Menu> menus) {
        List<MenuTreeDto> res = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(menus)) {
            for (Menu menu : menus) {
                res.add(new MenuTreeDto(menu.getId()).setText(menu.getName()).setState(new State()));
            }
        }
        return res;
    }
}
