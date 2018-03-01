package cn.damei.rest.commonapply;
import java.io.Serializable;
public class WatermarkInfo implements Serializable {
	private static final long serialVersionUID = 4501817785196042562L;
	private String context;
	private int x;
	private int y;
	private int type;
	private float fillOpacity;
	public WatermarkInfo(String context, int x, int y, int type) {
		this.context = context;
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public WatermarkInfo(String context, int x, int y, int type, float fillOpacity) {
		this(context, x, y, type);
		this.fillOpacity = fillOpacity;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public float getFillOpacity() {
		return fillOpacity;
	}
	public void setFillOpacity(float fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
}
