package cn.damei.common.view;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;
public class ViewExcel<T> extends AbstractExcelView {
	private String fileName = "myFile.xls";
	private String characterEncoding = "utf-8";
	private List<T> list = null;
	private int headerIndex = 0;
	private int contextIndex = 1;
	private String dateFmt = "yyyy-MM-dd HH:mm:ss";
	private String sheetName = "mySheet";
	private LinkedHashMap<String, String> headerMapper = null;
	private String emptyFillStr = "";
	private boolean isWriteRowNumer = false;
	public ViewExcel(String fileName, String sheetName, LinkedHashMap<String, String> headerMapper, List<T> list) {
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.headerMapper = headerMapper;
		this.list = list;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		// if (null == this.headerMapper) {
		// throw new IllegalStateException("headerMapper is empty.");
		// }
		// if (null == this.list) {
		// throw new IllegalStateException("list is empty.");
		// }
		response.setCharacterEncoding(this.characterEncoding);
		response.setContentType("application/ms-excel");
		response.setHeader("Content-Disposition",
				"inline; filename=" + new String(this.fileName.getBytes(), "iso8859-1"));
		OutputStream outputStream = response.getOutputStream();
		// 产生Excel表头
		HSSFSheet sheet = workbook.createSheet(this.sheetName);
		HSSFRow row = sheet.createRow(this.headerIndex);
		Collection<String> title = headerMapper.values();
		int hIndex = 0;
		if (isWriteRowNumer) {
			row.createCell(0).setCellValue("序号");
			hIndex++;
		}
		for (String h : title) {
			row.createCell(hIndex).setCellValue(h);
			hIndex++;
		}
		List<T> listT = this.list;
		int rowIdx = this.headerIndex + 1;
		Collection<String> files = headerMapper.keySet();
		for (T obj : listT) {
			row = sheet.createRow(rowIdx);
			int colIdx = 0;
			if (isWriteRowNumer) {
				row.createCell(0).setCellValue(rowIdx);
				colIdx++;
			}
			for (String fieldName : files) {
				Cell cell = row.createCell(colIdx);
				Object propValue = null;
				try {
					if (obj instanceof Map) {
						Map<String, Object> map = (Map<String, Object>) obj;
						propValue = map.get(fieldName);
					} else {
						propValue = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(obj, fieldName);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (null == propValue || "".equals(propValue)) {
					propValue = this.emptyFillStr;
				}
				if (propValue instanceof Date) {
					SimpleDateFormat df = new SimpleDateFormat(this.dateFmt);
					String dateStr = df.format(propValue);
					cell.setCellValue(dateStr);
				} else if (propValue instanceof Double) {
					cell.setCellValue((Double) propValue);
				} else if (propValue instanceof Integer) {
					cell.setCellValue((Integer) propValue);
				} else if (propValue instanceof Boolean) {
					cell.setCellValue((Boolean) propValue);
				} else {
					cell.setCellValue(propValue.toString());
				}
				colIdx++;
			}
			rowIdx++;
		}
		workbook.write(outputStream);
		outputStream.flush();
		//outputStream.close();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getHeaderIndex() {
		return headerIndex;
	}
	public void setHeaderIndex(int headerIndex) {
		this.headerIndex = headerIndex;
	}
	public int getContextIndex() {
		return contextIndex;
	}
	public void setContextIndex(int contextIndex) {
		this.contextIndex = contextIndex;
	}
	public String getDateFmt() {
		return dateFmt;
	}
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public LinkedHashMap<String, String> getHeaderMapper() {
		return headerMapper;
	}
	public void setHeaderMapper(LinkedHashMap<String, String> headerMapper) {
		this.headerMapper = headerMapper;
	}
	public String getEmptyFillStr() {
		return emptyFillStr;
	}
	public void setEmptyFillStr(String emptyFillStr) {
		this.emptyFillStr = emptyFillStr;
	}
	public boolean isWriteRowNumer() {
		return isWriteRowNumer;
	}
	public void setWriteRowNumer(boolean isWriteRowNumer) {
		this.isWriteRowNumer = isWriteRowNumer;
	}
}
