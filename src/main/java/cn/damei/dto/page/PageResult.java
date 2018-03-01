package cn.damei.dto.page;
import java.io.Serializable;
import java.util.List;
public class PageResult<T> implements Serializable {
	private static final long serialVersionUID = 1082048602226192539L;
	List<T> rows;
	Integer total;
	Integer offset;
	Integer limit;
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
