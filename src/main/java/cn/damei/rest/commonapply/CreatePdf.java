package cn.damei.rest.commonapply;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
public class CreatePdf {
	private int colNum;
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	int maxWidth = 520;
	Document document = new Document();// 建立一个Document对象
	private static Font titleFont;
	private static Font headfont;// 设置字体大小
	private static Font keyfont;// 设置字体大小
	private static Font textfont;// 设置字体大小
	public CreatePdf(File file,int colNum) {
		this.colNum = colNum;
		document.setPageSize(PageSize.A4);// 设置页面大小
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public CreatePdf(int colNum) {
		this.colNum = colNum;
	}
	public void initFile(File file) {
		document.setPageSize(PageSize.A4);// 设置页面大小
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public PdfPCell createCell(String value, Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	public PdfPCell createCell(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	public PdfPCell createCell(String value, Font font, int align, int colspan) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		}
		return cell;
	}
	public PdfPCell createCell(String value, Font font, int align, int colspan, int rowspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		}
		return cell;
	}
	public PdfPTable createTable(int colNumber) {
		PdfPTable table = new PdfPTable(colNumber);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	public PdfPTable createTable(float[] widths) {
		PdfPTable table = new PdfPTable(widths);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	public PdfPTable createBlankTable() {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorder(0);
		table.addCell(createCell("", keyfont));
		table.setSpacingAfter(20.0f);
		table.setSpacingBefore(20.0f);
		return table;
	}
	@SuppressWarnings("unchecked")
	public <T> void generatePDF(List<T> list, Map<String, Object> mapParam) {
		int colNum = 6;
		// Class clazz = list.get(0).getClass();
		// 创建一个只有5列的表格
		PdfPTable table = createTable(colNum);
		table.addCell(createCell("通用审批", titleFont, Element.ALIGN_CENTER, 6, false));
		// 添加备注,靠左，不显示边框
		table.addCell(createCell("大美装饰管理平台", keyfont, Element.ALIGN_LEFT, 3, false));
		// 添加备注,靠左，不显示边框
		table.addCell(createCell("申请日期：" + mapParam.get("applyTimeHMS"), keyfont, Element.ALIGN_RIGHT, 3, false));
		try {
			if (null != list && list.size() > 0) {
				int size = list.size();
				for (int i = 0; i < size; i++) {
					T obj = list.get(i);
					Class<? extends Object> clazz = obj.getClass();
					BeanInfo bif = Introspector.getBeanInfo(clazz);
					PropertyDescriptor pds[] = bif.getPropertyDescriptors();
					for (PropertyDescriptor pd : pds) {
						String field = pd.getName();
						if (field.equals("class")) {
							continue;
						}
						PropertyDescriptor proDescriptor = new PropertyDescriptor(field, clazz);
						Method methodGet = proDescriptor.getReadMethod();
						Object value = methodGet.invoke(obj);
						if (field.equals("label")) {
							table.addCell(createCell(String.valueOf(value), headfont, Element.ALIGN_LEFT, 1, true));
						} else {
							if (value instanceof List) {
								List<LinkedHashMap<String, Object>> listMap = (List<LinkedHashMap<String, Object>>) value;
								StringBuffer buff = new StringBuffer();
								for (Map<String, Object> map : listMap) {
									Set<Entry<String, Object>> entrySet = map.entrySet();
									for (Entry<String, Object> entry : entrySet) {
										Object cellValue = entry.getValue();
										if (null == cellValue) {
											cellValue = "";
										}
										buff.append(cellValue + " ");
									}
									buff.append("\n");
								}
								// float[] widths = { 433 };
								// PdfPTable nest1 = new PdfPTable(widths);
								// nest1.setSpacingBefore(0);
								// nest1.addCell("1");
								// nest1.addCell("2");
								// nest1.addCell("3");
								//
								// nest1.addCell("4");
								// nest1.addCell("5");
								// nest1.addCell("6");
								//
								// PdfPCell tempCell = new PdfPCell(nest1);
								// tempCell.setPadding(0);
								// table.addCell(tempCell);
								// System.out.println("list type");
								table.addCell(createCell(buff.toString(), textfont, Element.ALIGN_LEFT, 5, true));
							} else {
								table.addCell(createCell(String.valueOf(value), textfont, Element.ALIGN_LEFT, 5, true));
							}
						}
					}
				}
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
		// 添加备注,靠左，不显示边框
		table.addCell(createCell("打印人：" + mapParam.get("printUser"), keyfont, Element.ALIGN_LEFT, 3, false));
		// 添加备注,靠左，不显示边框
		table.addCell(createCell("打印时间：" + mapParam.get("printTime"), keyfont, Element.ALIGN_RIGHT, 3, false));
		try {
			// 将表格添加到文档中
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 关闭流
		document.close();
	}
	public <T> String generatePDFs(String saveFilePath, List<T> list, Map<String, Object> map) {
		File file = new File(saveFilePath);
		initFile(file);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new CreatePdf(file,this.colNum).generatePDF(list, map);
		return file.getAbsolutePath();
	}
	public <T> String generatePDFs(String saveFilePath, List<PDFCell> list) {
		File file = new File(saveFilePath);
		initFile(file);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new CreatePdf(file,this.colNum).generatePDF(list);
		return file.getAbsolutePath();
	}
	public <T> void generatePDF(List<PDFCell> list) {
		if(0 == this.colNum){
			throw new IllegalStateException("colNum is 0 ");
		}
		PdfPTable table = createTable(this.colNum);
		for (PDFCell pdfCell : list) {
			Object value = pdfCell.getValue();
			Font font = pdfCell.getFont();
			int align = pdfCell.getAlign();
			int colSpan = pdfCell.getColSpan();
			boolean boderFlag = pdfCell.isBoderFlag();
			if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<LinkedHashMap<String, Object>> listMap = (List<LinkedHashMap<String, Object>>) value;
				StringBuffer buff = new StringBuffer();
				for (Map<String, Object> map : listMap) {
					Set<Entry<String, Object>> entrySet = map.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						Object cellValue = entry.getValue();
						if (null == cellValue) {
							cellValue = "";
						}
						buff.append(cellValue + " ");
					}
					buff.append("\n");
				}
				table.addCell(createCell(buff.toString(), font, align, colSpan, boderFlag));
			} else {
				table.addCell(createCell(String.valueOf(value), font, align, colSpan, boderFlag));
			}
		}
		try {
			// 将表格添加到文档中
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 关闭流
		document.close();
	}
	public static void addWaterMark(String srcFile, String destFile, String text, int textWidth, int textHeight) {
		try {
			// 待加水印的文件
			PdfReader reader = new PdfReader(srcFile);
			// 加完水印的文件
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
			int total = reader.getNumberOfPages() + 1;
			PdfContentByte content;
			// 设置字体
			BaseFont font = BaseFont.createFont();
			// 循环对每页插入水印
			for (int i = 1; i < total; i++) {
				// 水印的起始
				content = stamper.getUnderContent(i);
				// 开始
				content.beginText();
				// 设置颜色 默认为蓝色
				// content.setColorFill(BaseColor.BLUE);
				// content.setColorFill(Color.GRAY);
				// 设置字体及字号
				content.setFontAndSize(font, 38);
				// 设置起始位置
				// content.setTextMatrix(400, 880);
				content.setTextMatrix(textWidth, textHeight);
				// 开始写入水印
				content.showTextAligned(Element.ALIGN_LEFT, text, textWidth, textHeight, 45);
				content.endText();
			}
			stamper.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public static void imageWatermark(String inputPath, String outputPath, String imgPath, int x, int y) {
		try {
			PdfReader reader = new PdfReader(inputPath);
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputPath));
			PdfGState gs1 = new PdfGState();
			gs1.setFillOpacity(0.1f);
			Image image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(imgPath)));
			int n = reader.getNumberOfPages();
			for (int i = 1; i <= n; i++) {
				PdfContentByte pdfContentByte = stamp.getOverContent(i);
				pdfContentByte.setGState(gs1);
				image.setAbsolutePosition(x, y);
				pdfContentByte.addImage(image);
			}
			stamp.close();
			reader.close();
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
	public static void imageWatermark(String inputPath, String outputPath, List<WatermarkInfo> wk)
			throws IOException, DocumentException {
		PdfReader reader = new PdfReader(inputPath);
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputPath));
		int n = reader.getNumberOfPages();
		for (int i = 1; i <= n; i++) {
			PdfContentByte pdfContentByte = stamp.getOverContent(i);
			for (WatermarkInfo watermarkInfo : wk) {
				int x = watermarkInfo.getX();
				int y = watermarkInfo.getY();
				int type = watermarkInfo.getType();
				float fillOpacity = watermarkInfo.getFillOpacity(); 
				// 图片
				PdfGState gs1 = new PdfGState();
				gs1.setFillOpacity(fillOpacity);
				pdfContentByte.setGState(gs1);
				String context = watermarkInfo.getContext();
				if (type == 0) {
					Image image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(context)));
					image.setAbsolutePosition(x, y);
					pdfContentByte.addImage(image);
				} else if (type == 1) {
					pdfContentByte.beginText();
					// 设置颜色 默认为蓝色
					// pdfContentByte.setColorFill(BaseColor.BLUE);
					// pdfContentByte.setColorFill(Color.GRAY);
					// 设置字体
					BaseFont font = BaseFont.createFont("/simsun.ttc" + ",1", BaseFont.IDENTITY_H,
							BaseFont.NOT_EMBEDDED);
					// 设置字体及字号
					pdfContentByte.setFontAndSize(font, 30);
					// 设置起始位置
					// pdfContentByte.setTextMatrix(400, 880);
					pdfContentByte.setTextMatrix(x, y);
					// 开始写入水印
					pdfContentByte.showTextAligned(Element.ALIGN_LEFT, context, x, y, 15);
					// pdfContentByte.showText(context);
					pdfContentByte.endText();
				}
			}
		}
		stamp.close();
		reader.close();
	}
}
