package cn.damei.dto.process;
import java.util.ArrayList;
import java.util.List;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
public class ProcessEntityTreeDto {
    private Long id;
    private String text;
    private List<ProcessEntityTreeDto> children;
    private TreeState state;
    private Long pid;
    private WfApplyTypeEnum parentWfType;
    private WfNatureEnum parentWfNature;
    private Integer sort;
    public ProcessEntityTreeDto setOpened(Boolean opened) {
        if (this.state == null) {
            this.state = new TreeState(opened);
        } else {
            this.state.setOpened(opened);
        }
        return this;
    }
    public Long getId() {
        return id;
    }
    public ProcessEntityTreeDto setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getPid() {
		return pid;
	}
	public ProcessEntityTreeDto setPid(Long pid) {
		this.pid = pid;
		return this;
	}
    public String getText() {
        return text;
    }
    public ProcessEntityTreeDto setText(String text) {
        this.text = text;
        return this;
    }
    public List<ProcessEntityTreeDto> getChildren() {
        return children;
    }
    public ProcessEntityTreeDto setChildren(List<ProcessEntityTreeDto> children) {
        this.children = children;
        return this;
    }
    public TreeState getState() {
        return state;
    }
    public ProcessEntityTreeDto setState(TreeState state) {
        this.state = state;
        return this;
    }
    public Integer getSort() {
        return sort;
    }
    public ProcessEntityTreeDto setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
	public WfApplyTypeEnum getParentWfType() {
		return parentWfType;
	}
	public ProcessEntityTreeDto setParentWfType(WfApplyTypeEnum parentWfType) {
		this.parentWfType = parentWfType;
		return this;
	}
	public WfNatureEnum getParentWfNature() {
		return parentWfNature;
	}
	public ProcessEntityTreeDto setParentWfNature(WfNatureEnum parentWfNature) {
		this.parentWfNature = parentWfNature;
		return this;
	}
    public void pushChildren(ProcessEntityTreeDto subPermissionTreeDto) {
        if(subPermissionTreeDto != null){
            if(this.children == null){
                this.children = new ArrayList<>();
            }
            this.children.add(subPermissionTreeDto);
        }
    }
    private static class TreeState {
        private Boolean opened;
        public TreeState() {
        }
        public TreeState(Boolean opened) {
            this.opened = opened;
        }
        public Boolean getOpened() {
            return opened;
        }
        public void setOpened(Boolean opened) {
            this.opened = opened;
        }
    }
}
