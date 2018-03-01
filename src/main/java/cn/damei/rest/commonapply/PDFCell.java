package cn.damei.rest.commonapply;
import java.io.IOException;
import java.io.Serializable;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
public class PDFCell implements  Comparable<PDFCell> {
	static BaseFont bfChinese = null;
	static {
		try {
			bfChinese = BaseFont.createFont("/simsun.ttc" + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final long serialVersionUID = 5491982384436258981L;
	private Object value;
	private int index;
	private int colSpan;
	private int align;
	private Font font;
	private boolean boderFlag;
	public PDFCell() {
	}
	public PDFCell(int index, Object value, Font font, int align, int colSpan, boolean boderFlag) {
		this.index = index;
		this.value = value;
		this.font = font;
		this.align = align;
		this.colSpan = colSpan;
		this.boderFlag = boderFlag;
	}
	public static PDFCell buildTitleCell(int index, Object value) {
		Font titleFont = new Font(bfChinese, 16, Font.BOLD);// 设置字体大小
		return new PDFCell(index, value, titleFont, Element.ALIGN_CENTER, 6, false);
	}
	public static PDFCell buildNoBorderCell(int index, Object value, int colSpan) {
		Font valueFont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
		return new PDFCell(index, value, valueFont, Element.ALIGN_CENTER, colSpan, false);
	}
	public static PDFCell buildLabelCell(int index, Object value) {
		Font labelFont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
		return new PDFCell(index, value, labelFont, Element.ALIGN_LEFT, 1, true);
	}
	public static PDFCell buildLabelCellNORMAL(int index, Object value) {
		Font labelFont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
		return new PDFCell(index, value, labelFont, Element.ALIGN_LEFT, 1, true);
	}
	public static PDFCell buildValueCell(int index, Object value, int colSpan) {
		Font valueFont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
		return new PDFCell(index, value, valueFont, Element.ALIGN_LEFT, colSpan, true);
	}
	public static PDFCell buildValueCellBOLD(int index, Object value, int colSpan) {
		Font valueFont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
		return new PDFCell(index, value, valueFont, Element.ALIGN_CENTER, colSpan, true);
	}
	public static PDFCell buildValueCellBOLDForSize(int index, Object value, int colSpan,int fontSize) {
		Font valueFont = new Font(bfChinese, fontSize, Font.BOLD);// 设置字体大小
		return new PDFCell(index, value, valueFont, Element.ALIGN_CENTER, colSpan, true);
	}
	public PDFCell getLabelCon(String label) {
		return this;
	}
	public PDFCell getValueCon(String label) {
		return this;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int compareTo(PDFCell o) {
		if (this.index > o.index) {
			return -1;
		} else if (this.index < o.index) {
			return 1;
		}
		return 0;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getAlign() {
		return align;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public boolean isBoderFlag() {
		return boderFlag;
	}
	public void setBoderFlag(boolean boderFlag) {
		this.boderFlag = boderFlag;
	}
}
