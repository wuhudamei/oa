package cn.damei.utils.excel;
public class ExcelHeader implements Comparable<ExcelHeader> {
	private String title;
	private int order;
	private String propertyName;
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int compareTo(ExcelHeader o) {
		return order > o.order ? 1 : (order < o.order ? -1 : 0);
	}
	public ExcelHeader(String title, int order, String propertyName) {
		super();
		this.title = title;
		this.order = order;
		this.propertyName = propertyName;
	}
}